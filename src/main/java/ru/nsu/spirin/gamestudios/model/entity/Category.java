package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Category {
    private Long categoryID;

    @NotEmpty
    private String name;
}
