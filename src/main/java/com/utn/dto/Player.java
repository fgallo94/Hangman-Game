package com.utn.dto;

import com.utn.controller.HangmanController;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Player implements Runnable {
    private String name;
    private HangmanController hangmanController;

    @Override
    public void run() {
        while (!hangmanController.isFinished()) {
            try {
                hangmanController.game(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
