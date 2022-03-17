package ru.nsu.spirin.gamestudios.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nsu.spirin.gamestudios.model.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long departmentID = rs.getLong("department_id");
        Long studioID = rs.getLong("studio_id");
        Object headIDObject = rs.getObject("head_id");
        String name = rs.getString("name");
        Boolean isRoot = rs.getBoolean("is_root");

        Long headID = headIDObject == null ? null : (Long)headIDObject;

        return new Department(departmentID, studioID, headID, name, isRoot);
    }
}
