package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Department {
    private Long departmentID;

    private Long studioID;

    private Long headID;

    @NotBlank
    private String name;

    private Boolean isRoot;
}
