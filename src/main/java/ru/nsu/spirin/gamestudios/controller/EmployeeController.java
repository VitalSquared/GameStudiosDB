package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.CategoryService;
import ru.nsu.spirin.gamestudios.service.DepartmentService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import java.security.Principal;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final StudioService studioService;
    private final CategoryService categoryService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final AccountService accountService;

    @Autowired
    public EmployeeController(StudioService studioService,
                              CategoryService categoryService,
                              DepartmentService departmentService,
                              EmployeeService employeeService,
                              AccountService accountService) {
        this.studioService = studioService;
        this.categoryService = categoryService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @GetMapping("")
    public String indexEmployees(Model model, Principal principal,
                                 @RequestParam(name = "studio", required = false, defaultValue = "0") String studio) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());

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
        model.addAttribute("employees", employeeService.getEmployeesByStudio(parsedStudioID));
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("accounts", accountService.getEmailsWithEmployeeIDs());
        return "studios/employees";
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("account") Account account,
                              Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/studios/new_employee";
    }

    @PostMapping("")
    public String create(@ModelAttribute("employee") Employee employee,
                         @ModelAttribute("account") Account account) {
        employeeService.newEmployee(employee, account);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long employeeID) {
        model.addAttribute("employee", employeeService.getEmployeeByID(employeeID));
        model.addAttribute("account", accountService.findAccountByEmployeeID(employeeID));
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/studios/edit_employee";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("employee") Employee employee,
                         @ModelAttribute("account") Account account,
                         @PathVariable("id") Long id) {
        employeeService.updateEmployee(id, employee, account);
        return "redirect:/employees";
    }
}
