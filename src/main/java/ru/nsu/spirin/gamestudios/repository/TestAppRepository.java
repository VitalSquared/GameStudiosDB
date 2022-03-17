package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.model.mapper.TestAppMapper;
import ru.nsu.spirin.gamestudios.repository.query.TestAppQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestAppRepository extends JdbcDaoSupport {

    @Autowired
    public TestAppRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestApp> findAllByTestID(Long testID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestAppQueries.QUERY_FIND_ALL_BY_GAME_ID, new TestAppMapper(), testID);
    }

    public List<TestApp> findAllByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestAppQueries.QUERY_FIND_ALL_BY_STUDIO_ID, new TestAppMapper(), studioID);
    }

    public TestApp findByID(Long appID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(TestAppQueries.QUERY_FIND_BY_ID, new TestAppMapper(), appID);
    }

    public TestApp findByTestIDAndStudioID(Long testID, Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(
                TestAppQueries.QUERY_FIND_BY_TEST_ID_AND_STUDIO_ID,
                new TestAppMapper(),
                testID, studioID
        );
    }

    public void updateResult(Long appID, Long resultID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_UPDATE_RESULT, resultID, appID);
    }

    public void saveAppEmployee(Long appID, Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_SAVE_APP_EMPLOYEE, appID, employeeID);
    }

    public void deleteAppEmployee(Long appID, Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_DELETE_APP_EMPLOYEE, appID, employeeID);
    }

    public void deleteAllAppEmployee(Long appID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_DELETE_ALL_APP_EMPLOYEE, appID);
    }

    public void deletePendingApps(Long testID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_DELETE_PENDING_APPS, testID);
    }

    public void deleteApp(Long appID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_DELETE_APP, appID);
    }

    public void deleteAllAppEmployeeByEmployeeID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestAppQueries.QUERY_DELETE_ALL_APP_EMPLOYEE_BY_EMPLOYEE_ID, employeeID);
    }
}
