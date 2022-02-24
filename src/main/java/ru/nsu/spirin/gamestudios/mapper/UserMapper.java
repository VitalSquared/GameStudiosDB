package ru.nsu.spirin.gamestudios.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String email = rs.getString("email");
                String passwordHash = rs.getString("passwd_hash");
                Long employeeID = rs.getLong("employee_id");
                boolean active = rs.getBoolean("active");

                return new User(email, passwordHash, employeeID, active);
        }
}
