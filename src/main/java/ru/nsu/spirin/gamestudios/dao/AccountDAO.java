package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.AccountMapper;
import ru.nsu.spirin.gamestudios.model.account.Account;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class AccountDAO extends JdbcDaoSupport {

    @Autowired
    public AccountDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Account findUserAccount(String userName) {
        // SELECT ... FROM account acc WHERE acc.email = ?
        String sql = """
                    SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                    FROM (account NATURAL JOIN employee) acc  WHERE acc.email = ?;
        """;

        Object[] params = new Object[]{userName};
        AccountMapper mapper = new AccountMapper();
        try {
            return this.getJdbcTemplate().queryForObject(sql, params, mapper);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Account findUserAccountByEmployeeID(Long employeeID) {
        String sql = """
                    SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                    FROM (account NATURAL JOIN employee) acc  WHERE acc.employee_id = ?;
        """;

        Object[] params = new Object[]{employeeID};
        AccountMapper mapper = new AccountMapper();
        try {
            return this.getJdbcTemplate().queryForObject(sql, params, mapper);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Map<Long, String> getEmails() {
        Map<Long, String> map = new HashMap<>();

        String sql = """
                        SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                        FROM (account NATURAL JOIN employee) acc;
                    """;

        List<Account> accounts = this.getJdbcTemplate().query(sql, new AccountMapper());
        for (var account : accounts) {
            map.put(account.getEmployeeID(), account.getLogin());
        }
        return map;
    }
}
