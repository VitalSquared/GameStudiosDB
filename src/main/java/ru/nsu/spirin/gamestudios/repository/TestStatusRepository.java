package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestStatus;
import ru.nsu.spirin.gamestudios.model.mapper.TestStatusMapper;
import ru.nsu.spirin.gamestudios.repository.query.TestStatusQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestStatusRepository extends JdbcDaoSupport {

    @Autowired
    public TestStatusRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestStatus> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestStatusQueries.QUERY_FIND_ALL, new TestStatusMapper());
    }

    public TestStatus findByID(Long statusID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(TestStatusQueries.QUERY_FIND_BY_ID, new TestStatusMapper(), statusID);
    }
}
