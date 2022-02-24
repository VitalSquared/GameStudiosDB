package ru.nsu.spirin.gamestudios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.spirin.gamestudios.model.user.Role;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RoleDAO extends JdbcDaoSupport {

    @Autowired
    public RoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Role> getEmployeeRoles(Long employeeID) {
        String sqlDeveloper = "SELECT count(*) FROM developer dev WHERE dev.employee_id = ?";
        Object[] params = new Object[]{employeeID};
        Integer devsCount = this.getJdbcTemplate().queryForObject(sqlDeveloper, params, Integer.class);

        if (devsCount == null || devsCount > 0) {
            return List.of(Role.ROLE_DEVELOPER);    //developer
        }

        String sqlDirector = "SELECT dir.studio_id FROM director dir WHERE dir.employee_id = ?";
        Integer studioID = this.getJdbcTemplate().queryForObject(sqlDirector, params,
                (ResultSet rs, int rowNum) -> rs.getInt("studio_id"));

        return studioID == null ? new ArrayList<>() :
                studioID != 0 ? List.of(Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER) :   //studio director
                        employeeID != 0 ? List.of(Role.ROLE_GENERAL_DIRECTOR, Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER) : //general director
                                List.of(Role.ROLE_ADMIN, Role.ROLE_GENERAL_DIRECTOR, Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER);    //admin
    }
}
