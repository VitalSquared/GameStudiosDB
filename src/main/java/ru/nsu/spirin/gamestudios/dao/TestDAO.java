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

    public List<Test> getResultedTests() {
        String sql = """
                        SELECT * 
                        FROM test
                        WHERE status_id = 3;
                    """;
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
                                    SET start_date = ?, end_date = ?, grand = ?, min_studios_num = ?, apps_deadline = ?
                                    WHERE test_id = ?
                                """;
        this.getJdbcTemplate().update(sql, test.getStartDate(), test.getEndDate(), test.getGrand(), test.getMinStudiosNum(), test.getAppsDeadline(), id);
    }

    public void addGenre(Long testID, Long genreID) {
        String sql = """
                        INSERT INTO test__genre (test_id, genre_id) VALUES
                             (?, ?)
                    """;
        this.getJdbcTemplate().update(sql, testID, genreID);
    }

    public void removeGenre(Long testID, Long genreID) {
        String sql = """
                        DELETE FROM test__genre
                        WHERE test_id = ? AND genre_id = ?
                    """;
        this.getJdbcTemplate().update(sql, testID, genreID);
    }

    public void startTest(Long id) {
        String sql1 = """
                            DELETE FROM test_app
                            WHERE test_id = ? AND result_id = 0;
                        """;
        this.getJdbcTemplate().update(sql1, id);

        String sql2 = """
                        UPDATE test
                        SET status_id = 1
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql2, id);
    }

    public void finishTest(Long id) {
        String sql = """
                        UPDATE test
                        SET status_id = 2
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, id);
    }

    public void resultsReadyTest(Long id) {
        String sql1 = """
                            UPDATE test_app
                            SET result_id = 2
                            WHERE test_id = ? AND result_id = 1;
                        """;
        this.getJdbcTemplate().update(sql1, id);

        String sql2 = """
                        UPDATE test
                        SET status_id = 3
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql2, id);
    }

    public void cancelTest(Long id) {
        String sql11 = """
                        DELETE from test__genre
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql11, id);

        String sql12 = """
                        DELETE from test_app__employee
                        WHERE app_id in (
                            SELECT app_id
                            FROM test_app
                            WHERE test_id = ?
                        );
                    """;
        this.getJdbcTemplate().update(sql12, id);

        String sql13 = """
                        DELETE from test_app
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql13, id);

        String sql2 = """
                        UPDATE test
                        SET status_id = 4
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql2, id);
    }

    public void deleteTest(Long id) {
        String sql = """
                        DELETE FROM test
                        WHERE test_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, id);
    }
}
