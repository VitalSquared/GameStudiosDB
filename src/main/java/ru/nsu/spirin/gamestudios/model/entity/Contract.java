package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Contract {
    private Long contractID;

    @NotNull
    private Date date;

    @Min(0) @Max(100)
    private Long percent;

    private Long testID;
}
