package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class GameRelease {
    private Long gameID;
    private Long contractID;
    private Long platformID;
    private Date releaseDate;
    private Long cost;
    private Long soldCount;
}
