package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
public class GameRelease {
    private Long gameID;

    private Long contractID;

    private Long platformID;

    @NotNull
    private Date releaseDate;

    @NotNull @Min(0) @Max(999)
    private Long cost;

    @NotNull @Min(0)
    private Long soldCount;
}
