package com.utn.controller;

import com.utn.dao.ResultDao;
import com.utn.dao.WordsDao;
import com.utn.dto.Player;
import com.utn.dto.Result;
import com.utn.exception.CharNotFoundException;
import com.utn.exception.WordNotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HangmanController {

    private ResultDao resultDao;
    private WordsDao wordsDao;
    private String wordOfGame;
    private String initWordOfGame;
    private String transitionalWord;
    private boolean finished = false;
    private boolean available = true;
    private Integer charUsed = 0;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public HangmanController() {
        resultDao = new ResultDao();
        wordsDao = new WordsDao();
    }

    public void showResults() {
        System.out.println("----Last Winners Are ----\n");
        System.out.println(resultDao.getResults().toString());
        System.out.println("-----------------------\n");
    }

    public void play() {
        Optional<String> wordOptional = pickAWord();
        if (wordOptional.isPresent()) {
            System.out.println("Word picked: " + wordOptional.get() + " \n");
            this.initWordOfGame = wordOptional.get().toLowerCase();
            this.transitionalWord = wordOptional.get().toLowerCase();
            hideChars(initWordOfGame.length());
        } else {
            throw new WordNotFoundException();
        }
    }

    public void startThreads(Player... players) {
        Arrays.stream(players).forEach(player -> {
            Thread t = new Thread(player);
            t.start();
        });
    }

    public synchronized void game(Player player) throws InterruptedException {
        if (!isAvailable()) {
            wait();
        } else {
            setAvailable(false);
            sayRandomChar();
            checkIfWin(player);
            setAvailable(true);
            notifyAll();
        }
    }

    private void checkIfWin(Player player) {
        if (wordOfGame.indexOf('*') < 0) {
            this.finished = true;
            System.out.println("\nWinner " + player.getName() + " after " + charUsed + " chars");
            resultDao.saveResult(Result.builder()
                    .nameOfWinner(player.getName())
                    .charsUsed(charUsed)
                    .build());
        }
    }

    private void hideChars(Integer lenght) {
        char[] charArray = new char[lenght];
        Arrays.fill(charArray, '*');
        this.wordOfGame = new String(charArray);
    }

    private void sayRandomChar() {
        char randomChar = getRandomChar();
        if(transitionalWord.contains(String.valueOf(randomChar))) {
            List<Integer> indexes = IntStream.range(0, initWordOfGame.length())
                    .filter(i -> initWordOfGame.charAt(i) == randomChar)
                    .boxed()
                    .collect(Collectors.toList());
            indexes.forEach(index -> {
                StringBuilder wordOfGameBuilder = new StringBuilder(wordOfGame);
                wordOfGameBuilder.setCharAt(index,randomChar);
                this.wordOfGame = wordOfGameBuilder.toString();
            });
            this.transitionalWord = removeCharOnWord(transitionalWord,randomChar);
        }
        this.charUsed++;
    }

    private String removeCharOnWord(String stringToRemove ,Character character){
        return String.valueOf(stringToRemove).replace(String.valueOf(character),"");
    }

    private char getRandomChar() {
        List<Character> characterList
                = this.alphabet.codePoints()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(characterList);
        char charToReturn = characterList.stream().findFirst().orElseThrow(CharNotFoundException::new);
        this.alphabet = removeCharOnWord(this.alphabet,charToReturn);
        return charToReturn;
    }

    private Optional<String> pickAWord() {
        ArrayList<String> words = wordsDao.getAll();
        Collections.shuffle(words);
        return words.stream().findFirst();
    }

    private boolean isAvailable() {
        return this.available;
    }

    public boolean isFinished() {
        return this.finished;
    }

    private void setAvailable(boolean available) {
        this.available = available;
    }
}
