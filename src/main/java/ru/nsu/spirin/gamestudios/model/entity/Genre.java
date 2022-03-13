package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Genre {
    private Long genreID;

    @NotEmpty
    private String name;
}
