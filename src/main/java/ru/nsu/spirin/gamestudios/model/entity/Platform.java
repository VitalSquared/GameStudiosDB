package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Platform {
    private Long platformID;

    @NotEmpty
    private String name;

    @Min(0) @Max(100)
    private Long percent;
}
