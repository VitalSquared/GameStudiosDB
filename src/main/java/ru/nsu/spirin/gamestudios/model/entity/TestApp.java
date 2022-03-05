package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestApp {
    private Long appID;
    private Long studioID;
    private Long testID;
    private Long resultID;
}
