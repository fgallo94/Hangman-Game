package com.utn.controller;

import com.utn.dao.ResultDao;
import com.utn.dao.WordsDao;
import com.utn.dto.Player;
import com.utn.exception.WordNotFoundException;

import java.util.*;

public class HangmanController {

    private ResultDao resultDao;
    private WordsDao wordsDao;
    private String wordOfGame;
    private String initWordOfGame;
    private boolean finished = false;
    private boolean available = true;
    private Integer charUsed = 0;

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
            this.initWordOfGame = wordOptional.get();
            hideChars(initWordOfGame.length());
        } else {
            throw new WordNotFoundException();
        }
    }

    public void startThreads(Player... players) {
        Arrays.stream(players).forEach(player -> player.run());
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
            System.out.println("\n Winner " + player.getName() + " after " + charUsed + " chars");
        }
    }

    private void hideChars(Integer lenght) {
        char[] charArray = new char[lenght];
        Arrays.fill(charArray, '*');
        this.wordOfGame = new String(charArray);
    }

    private void sayRandomChar() {
        char randomChar = getRandomChar();
        int indexRandomChar = initWordOfGame.indexOf(randomChar);
        if (indexRandomChar >= 0) {
            this.wordOfGame = wordOfGame.replace('*', randomChar);
            StringBuilder wordOfGameBuilder = new StringBuilder(wordOfGame);
            wordOfGameBuilder.replace(indexRandomChar, indexRandomChar, String.valueOf(randomChar));
        }
        this.charUsed++;
    }

    private void sayRandomCharWithStream(){
        char randomChar = getRandomChar();
        initWordOfGame.chars()
                .
    }

    private char getRandomChar() {
        Random r = new Random();
        return (char) (r.nextInt(26) + 'a');
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
