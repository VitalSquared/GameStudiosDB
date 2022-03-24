package ru.nsu.spirin.gamestudios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Genre {
    private Long genreID;

    @NotBlank
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Genre)) return false;
        Genre that = (Genre) object;
        return this.genreID.equals(that.genreID);
    }
}
