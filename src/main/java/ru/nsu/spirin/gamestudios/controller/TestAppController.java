package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.EmployeeDAO;
import ru.nsu.spirin.gamestudios.dao.TestAppDAO;
import ru.nsu.spirin.gamestudios.dao.TestDAO;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Game;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;

import java.security.Principal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;

@Controller
@RequestMapping("/tests")
public class TestAppController {

    private final TestAppDAO testAppDAO;
    private final TestDAO testDAO;
    private final EmployeeDAO employeeDAO;

    @Autowired
    public TestAppController(TestAppDAO testAppDAO, EmployeeDAO employeeDAO, TestDAO testDAO) {
        this.testAppDAO = testAppDAO;
        this.employeeDAO = employeeDAO;
        this.testDAO = testDAO;
    }

    @GetMapping("/apps/{id}")
    public String viewApp(Model model, Principal principal,
                           @PathVariable(name = "id") Long appID) {
        TestApp app = testAppDAO.getAppByID(appID);
        model.addAttribute("app", app);
        model.addAttribute("test", testDAO.getTestByID(app.getTestID()));
        model.addAttribute("employees", employeeDAO.getEmployeesByTestApp(appID));
        model.addAttribute("curDate", Date.from(Instant.now()));
        return "testings/view_testapp";
    }

    @GetMapping("/apps/{id}/accept")
    public String acceptApp(@PathVariable(name = "id") Long appID) {
        testAppDAO.acceptApp(appID);
        return "redirect:/tests/apps/{id}";
    }

    @GetMapping("/apps/{id}/decline")
    public String declineApp(@PathVariable(name = "id") Long appID) {
        testAppDAO.declineApp(appID);
        return "redirect:/tests";
    }

    @GetMapping("/apps/{id}/add_employee")
    public String addEmployeeGet(@ModelAttribute("employee") Employee employee,
                                 Model model, Principal principal, @PathVariable(name = "id") Long appID) {
        TestApp app = testAppDAO.getAppByID(appID);
        model.addAttribute("employees", employeeDAO.getEmployeesByStudio(app.getStudioID()));
        model.addAttribute("appID", appID);
        return "/testings/testapp_add_employee";
    }

    @PostMapping("/apps/{id}/employee")
    public String addEmployeePost(@ModelAttribute("employee") Employee employee,
                                  Model model, Principal principal, @PathVariable(name = "id") Long appID) throws SQLException {
        testAppDAO.addEmployee(appID, employee.getEmployeeID());
        return "redirect:/tests/apps/{id}";
    }

    @GetMapping("/apps/{id}/remove_employee/{id1}")
    public String removeEmployeeGet(Model model, Principal principal,
                                    @PathVariable(name = "id") Long appID,
                                    @PathVariable(name = "id1") Long employeeID) {
        testAppDAO.removeEmployee(appID, employeeID);
        return "redirect:/tests/apps/{id}";
    }

    @GetMapping("/apps/{id}/success")
    public String successEndResult(@PathVariable(name = "id") Long appID) {
        testAppDAO.successEndResult(appID);
        return "redirect:/tests/apps/{id}";
    }

    @GetMapping("/apps/{id}/failure")
    public String failureEndResult(@PathVariable(name = "id") Long appID) {
        testAppDAO.failureEndResult(appID);
        return "redirect:/tests/apps/{id}";
    }
}
