package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Platform;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlatformMapper implements RowMapper<Platform> {

    @Override
    public Platform mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long platformID = rs.getLong("platform_id");
        String name = rs.getString("name");
        Long percent = rs.getLong("percent");
        return new Platform(platformID, name, percent);
    }
}
