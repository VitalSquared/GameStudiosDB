package ru.nsu.spirin.gamestudios.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.Employee;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long employeeID = rs.getLong("employee_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date birthDate = rs.getDate("birth_date");
        Long categoryID = rs.getLong("category_id");
        Long studioID = rs.getLong("studio_id");
        Long departmentID = rs.getLong("department_id");
        Boolean active = rs.getBoolean("active");

        return new Employee(employeeID, firstName, lastName, birthDate, categoryID, studioID, departmentID, active);
    }
}
