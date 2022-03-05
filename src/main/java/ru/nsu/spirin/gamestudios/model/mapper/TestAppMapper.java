package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestAppMapper implements RowMapper<TestApp> {

    @Override
    public TestApp mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long appID = rs.getLong("app_id");
        Long studioID = rs.getLong("studio_id");
        Long testID = rs.getLong("test_id");
        Long resultID = rs.getLong("result_id");
        return new TestApp(appID, studioID, testID, resultID);
    }
}