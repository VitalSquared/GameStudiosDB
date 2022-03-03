package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Platform {
    private Long platformID;
    private String name;
    private Long percent;
}
