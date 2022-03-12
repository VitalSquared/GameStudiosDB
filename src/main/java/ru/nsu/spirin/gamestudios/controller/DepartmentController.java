package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.DepartmentService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import java.security.Principal;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final StudioService studioService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final AccountService accountService;

    @Autowired
    public DepartmentController(StudioService studioService,
                                DepartmentService departmentService,
                                EmployeeService employeeService,
                                AccountService accountService) {
        this.studioService = studioService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexDepartments(Model model, Principal principal,
                                   @RequestParam(name = "studio", required = false, defaultValue = "0") String studio) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());

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
        model.addAttribute("departments", departmentService.getAllDepartmentsOfStudio(parsedStudioID));
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        return "studios/departments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newDepartment(@ModelAttribute("department") Department department, Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("employees", employeeService.getEmployeesByDepartment(department.getDepartmentID()));
        return "/studios/new_department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public String create(@ModelAttribute("department") Department department) {
        departmentService.newDepartment(department);
        return "redirect:/departments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editDepartment(Model model, @PathVariable("id") Long departmentID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("department", departmentService.getDepartmentByID(departmentID));
        model.addAttribute("employees", employeeService.getEmployeesByDepartment(departmentID));
        return "/studios/edit_department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("department") Department department, @PathVariable("id") Long id) {
        departmentService.updateDepartment(id, department);
        return "redirect:/departments";
    }
}
