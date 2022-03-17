package ru.nsu.spirin.gamestudios.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.mapper.DepartmentMapper;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.repository.query.DepartmentQueries;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class DepartmentRepository extends JdbcDaoSupport {

    @Autowired
    public DepartmentRepository(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Department findByID(Long departmentID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(DepartmentQueries.QUERY_FIND_BY_ID, new DepartmentMapper(), departmentID);
    }

    public Department findRootDepartmentByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().queryForObject(DepartmentQueries.QUERY_FIND_ROOT_DEPARTMENT_BY_STUDIO_ID,
                new DepartmentMapper(), studioID);
    }

    public List<Department> findAllByStudioID(Long studioID) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(DepartmentQueries.QUERY_FIND_ALL_BY_STUDIO_ID, new DepartmentMapper(), studioID);
    }

    public List<Department> findAllByStudioIDSorted(Long studioID, String sortField, String sortDir) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        String orderByQuery = "";
        if (null != sortField && !sortField.isEmpty() && sortDir != null && !sortDir.isEmpty()) {
            String dir = sortDir.equalsIgnoreCase("ASC") ? "ASC" : sortDir.equalsIgnoreCase("DESC") ? "DESC" : "";
            if (!dir.isEmpty()) {
                orderByQuery = "ORDER BY " + sortField + " " + dir;
            }
        }
        return this.getJdbcTemplate().query(
                String.format(DepartmentQueries.QUERY_FIND_ALL_BY_STUDIO_ID_SORTED, orderByQuery),
                new DepartmentMapper(),
                studioID);
    }

    public List<Department> findAll() {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        return this.getJdbcTemplate().query(DepartmentQueries.QUERY_FIND_ALL, new DepartmentMapper());
    }

    public List<Department> findAllSorted(String sortField, String sortDir) {
        if (null == this.getJdbcTemplate()) {
            return null;
        }
        String orderByQuery = "";
        if (null != sortField && !sortField.isEmpty() && sortDir != null && !sortDir.isEmpty()) {
            String dir = sortDir.equalsIgnoreCase("ASC") ? "ASC" : sortDir.equalsIgnoreCase("DESC") ? "DESC" : "";
            if (!dir.isEmpty()) {
                orderByQuery = "ORDER BY " + sortField + " " + dir;
            }
        }
        return this.getJdbcTemplate().query(
                String.format(DepartmentQueries.QUERY_FIND_ALL_SORTED, orderByQuery),
                new DepartmentMapper());
    }

    public void save(Department department) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(DepartmentQueries.QUERY_SAVE, department.getStudioID(), department.getName(), department.getHeadID());
    }

    public void update(Long departmentID, Department department) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(DepartmentQueries.QUERY_UPDATE, department.getName(), department.getHeadID(), departmentID);
    }

    public void delete(Long departmentID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(DepartmentQueries.QUERY_DELETE, departmentID);
    }

    public void updateDepartmentsHead(Long employeeID) {
        if (null == this.getJdbcTemplate()) {
            return;
        }
        this.getJdbcTemplate().update(DepartmentQueries.QUERY_UPDATE_DEPARTMENTS_HEAD, employeeID);
    }
}
