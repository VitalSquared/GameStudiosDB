package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.EmployeeMapper;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.repository.filtration.Filtration;
import ru.nsu.spirin.gamestudios.repository.query.EmployeeQueries;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class EmployeeRepository extends JdbcDaoSupport {
    @Autowired
    public EmployeeRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Employee findByID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(EmployeeQueries.QUERY_FIND_BY_ID, new EmployeeMapper(), employeeID);
    }

    public Long countDevelopersByID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(EmployeeQueries.QUERY_COUNT_DEVELOPERS_BY_ID, Long.class, employeeID);
    }

    public Long findDirectorStudioByID(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(EmployeeQueries.QUERY_FIND_DIRECTOR_STUDIO_BY_ID,
                (ResultSet rs, int rowNum) -> rs.getLong("studio_id"),
                employeeID
        );
    }

    public List<Employee> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(EmployeeQueries.QUERY_FIND_ALL, new EmployeeMapper());
    }

    public List<Employee> findAllByStudioIDWithFiltration(Long studioID, Filtration filtration, String sortField, String sortDir) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        String dirStudio = "dir.studio_id = ?", depStudio = "dep.studio_id = ?";
        Object[] params = new Object[] { studioID, studioID };
        if (studioID == -1) {
            dirStudio = "TRUE";
            depStudio = "TRUE";
            params = new Object[] {};
        }
        String orderByQuery = "";
        if (null != sortField && !sortField.isEmpty() && sortDir != null && !sortDir.isEmpty()) {
            String dir = sortDir.equalsIgnoreCase("ASC") ? "ASC" : sortDir.equalsIgnoreCase("DESC") ? "DESC" : "";
            if (!dir.isEmpty()) {
                orderByQuery = "ORDER BY e." + sortField + " " + dir;
            }
        }
        return this.getJdbcTemplate().query(
                String.format(
                        EmployeeQueries.QUERY_FIND_ALL_BY_STUDIO_ID_WITH_FILTRATION,
                        dirStudio,
                        depStudio,
                        filtration.buildQuery(),
                        orderByQuery
                ),
                new EmployeeMapper(),
                params);
    }

    public List<Employee> findAllByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(
                String.format(EmployeeQueries.QUERY_FIND_ALL_BY_STUDIO_ID),
                new EmployeeMapper(),
                studioID, studioID);
    }

    public List<Employee> findByGameID(Long gameID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(EmployeeQueries.QUERY_FIND_ALL_BY_GAME_ID, new EmployeeMapper(), gameID);
    }

    public List<Employee> findByDepartmentID(Long departmentID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(EmployeeQueries.QUERY_FIND_ALL_BY_DEPARTMENT_ID, new EmployeeMapper(), departmentID);
    }

    public List<Employee> findByTestAppID(Long appID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(EmployeeQueries.QUERY_FIND_ALL_BY_TEST_APP_ID, new EmployeeMapper(), appID);
    }

    public Long save(Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.getJdbcTemplate().update(
                con -> {
                    PreparedStatement ps =con.prepareStatement(EmployeeQueries.QUERY_SAVE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, employee.getFirstName());
                    ps.setString(2, employee.getLastName());
                    ps.setDate(3, employee.getBirthDate());
                    ps.setObject(4, employee.getActive());
                    return ps;
                },
                keyHolder
        );

        return null == keyHolder.getKey() ? null : keyHolder.getKey().longValue();
    }

    public void saveDeveloper(Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_SAVE_DEVELOPER, employee.getEmployeeID(), employee.getCategoryID(), employee.getDepartmentID());
    }

    public void saveDirector(Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_SAVE_DIRECTOR, employee.getEmployeeID(), employee.getStudioID());
    }

    public void update(Long employeeID, Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_UPDATE_EMPLOYEE,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthDate(),
                employee.getActive(),
                employeeID
        );
    }

    public void updateDevelopersCategory(Long oldCategory, Long newCategory) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_UPDATE_DEVELOPER_CATEGORY,
                newCategory,
                oldCategory
        );
    }

    public void updateDevelopersDepartment(Long oldDepartment, Long newDepartment) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_UPDATE_DEVELOPER_DEPARTMENT,
                newDepartment,
                oldDepartment
        );
    }

    public void delete(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_DELETE_DIRECTOR, employeeID);
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_DELETE_DEVELOPER, employeeID);
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_DELETE_EMPLOYEE, employeeID);
    }

    public void upsertDeveloper(Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_UPSERT_DEVELOPER,
                employee.getEmployeeID(),
                employee.getCategoryID(),
                employee.getDepartmentID(),
                employee.getCategoryID(),
                employee.getDepartmentID()
        );
    }

    public void upsertDirector(Employee employee) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_UPSERT_DIRECTOR,
                employee.getEmployeeID(),
                employee.getStudioID(),
                employee.getStudioID()
        );
    }

    public void deleteDeveloper(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_DELETE_DEVELOPER, employeeID);
    }

    public void deleteDirector(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(EmployeeQueries.QUERY_DELETE_DIRECTOR, employeeID);
    }

    public List<Long> findAllDirectorsByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return new ArrayList<>();
        }
        return this.getJdbcTemplate().query(EmployeeQueries.QUERY_FIND_ALL_DIRECTORS_BY_STUDIO_ID,
                (rs, rowNum) -> rs.getLong(1),
                studioID
        );
    }
}
