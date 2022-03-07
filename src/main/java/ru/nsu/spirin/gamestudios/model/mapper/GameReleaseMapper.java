package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.GameRelease;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameReleaseMapper implements RowMapper<GameRelease> {

    @Override
    public GameRelease mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long gameID = rs.getLong("game_id");
        Long contractID = rs.getLong("contract_id");
        Long platformID = rs.getLong("platform_id");
        Date releaseDate = rs.getDate("release_date");
        Long cost = rs.getLong("cost");
        Long soldCount = rs.getLong("sold_count");

        return new GameRelease(gameID, contractID, platformID, releaseDate, cost, soldCount);
    }
}
