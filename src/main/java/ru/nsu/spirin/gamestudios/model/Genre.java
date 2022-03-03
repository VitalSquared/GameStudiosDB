package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
    private Long genreID;
    private String name;
}
