package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Test {
    private Long testID;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull @Min(0) @Max(999999999)
    private Long grand;

    @NotNull @Min(1)
    private Long minStudiosNum;

    @NotNull
    private Date appsDeadline;

    private Long statusID;
}
