package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.DepartmentMapper;
import ru.nsu.spirin.gamestudios.model.entity.Department;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class DepartmentDAO extends JdbcDaoSupport {

    @Autowired
    public DepartmentDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Department getDepartmentByID(Long departmentID) {
        String sql = """
                        SELECT *
                        FROM department dep
                        WHERE dep.department_id = ?;
                    """;
        return this.getJdbcTemplate().queryForObject(sql, new DepartmentMapper(), departmentID);
    }

    public List<Department> getAllDepartmentsOfStudio(Long studioID) {
        String sql = """
                        SELECT * 
                        FROM department dep
                        WHERE dep.studio_id = ?;
                    """;
        return this.getJdbcTemplate().query(sql, new DepartmentMapper(), studioID);
    }

    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM department;";
        return this.getJdbcTemplate().query(sql, new DepartmentMapper());
    }

    public void newDepartment(Department department) throws SQLException {
        if (department.getName() == null) {
            return;
        }
        Long headID = department.getHeadID() == -1 ? null : department.getHeadID();
        String sql = """
                        INSERT INTO department (department_id, studio_id, name, head_id, is_root) VALUES
                             (default, ?, ?, ?, false);
                    """;
        this.getJdbcTemplate().update(sql, department.getStudioID(), department.getName(), headID);
    }

    public void updateDepartment(Long id, Department department) throws SQLException {
        if (department.getName() == null) {
            return;
        }
        Long headID = department.getHeadID() == -1 ? null : department.getHeadID();
        String sql = """
                        UPDATE department
                        SET name = ?, head_id = ?
                        WHERE department_id = ?;
                    """;
        this.getJdbcTemplate().update(sql, department.getName(), headID, id);
    }
}
