package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                String email = rs.getString("email");
                String passwordHash = rs.getString("passwd_hash");
                Long employeeID = rs.getLong("employee_id");
                Boolean active = rs.getBoolean("active");

                return new Account(email, passwordHash, employeeID, active);
        }
}
