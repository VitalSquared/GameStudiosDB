package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.model.entity.Department;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.DepartmentService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexDepartments(Model model, Principal principal,
                                   @RequestParam(name = "studio", required = false, defaultValue = "-1") String studio,
                                   @RequestParam(name = "sortField", required = false, defaultValue = "") String sortField,
                                   @RequestParam(name = "sortDir", required = false, defaultValue = "") String sortDir) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());

        long parsedStudioID;
        try {
            parsedStudioID = Long.parseLong(studio);
        }
        catch (Exception e) {
            return "redirect:/departments";
        }

        if ((employee.getStudioID() > 0) && !(employee.getStudioID().equals(parsedStudioID))) {
            return "redirect:/departments?studio="+employee.getStudioID();
        }

        model.addAttribute("urlFilter", "/departments?");
        model.addAttribute("idSortField", "department_id");
        model.addAttribute("nameSortField", "name");
        model.addAttribute("sortField", sortField);
        model.addAttribute("nextSortDir", sortDir.isEmpty() ? "ASC" : "ASC".equals(sortDir) ? "DESC" : "");

        List<Department> departmentList;
        if (parsedStudioID == -1) {
            departmentList = this.departmentService.getAllDepartmentsSorted(sortField, sortDir);
        }
        else {
            departmentList = this.departmentService.getAllDepartmentsOfStudio(parsedStudioID, sortField, sortDir);
        }

        model.addAttribute("studio", studio);
        model.addAttribute("url", "/departments");
        model.addAttribute("departments", departmentList);
        model.addAttribute("studios", this.studioService.getStudiosListByID(employee.getStudioID()));

        model.addAttribute("all_studios", this.studioService.getAllStudios());
        model.addAttribute("all_employees", this.employeeService.getEmployees());

        return "studios/departments";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newDepartment(@ModelAttribute("department") Department department, Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("employees", employeeService.getEmployeesByDepartment(department.getDepartmentID()));
        return "/studios/new_department";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createDepartment(@Valid @ModelAttribute("department") Department department,
                                   BindingResult bindingResult,
                                   Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studios", this.studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("employees", this.employeeService.getEmployeesByDepartment(department.getDepartmentID()));

        if (bindingResult.hasErrors()) {
            return "/studios/new_department";
        }

        this.departmentService.newDepartment(department);
        return "redirect:/departments";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editDepartment(Model model, @PathVariable("id") Long departmentID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("departmentID", departmentID);
        model.addAttribute("studios", this.studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("department", this.departmentService.getDepartmentByID(departmentID));
        model.addAttribute("employees", this.employeeService.getEmployeesByDepartment(departmentID));
        return "/studios/edit_department";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateDepartment(@Valid @ModelAttribute("department") Department department,
                                   BindingResult bindingResult,
                                   Model model, Principal principal,
                                   @PathVariable("id") Long departmentID) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("departmentID", departmentID);
        model.addAttribute("studios", this.studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("employees", this.employeeService.getEmployeesByDepartment(departmentID));

        if (bindingResult.hasErrors()) {
            return "/studios/edit_department";
        }

        this.departmentService.updateDepartment(departmentID, department);
        return "redirect:/departments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
    public String deleteDepartment(@PathVariable("id") Long departmentID) {
        this.departmentService.deleteDepartment(departmentID);
        return "redirect:/departments";
    }
}
