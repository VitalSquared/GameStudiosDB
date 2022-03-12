package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.model.entity.account.Role;
import ru.nsu.spirin.gamestudios.repository.AccountRepository;
import ru.nsu.spirin.gamestudios.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AccountRepository accountRepository) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
    }

    public Employee getEmployeeByID(Long employeeID) {
        Employee employee = this.employeeRepository.findByID(employeeID);
        employee.setEmployeeType(employee.getDepartmentID() == null ? "director" : "developer");
        return employee;
    }

    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByStudio(Long studioID) {
        return this.employeeRepository.findAllByStudioID(studioID);
    }

    public List<Employee> getEmployeesByGameID(Long gameID) {
        return this.employeeRepository.findByGameID(gameID);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentID) {
        return this.employeeRepository.findByDepartmentID(departmentID);
    }

    public List<Employee> getEmployeesByTestApp(Long appID) {
        return this.employeeRepository.findByTestAppID(appID);
    }

    public void newEmployee(Employee employee, Account account) {
        Long employeeID = this.employeeRepository.save(employee);
        employee.setEmployeeID(employeeID);
        if ("developer".equalsIgnoreCase(employee.getEmployeeType())) {
            this.employeeRepository.saveDeveloper(employee);
        }
        else {
            this.employeeRepository.saveDirector(employee);
        }

        String login = account.getEmail().split("@")[0];
        String passwd = new BCryptPasswordEncoder(12).encode(login);
        account.setPasswordHash(passwd);
        account.setEmployeeID(employeeID);
        this.accountRepository.save(account);
    }

    public void updateEmployee(Long employeeID, Employee employee, Account account) {
        employee.setEmployeeID(employeeID);
        this.employeeRepository.update(employeeID, employee);
        if ("developer".equalsIgnoreCase(employee.getEmployeeType())) {
            this.employeeRepository.deleteDirector(employeeID);
            this.employeeRepository.upsertDeveloper(employee);
        }
        else {
            this.employeeRepository.deleteDeveloper(employeeID);
            this.employeeRepository.upsertDirector(employee);
        }

        Account saved = this.accountRepository.findByID(account.getEmail());
        account.setPasswordHash(saved.getPasswordHash());
        if ("reset".equalsIgnoreCase(account.getPasswordResetState())) {
            String login = account.getEmail().split("@")[0];
            String passwd = new BCryptPasswordEncoder(12).encode(login);
            account.setPasswordHash(passwd);
        }
        this.accountRepository.update(saved.getEmail(), account);
    }

    public List<Role> getEmployeeRoles(Long employeeID) {
        Long devsCount = this.employeeRepository.countDevelopersByID(employeeID);
        if (devsCount == null || devsCount > 0) {
            return List.of(Role.ROLE_DEVELOPER);    //developer
        }

        Long studioID = this.employeeRepository.findDirectorStudioByID(employeeID);
        return studioID == null ? new ArrayList<>() :
                studioID != 0 ? List.of(Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER) :   //studio director
                        employeeID != 0 ? List.of(Role.ROLE_GENERAL_DIRECTOR, Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER) : //general director
                                List.of(Role.ROLE_ADMIN, Role.ROLE_GENERAL_DIRECTOR, Role.ROLE_STUDIO_DIRECTOR, Role.ROLE_DEVELOPER);    //admin
    }
}
