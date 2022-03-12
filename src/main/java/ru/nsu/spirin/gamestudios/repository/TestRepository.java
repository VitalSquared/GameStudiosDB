package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.Test;
import ru.nsu.spirin.gamestudios.model.mapper.TestMapper;
import ru.nsu.spirin.gamestudios.repository.query.TestQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestRepository extends JdbcDaoSupport {

    @Autowired
    public TestRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Test> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestQueries.QUERY_FIND_ALL, new TestMapper());
    }

    public List<Test> findAllWithResults() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestQueries.QUERY_FIND_ALL_WITH_RESULTS, new TestMapper());
    }

    public Test findByID(Long testID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(TestQueries.QUERY_FIND_BY_ID, new TestMapper(), testID);
    }

    public void save(Test test) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_SAVE,
                test.getStartDate(),
                test.getEndDate(),
                test.getGrand(),
                test.getMinStudiosNum(),
                test.getAppsDeadline(),
                test.getStatusID()
        );
    }

    public void update(Long id, Test test) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_UPDATE,
                test.getStartDate(),
                test.getEndDate(),
                test.getGrand(),
                test.getMinStudiosNum(),
                test.getAppsDeadline(),
                id);
    }

    public void updateTestStatus(Long id, Long statusValue) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_UPDATE_TEST_STATUS, statusValue, id);
    }

    public void saveTestGenre(Long testID, Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_SAVE_TEST_GENRE, testID, genreID);
    }

    public void deleteTestGenre(Long testID, Long genreID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_DELETE_TEST_GENRE, testID, genreID);
    }

    public void deleteAllTestGenre(Long id) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_DELETE_ALL_TEST_GENRE, id);
    }

    public void delete(Long id) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(TestQueries.QUERY_DELETE, id);
    }
}
