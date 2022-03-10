package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.model.mapper.TestAppMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestAppDAO extends JdbcDaoSupport {

    @Autowired
    public TestAppDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestApp> getAppsForTest(Long testID) {
        String sql = """
                        SELECT *
                        FROM test_app
                        WHERE test_id = ?;
                    """;
        return this.getJdbcTemplate().query(sql, new TestAppMapper(), testID);
    }

    public TestApp getAppByID(Long appID) {
        String sql = """
                        SELECT *
                        FROM test_app
                        WHERE app_id = ?;
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new TestAppMapper(), appID);
    }

    public void acceptApp(Long appID) {
        String sql = """
                        UPDATE test_app
                        SET result_id = 1
                        WHERE app_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, appID);
    }

    public void declineApp(Long appID) {
        String sql1 = """
                        DELETE FROM test_app__employee
                        WHERE app_id = ?;
                    """;
        this.getJdbcTemplate().update(sql1, appID);

        String sql2 = """
                        DELETE FROM test_app
                        WHERE app_id = ?;
                    """;
        this.getJdbcTemplate().update(sql2, appID);
    }

    public void addEmployee(Long appID, Long employeeID) {
        String sql = """
                        INSERT INTO test_app__employee (app_id, employee_id) VALUES 
                        (?, ?);
                    """;
        this.getJdbcTemplate().update(sql, appID, employeeID);
    }

    public void removeEmployee(Long appID, Long employeeID) {
        String sql = """
                        DELETE FROM test_app__employee
                        WHERE app_id = ? AND employee_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, appID, employeeID);
    }

    private void endResult(Long appID, Long resultID) {
        String sql = """
                        UPDATE test_app
                        SET result_id = ?
                        WHERE app_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, resultID, appID);
    }

    public void successEndResult(Long appID) {
        endResult(appID, 3L);
    }

    public void failureEndResult(Long appID) {
        endResult(appID, 2L);
    }
}
