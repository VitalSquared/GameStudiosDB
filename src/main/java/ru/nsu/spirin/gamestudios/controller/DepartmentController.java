package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.Department;
import ru.nsu.spirin.gamestudios.model.Employee;
import ru.nsu.spirin.gamestudios.model.account.Account;

import java.security.Principal;
import java.sql.SQLException;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final StudioDAO studioDAO;
    private final CategoryDAO categoryDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public DepartmentController(StudioDAO studioDAO,
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
    public String indexDepartments(Model model, Principal principal,
                                   @RequestParam(name = "studio", required = false, defaultValue = "0") String studio) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountDAO.findUserAccount(user.getUsername());
        Employee employee = employeeDAO.getEmployeeByID(account.getEmployeeID());

        Long parsedStudioID;
        try {
            parsedStudioID = Long.parseLong(studio);
        }
        catch (Exception e) {
            return "redirect:/departments";
        }

        if ((employee.getStudioID() > 0) && !(employee.getStudioID().equals(parsedStudioID))) {
            return "redirect:/departments?studio="+employee.getStudioID();
        }

        model.addAttribute("studio", studio);
        model.addAttribute("url", "/departments");
        model.addAttribute("departments", departmentDAO.getAllDepartmentsOfStudio(parsedStudioID));
        model.addAttribute("studios", studioDAO.getStudiosListByID(employee.getStudioID()));
        return "studios/departments";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newDepartment(@ModelAttribute("department") Department department, Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountDAO.findUserAccount(user.getUsername());
        Employee employee = employeeDAO.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", studioDAO.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("employees", employeeDAO.getEmployeesByDepartment(department.getDepartmentID()));
        return "/studios/new_department";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute("department") Department department, Principal principal) throws SQLException {
        departmentDAO.newDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editDepartment(Model model, @PathVariable("id") Long departmentID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountDAO.findUserAccount(user.getUsername());
        Employee employee = employeeDAO.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", studioDAO.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("department", departmentDAO.getDepartmentByID(departmentID));
        model.addAttribute("employees", employeeDAO.getEmployeesByDepartment(departmentID));
        return "/studios/edit_department";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("department") Department department, Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        departmentDAO.updateDepartment(id, department);
        return "redirect:/departments";
    }
}
