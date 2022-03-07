package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Test;
import ru.nsu.spirin.gamestudios.model.mapper.TestMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestDAO extends JdbcDaoSupport {

    @Autowired
    public TestDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Test> getAllTests() {
        String sql = "SELECT * FROM test;";
        return this.getJdbcTemplate().query(sql, new TestMapper());
    }

    public Test getTestByID(Long testID) {
        String sql = "SELECT * FROM test WHERE test_id = ?";
        return this.getJdbcTemplate().queryForObject(sql, new TestMapper(), testID);
    }

    public void newTest(Test test) {
        String sql = """
                        INSERT INTO test (test_id, start_date, end_date, grand, min_studios_num, apps_deadline, status_id) VALUES
                             (default, ?, ?, ?, ?, ?, ?)
                    """;
        this.getJdbcTemplate().update(sql, test.getStartDate(), test.getEndDate(), test.getGrand(), test.getMinStudiosNum(), test.getAppsDeadline(), test.getStatusID());
    }

    public void updateTest(Long id, Test test) {
        String sql = """
                                    UPDATE test
                                    SET start_date = ?, end_date = ?, grand = ?, min_studios_num = ?, apps_deadline = ?, status_id = ?
                                    WHERE test_id = ?
                                """;
        this.getJdbcTemplate().update(sql, test.getStartDate(), test.getEndDate(), test.getGrand(), test.getMinStudiosNum(), test.getAppsDeadline(), test.getStatusID(), id);
    }
}
