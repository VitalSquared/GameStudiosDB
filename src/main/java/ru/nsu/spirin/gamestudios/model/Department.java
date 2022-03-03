package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {
    private Long departmentID;
    private Long studioID;
    private Long headID;
    private String name;
    private Boolean isRoot;
}
