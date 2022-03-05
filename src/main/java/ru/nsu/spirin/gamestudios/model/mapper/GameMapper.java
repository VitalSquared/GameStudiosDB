package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Game;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long gameID = rs.getLong("game_id");
        Date startDate = rs.getDate("start_date");
        String devName = rs.getString("dev_name");
        String releaseName = rs.getString("release_name");
        Long expenses = rs.getLong("expenses");
        Long studioID = rs.getLong("studio_id");

        return new Game(gameID, startDate, devName, releaseName, expenses, studioID);
    }
}
