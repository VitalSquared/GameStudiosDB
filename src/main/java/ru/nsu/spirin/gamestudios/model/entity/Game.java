package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Game {
    private Long gameID;

    @NotNull
    private Date startDate;

    @NotEmpty
    private String devName;

    @NotEmpty
    private String releaseName;

    @NotNull @Min(0)
    private Long expenses;

    private Long studioID;
}
