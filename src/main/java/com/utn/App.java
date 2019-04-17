package com.utn;

import com.utn.controller.HangmanController;
import com.utn.dto.Player;

public class App {
    /**
     * Init HangmanController, build 3 Players, show last winners of the game, set up the game and start threads.
     *
     * @param args
     */
    public static void main(String[] args) {
        HangmanController hangmanController = new HangmanController();
        Player playerOne = Player.builder()
                .name("Lance")
                .hangmanController(hangmanController)
                .build();
        Player playerTwo = Player.builder()
                .name("Peter")
                .hangmanController(hangmanController)
                .build();
        Player playerThree = Player.builder()
                .name("Morgan")
                .hangmanController(hangmanController)
                .build();

        hangmanController.showResults();
        hangmanController.play();
        hangmanController.startThreads(playerOne, playerTwo, playerThree);
    }
}
