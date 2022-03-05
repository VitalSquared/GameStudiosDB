package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;

import java.security.Principal;
import java.sql.SQLException;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final StudioDAO studioDAO;
    private final CategoryDAO categoryDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public EmployeeController(StudioDAO studioDAO,
                                CategoryDAO categoryDAO,
                                DepartmentDAO departmentDAO,
                                EmployeeDAO employeeDAO,
                                AccountDAO accountDAO) {
        this.studioDAO = studioDAO;
        this.categoryDAO = categoryDAO;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    public String indexEmployees(Model model, Principal principal,
                                 @RequestParam(name = "studio", required = false, defaultValue = "0") String studio) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountDAO.findUserAccount(user.getUsername());
        Employee employee = employeeDAO.getEmployeeByID(account.getEmployeeID());

        Long parsedStudioID;
        try {
            parsedStudioID = Long.parseLong(studio);
        }
        catch (Exception e) {
            return "redirect:/employees";
        }

        if ((employee.getStudioID() > 0) && !(employee.getStudioID().equals(parsedStudioID))) {
            return "redirect:/employees?studio="+employee.getStudioID();
        }

        model.addAttribute("studio", studio);
        model.addAttribute("url", "/employees");
        model.addAttribute("employees", employeeDAO.getEmployeesByStudio(parsedStudioID));
        model.addAttribute("studios", studioDAO.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("accounts", accountDAO.getEmails());
        return "studios/employees";
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("account") Account account,
                              Model model, Principal principal) {
        model.addAttribute("studios", studioDAO.getAllStudios());
        model.addAttribute("departments", departmentDAO.getAllDepartments());
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "/studios/new_employee";
    }

    @PostMapping("")
    public String create(@ModelAttribute("employee") Employee employee,
                         @ModelAttribute("account") Account account,
                         Model model,
                         Principal principal) throws SQLException {
        employeeDAO.newEmployee(employee, account);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long employeeID) {
        model.addAttribute("employee", employeeDAO.getEmployeeByID(employeeID));
        model.addAttribute("account", accountDAO.findUserAccountByEmployeeID(employeeID));
        model.addAttribute("studios", studioDAO.getAllStudios());
        model.addAttribute("departments", departmentDAO.getAllDepartments());
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "/studios/edit_employee";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("employee") Employee employee,
                         @ModelAttribute("account") Account account,
                         Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        employeeDAO.updateEmployee(id, employee, account);
        return "redirect:/employees";
    }
}
