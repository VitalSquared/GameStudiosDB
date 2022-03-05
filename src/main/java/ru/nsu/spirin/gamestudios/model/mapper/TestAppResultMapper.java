package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.TestAppResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestAppResultMapper implements RowMapper<TestAppResult> {

    @Override
    public TestAppResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long resultID = rs.getLong("result_id");
        String name = rs.getString("name");
        return new TestAppResult(resultID, name);
    }
}
