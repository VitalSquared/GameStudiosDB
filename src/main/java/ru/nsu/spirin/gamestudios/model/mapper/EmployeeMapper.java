package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Employee;

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
        Object categoryIDObject = rs.getObject("category_id");
        Long studioID = rs.getLong("studio_id");
        Object departmentIDObject = rs.getObject("department_id");
        Boolean active = rs.getBoolean("active");

        Long categoryID = categoryIDObject == null ? null : (Long)categoryIDObject;
        Long departmentID = departmentIDObject == null ? null : (Long)departmentIDObject;

        return new Employee(employeeID, firstName, lastName, birthDate, categoryID, studioID, departmentID, active);
    }
}
