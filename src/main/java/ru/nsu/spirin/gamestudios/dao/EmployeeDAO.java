package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.mapper.EmployeeMapper;
import ru.nsu.spirin.gamestudios.model.Employee;

import javax.sql.DataSource;
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
                                        dev.category_id, dev.department_id,
                                        case when (dir.studio_id is not null) then
                                            dir.studio_id
                                        else
                                            dep.studio_id
                                        end as studio_id
                                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                                         on e.employee_id = dev.employee_id
                                WHERE e.employee_id = ? and e.employee_id != 0;
                            """;
        return this.getJdbcTemplate().queryForObject(sql, new EmployeeMapper(), params);
    }

    public List<Employee> getEmployees() {
        String sqlSent = """
                            SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                                        dev.category_id, dev.department_id,
                                        case when (dir.studio_id is not null) then
                                            dir.studio_id
                                        else
                                            dep.studio_id
                                        end as studio_id
                            FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                                         on e.employee_id = dev.employee_id;
                         """;
        return this.getJdbcTemplate().query(sqlSent, new EmployeeMapper());
    }
}
