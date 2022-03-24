package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.model.entity.account.Role;
import ru.nsu.spirin.gamestudios.model.entity.message.Message;
import ru.nsu.spirin.gamestudios.repository.*;
import ru.nsu.spirin.gamestudios.repository.filtration.Filtration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;
    private final DepartmentRepository departmentRepository;
    private final GameRepository gameRepository;
    private final TestAppRepository testAppRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AccountRepository accountRepository, MessageRepository messageRepository, DepartmentRepository departmentRepository, GameRepository gameRepository, TestAppRepository testAppRepository) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
        this.departmentRepository = departmentRepository;
        this.gameRepository = gameRepository;
        this.testAppRepository = testAppRepository;
    }

    public Employee getEmployeeByID(Long employeeID) {
        Employee employee = this.employeeRepository.findByID(employeeID);
        employee.setEmployeeType(employee.getDepartmentID() == null ? "director" : "developer");
        return employee;
    }

    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByStudioWithFiltration(Long studioID,
                                                             String firstName,
                                                             String lastName,
                                                             String category,
                                                             String birthDate,
                                                             String sortField,
                                                             String sortDir) {
        Filtration filtration = new Filtration();
        filtration.addFilter("e.first_name", Filtration.FiltrationType.String, firstName);
        filtration.addFilter("e.last_name", Filtration.FiltrationType.String, lastName);
        filtration.addFilter("dev.category_id", Filtration.FiltrationType.Integer, category);
        filtration.addFilter("e.birth_date", Filtration.FiltrationType.DoubleDate, birthDate);
        return this.employeeRepository.findAllByStudioIDWithFiltration(studioID, filtration, sortField, sortDir);
    }

    public List<Employee> getEmployeesByStudio(Long studioID) {
        return this.employeeRepository.findAllByStudioID(studioID);
    }

    public List<Employee> getEmployeesByStudioExceptTestApp(Long studioID, Long appID) {
        List<Employee> apps = this.employeeRepository.findByTestAppID(appID);
        List<Employee> all = this.employeeRepository.findAllByStudioID(studioID);
        return all.stream().filter(x -> !apps.contains(x)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByStudioExceptGame(Long studioID, Long gameID) {
        List<Employee> game = this.employeeRepository.findByGameID(gameID);
        List<Employee> all = this.employeeRepository.findAllByStudioID(studioID);
        return all.stream().filter(x -> !game.contains(x)).collect(Collectors.toList());
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

    public void deleteEmployee(Long employeeID) {
        Account account = this.accountRepository.findByEmployeeID(employeeID);
        List<Long> allSent = this.messageRepository.findAllSentMessagesByEmailSimple(account.getEmail());
        for (var sent : allSent) {
            this.messageRepository.deleteAllReceivedMessagesByMessageID(sent);
        }
        this.messageRepository.deleteAllSentMessagesByAccount(account.getEmail());
        this.messageRepository.deleteAllReceivedMessagesByAccount(account.getEmail());
        this.accountRepository.deleteByEmployeeID(employeeID);
        this.gameRepository.deleteAllGameEmployeeByEmployeeID(employeeID);
        this.testAppRepository.deleteAllAppEmployeeByEmployeeID(employeeID);
        this.departmentRepository.updateDepartmentsHead(employeeID);
        this.employeeRepository.delete(employeeID);
    }
}
