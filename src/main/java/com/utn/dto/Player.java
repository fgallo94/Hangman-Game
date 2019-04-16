package com.utn.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Player implements Runnable{
    private String name;

    @Override
    public void run() {

    }
}
