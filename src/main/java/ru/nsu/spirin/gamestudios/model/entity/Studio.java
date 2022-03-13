package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Studio {
    private Long studioID;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;
}
