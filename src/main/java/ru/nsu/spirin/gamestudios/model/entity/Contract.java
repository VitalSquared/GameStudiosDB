package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Contract {
    private Long contractID;
    private Date date;
    private Long percent;
    private Long testID;
}
