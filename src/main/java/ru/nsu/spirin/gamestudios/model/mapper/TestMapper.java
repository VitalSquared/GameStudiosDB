package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMapper implements RowMapper<Test> {

    @Override
    public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long testID = rs.getLong("test_id");
        Date startDate = rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");
        Long grand = rs.getLong("grand");
        Long minStudiosNum = rs.getLong("min_studios_num");
        Date appsDeadline = rs.getDate("apps_deadline");
        Long statusID = rs.getLong("status_id");

        return new Test(testID, startDate, endDate, grand, minStudiosNum, appsDeadline, statusID);
    }
}
