package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Game {
    private Long gameID;
    private Date startDate;
    private String devName;
    private String releaseName;
    private Long expenses;
    private Long studioID;
}
