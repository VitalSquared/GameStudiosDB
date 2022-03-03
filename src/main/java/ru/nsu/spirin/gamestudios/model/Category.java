package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    private Long categoryID;
    private String name;
}
