package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Test {
    private Long testID;
    private Date startDate;
    private Date endDate;
    private Long grand;
    private Long minStudiosNum;
    private Date appsDeadline;
    private Long statusID;
}
