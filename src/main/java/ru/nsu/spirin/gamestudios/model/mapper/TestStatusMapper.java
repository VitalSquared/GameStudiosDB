package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.TestStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestStatusMapper implements RowMapper<TestStatus> {

    @Override
    public TestStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long statusID = rs.getLong("status_id");
        String name = rs.getString("name");
        return new TestStatus(statusID, name);
    }
}
