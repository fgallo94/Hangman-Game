package com.utn.controller;

import com.utn.dao.ResultDao;
import com.utn.dao.WordsDao;
import com.utn.exception.CharNotFoundException;
import com.utn.exception.WordNotFoundException;
import com.utn.model.Player;
import com.utn.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Core class of the project.
 */
public class HangmanController {

    private static final Character SPECIAL_CHARACTER = '*';
    private ResultDao resultDao;
    private WordsDao wordsDao;
    private String wordOfGame;
    private String initWordOfGame;
    private boolean finished = false;
    private boolean available = true;
    private Integer charUsed = 0;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Init Data Access Objects.
     */
    public HangmanController() {
        resultDao = new ResultDao();
        wordsDao = new WordsDao();
    }

    /**
     * Show all the result of data base on screen.
     */
    public void showResults() {
        System.out.println("----Last Winners Are ----\n");
        System.out.println(resultDao.getResults().toString());
        System.out.println("-----------------------\n");
    }

    /**
     * Pick a word of the data base if exist show on screen. After that, set initWordOfGame with this word and then hide all chars.
     * if not exist throw a WordNotFoundException
     */
    public void play() {
        Optional<String> wordOptional = pickAWord();
        if (wordOptional.isPresent()) {
            System.out.println("Word picked: " + wordOptional.get() + " \n");
            this.initWordOfGame = wordOptional.get().toLowerCase();
            hideChars(initWordOfGame.length());
        } else {
            throw new WordNotFoundException();
        }
    }

    /**
     * Start any Runnable
     *
     * @param players Class was implement Runnable
     */
    public void startThreads(Player... players) {
        Arrays.stream(players).forEach(player -> {
            Thread t = new Thread(player);
            t.start();
        });
    }

    /**
     * Synchronized method, this is the core of the controller, if isn't available wait(), if is available and isn't finished
     * set available false, say random char, match this with word of game. Then check if win the game, set available false and notify all the others threads
     *
     * @param player Class who implement Runnable
     * @throws InterruptedException if Thread crash
     */
    public synchronized void game(Player player) throws InterruptedException {
        if (!isAvailable()) {
            wait();
        } else if (!finished) {
            setAvailable(false);
            sayRandomChar(player);
            checkIfWin(player);
            setAvailable(true);
            notifyAll();
        }
    }

    /**
     * Check if the word have any special character if isn't have any special character set finished in true, show the winner on screen
     * and save a new Result whit Player data
     *
     * @param player class who implement Runnable
     */
    private void checkIfWin(Player player) {
        if (wordOfGame.indexOf(SPECIAL_CHARACTER) < 0) {
            this.finished = true;
            System.out.println("\nWinner " + player.getName() + " after " + charUsed + " chars");
            resultDao.saveResult(Result.builder()
                    .nameOfWinner(player.getName())
                    .charsUsed(charUsed)
                    .build());
        }
    }

    /**
     * Replace word of game with SPECIAL_CHARACTER
     *
     * @param lenght size of String
     */
    private void hideChars(Integer lenght) {
        char[] charArray = new char[lenght];
        Arrays.fill(charArray, SPECIAL_CHARACTER);
        this.wordOfGame = new String(charArray);
    }

    /**
     * Get a randomChar of alphabet, get the indexes of this char matches on initWordOfGame. Then, put this indexes into a list.
     * For each indexes, create a StringBuilder and replace for the randomChar in each index of this and then replace wordOfGame with this builder.
     * After that, increment char used and show info about the game in screen.
     *
     * @param player class who implements Runnable.
     */
    private void sayRandomChar(Player player) {
        char randomChar = getRandomChar();
        List<Integer> indexes = IntStream.range(0, initWordOfGame.length())
                .filter(i -> initWordOfGame.charAt(i) == randomChar)
                .boxed()
                .collect(Collectors.toList());
        indexes.forEach(index -> {
            StringBuilder wordOfGameBuilder = new StringBuilder(wordOfGame);
            wordOfGameBuilder.setCharAt(index, randomChar);
            this.wordOfGame = wordOfGameBuilder.toString();
        });
        this.charUsed++;
        System.out.println("\n" + player.getName() + " say '" + randomChar + "' and the game now is || " + wordOfGame + " || after " + this.charUsed + " char sayed.");
    }

    /**
     * Remove Char on String and return it
     *
     * @param stringToRemove String to replace
     * @param character      Char to remove
     * @return stringToRemove without character
     */
    private String removeCharOnWord(String stringToRemove, Character character) {
        return String.valueOf(stringToRemove).replace(String.valueOf(character), "");
    }

    /**
     * Put alphabet String into a list, shuffle it. Then obtain first element, if isn't exist throw a CharNotFoundException. After that, removes this element
     * of the alphabet string and return it.
     *
     * @return random char of alphabet String.
     */
    private char getRandomChar() {
        List<Character> characterList
                = this.alphabet.codePoints()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(characterList);
        char charToReturn = characterList.stream().findFirst().orElseThrow(CharNotFoundException::new);
        this.alphabet = removeCharOnWord(this.alphabet, charToReturn);
        return charToReturn;
    }

    /**
     * Get all words of data bases into a list, shuffle it and return first element.
     *
     * @return
     */
    private Optional<String> pickAWord() {
        ArrayList<String> words = wordsDao.getAll();
        Collections.shuffle(words);
        return words.stream().findFirst();
    }

    /**
     * Check if shared resource is available
     *
     * @return true if is available, false if isn't available.
     */
    private boolean isAvailable() {
        return this.available;
    }

    private void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Check if the game is finished
     *
     * @return true if is finished, false if isn't finished.
     */
    public boolean isFinished() {
        return this.finished;
    }
}
