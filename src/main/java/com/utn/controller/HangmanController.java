package com.utn.controller;

import com.utn.dao.ResultDao;
import com.utn.dao.WordsDao;
import com.utn.dto.Player;
import com.utn.exception.WordNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class HangmanController {

    private ResultDao resultDao;
    private WordsDao wordsDao;
    private String word;

    public HangmanController(){
        resultDao = new ResultDao();
        wordsDao = new WordsDao();
    }

    public void showResults() {
        System.out.println("----Last Winners Are ----\n");
        System.out.println(resultDao.getResults().toString());
        System.out.println("-----------------------\n");
    }

    public void play(Player... players) {
        this.word = pickAWord();
        Arrays.stream(players).forEach(player -> player.run());
    }

    public synchronized void game(){
        Optional<String> wordOptional = Optional.ofNullable(word);
        if(wordOptional.isPresent()){

        }else{
            System.out.println("Something bad happens and doesn't have any words");
        }
    }

    private String pickAWord() {
        ArrayList<String> words = wordsDao.getAll();
        Collections.shuffle(words);
        return words.stream().findFirst().orElseThrow(() -> new WordNotFoundException());
    }

}
