package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.UserMapper;
import ru.nsu.spirin.gamestudios.model.User;

import javax.sql.DataSource;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport {

    @Autowired
    public UserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public User findUserAccount(String userName) {
        // SELECT ... FROM account acc WHERE acc.email = ?
        String sql = UserMapper.BASE_SQL + " WHERE acc.email = ? ";

        Object[] params = new Object[]{userName};
        UserMapper mapper = new UserMapper();
        try {
            return this.getJdbcTemplate().queryForObject(sql, params, mapper);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
