package com.utn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Simple POJO of Results
 */
@Builder
@Data
@AllArgsConstructor
public class Result {
    private String nameOfWinner;
    private Integer charsUsed;

    @Override
    public String toString() {
        return  "|| Winner ='" + nameOfWinner + '\'' +
                ", charsUsed=" + charsUsed +
                "  ";
    }
}