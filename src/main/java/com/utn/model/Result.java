package com.utn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Simple POJO of Results
 */
@Builder
@Data
@AllArgsConstructor
public class Result {
    private String nameOfWinner;
    private Timestamp dateTime;
    private Integer charsUsed;

    @Override
    public String toString() {
        return "|| Winner ='" + nameOfWinner + '\'' +
                ",dateTime ='" + dateTime + "' | charsUsed=" + charsUsed +
                "  \n";
    }
}