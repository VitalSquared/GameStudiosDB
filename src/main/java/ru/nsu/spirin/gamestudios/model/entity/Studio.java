package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Studio {
    private Long studioID;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
