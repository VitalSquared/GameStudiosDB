package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.entity.TestAppResult;
import ru.nsu.spirin.gamestudios.model.mapper.TestAppResultMapper;
import ru.nsu.spirin.gamestudios.repository.query.TestAppResultQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TestAppResultRepository extends JdbcDaoSupport {

    @Autowired
    public TestAppResultRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<TestAppResult> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(TestAppResultQueries.QUERY_FIND_ALL, new TestAppResultMapper());
    }

    public TestAppResult findByID(Long resultID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(TestAppResultQueries.QUERY_FIND_BY_ID, new TestAppResultMapper(), resultID);
    }
}
