package ru.nsu.spirin.gamestudios.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Studio {
    private Long studioID;
    private String name;
    private String address;
}
