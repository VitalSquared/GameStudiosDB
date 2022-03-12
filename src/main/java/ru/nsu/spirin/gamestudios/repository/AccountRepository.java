package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.AccountMapper;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.repository.query.AccountQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class AccountRepository extends JdbcDaoSupport {

    @Autowired
    public AccountRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Account findByID(String id) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(AccountQueries.QUERY_FIND_BY_ID,
                new AccountMapper(),
                id
        );
    }

    public List<Account> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(AccountQueries.QUERY_FIND_ALL,
                new AccountMapper()
        );
    }

    public Account findByEmployeeID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(AccountQueries.QUERY_FIND_BY_EMPLOYEE_ID,
                new AccountMapper(),
                employeeID
        );
    }

    public void save(Account account) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(AccountQueries.QUERY_SAVE,
                account.getEmail(), account.getPasswordHash(), account.getEmployeeID()
        );
    }

    public void update(String id, Account account) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(AccountQueries.QUERY_UPDATE,
                account.getEmail(), account.getPasswordHash(), id
        );
    }
}
