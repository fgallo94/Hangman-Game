package com.utn;

import com.utn.controller.HangmanController;
import com.utn.dto.Player;

public class App
{
    public static void main( String[] args )
    {
        Player playerOne = Player.builder()
                .name("Lance")
                .build();
        Player playerTwo = Player.builder()
                .name("Peter")
                .build();
        Player playerThree = Player.builder()
                .name("Morgan")
                .build();

        HangmanController hangmanController = new HangmanController();
        hangmanController.showResults();
        hangmanController.play(playerOne,playerTwo,playerThree);
    }
}
