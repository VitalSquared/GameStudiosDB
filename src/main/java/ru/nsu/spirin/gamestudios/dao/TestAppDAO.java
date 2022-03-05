package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class TestAppDAO extends JdbcDaoSupport {

    @Autowired
    public TestAppDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

}
