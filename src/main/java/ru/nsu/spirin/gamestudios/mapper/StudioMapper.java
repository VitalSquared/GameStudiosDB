package ru.nsu.spirin.gamestudios.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.Studio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudioMapper implements RowMapper<Studio> {

    @Override
    public Studio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long studioID = rs.getLong("studio_id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        return new Studio(studioID, name, address);
    }
}
