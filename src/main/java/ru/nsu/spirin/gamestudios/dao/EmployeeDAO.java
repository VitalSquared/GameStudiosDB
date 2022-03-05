package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.EmployeeMapper;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@Transactional
public class EmployeeDAO extends JdbcDaoSupport {
    @Autowired
    public EmployeeDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Employee getEmployeeByID(Long employeeID) {
        Object[] params = new Object[]{ employeeID };
        String sql = """
                                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                                        dev.category_id, dev.department_id, e.active,
                                        case when (dir.studio_id is not null) then
                                            dir.studio_id
                                        else
                                            dep.studio_id
                                        end as studio_id
                                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                                         on e.employee_id = dev.employee_id
                                WHERE e.employee_id = ?;
                            """;
        Employee employee = this.getJdbcTemplate().queryForObject(sql, new EmployeeMapper(), params);
        employee.setEmployeeType(employee.getDepartmentID() == null ? "director" : "developer");
        return employee;
    }

    public List<Employee> getEmployees() {
        String sqlSent = """
                            SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                                        dev.category_id, dev.department_id, e.active,
                                        case when (dir.studio_id is not null) then
                                            dir.studio_id
                                        else
                                            dep.studio_id
                                        end as studio_id
                            FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                                         on e.employee_id = dev.employee_id
                            WHERE e.employee_id != 0;
                         """;
        return this.getJdbcTemplate().query(sqlSent, new EmployeeMapper());
    }

    public List<Employee> getEmployeesByStudio(Long studioID) {
        String sqlSent = """
                            SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                                        dev.category_id, dev.department_id, e.active,
                                        case when (dir.studio_id is not null) then
                                            dir.studio_id
                                        else
                                            dep.studio_id
                                        end as studio_id
                            FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                                         on e.employee_id = dev.employee_id
                            WHERE e.employee_id != 0 AND (
                                (dir.studio_id is not null AND dir.studio_id = ?) OR (dir.studio_id is null AND dep.studio_id = ?)
                            );
                         """;
        return this.getJdbcTemplate().query(sqlSent, new EmployeeMapper(), studioID, studioID);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentID) {
        String sqlSent = """
                            SELECT e.employee_id, e.first_name, e.last_name, e.birth_date, e.category_id, e.department_id, 
                                 e.studio_id, e.active
                            FROM (employee NATURAL JOIN 
                                    (
                                        SELECT dev.category_id, dev.department_id, dev.studio_id 
                                        FROM (developer NATURAL JOIN department) dev
                                    ) as dev1
                            ) e
                            WHERE e.employee_id != 0 AND e.department_id = ?;
                         """;
        return this.getJdbcTemplate().query(sqlSent, new EmployeeMapper(), departmentID);
    }

    public void newEmployee(Employee employee, Account account) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsert1 = """
                                    INSERT INTO employee (employee_id, first_name, last_name, birth_date, active) VALUES
                                         (default, ?, ?, ?, ?)
                                         RETURNING employee_id;
                                """;
        this.getJdbcTemplate().update(
                con -> {
                    PreparedStatement ps =con.prepareStatement(sqlInsert1, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, employee.getFirstName());
                    ps.setString(2, employee.getLastName());
                    ps.setDate(3, employee.getBirthDate());
                    ps.setObject(4, employee.getActive());
                    return ps;
                },
                keyHolder
        );

        if ("developer".equalsIgnoreCase(employee.getEmployeeType())) {
            String sqlInsert2 = """
                                        INSERT INTO developer (employee_id, category_id, department_id) VALUES
                                            (?, ?, ?);
                                    """;
            this.getJdbcTemplate().update(sqlInsert2, keyHolder.getKey().longValue(), employee.getCategoryID(), employee.getDepartmentID());
        }
        else {
            String sqlInsert2 = """
                                        INSERT INTO director (employee_id, studio_id) VALUES
                                            (?, ?);
                                    """;
            this.getJdbcTemplate().update(sqlInsert2, keyHolder.getKey().longValue(), employee.getStudioID());
        }

        String login = account.getLogin().split("@")[0];
        String passwd = new BCryptPasswordEncoder(12).encode(login);
        String sqlInsert3 = """
                            INSERT INTO account (email, passwd_hash, employee_id) VALUES 
                                                    (?, ?, ?);
                        """;
        this.getJdbcTemplate().update(sqlInsert3, account.getLogin(), passwd, keyHolder.getKey().longValue());
    }

    public void updateEmployee(Long id, Employee employee, Account account) throws SQLException {
        String sql1 = """
                        UPDATE employee
                        SET first_name = ?, last_name = ?, birth_date = ?, active = ?
                        WHERE employee_id = ?;
                    """;
        this.getJdbcTemplate().update(sql1, employee.getFirstName(), employee.getLastName(), employee.getBirthDate(), employee.getActive(), id);

        if ("developer".equalsIgnoreCase(employee.getEmployeeType())) {
            String sql2 = """
                                DELETE FROM director
                                WHERE employee_id = ?
                            """;
            this.getJdbcTemplate().update(sql2, id);

            String sql3 = """
                                INSERT INTO developer (employee_id, category_id, department_id) VALUES
                                    (?, ?, ?)
                                ON CONFLICT (employee_id) DO UPDATE 
                                    SET category_id = ?, department_id = ?
                            """;
            this.getJdbcTemplate().update(sql3, id, employee.getCategoryID(), employee.getDepartmentID(), employee.getCategoryID(), employee.getDepartmentID());
        }
        else {
            String sql2 = """
                                        DELETE FROM developer
                                        WHERE employee_id = ?
                                    """;
            this.getJdbcTemplate().update(sql2, id);

            String sql3 = """
                                INSERT INTO director (employee_id, studio_id) VALUES
                                    (?, ?)
                                ON CONFLICT (employee_id) DO UPDATE 
                                    SET studio_id = ?;
                            """;
            this.getJdbcTemplate().update(sql3, id, employee.getStudioID(), employee.getStudioID());
        }

        if ("reset".equalsIgnoreCase(account.getPasswordResetState())) {
            String login = account.getLogin().split("@")[0];
            String passwd = new BCryptPasswordEncoder(12).encode(login);
            String sqlInsert3 = """
                            UPDATE account
                            SET email = ?, passwd_hash = ?
                            WHERE employee_id = ?
                        """;
            this.getJdbcTemplate().update(sqlInsert3, account.getLogin(), passwd, id);
        }
        else {
            String sqlInsert3 = """
                            UPDATE account
                            SET email = ?
                            WHERE employee_id = ?
                        """;
            this.getJdbcTemplate().update(sqlInsert3, account.getLogin(), id);
        }
    }
}
