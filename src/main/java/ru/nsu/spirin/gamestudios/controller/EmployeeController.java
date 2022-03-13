package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.repository.filtration.Filtration;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.CategoryService;
import ru.nsu.spirin.gamestudios.service.DepartmentService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import javax.validation.Valid;
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

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexEmployees(Model model, Principal principal,
                                 @RequestParam(name = "studio", required = false, defaultValue = "-1") String studio,
                                 @RequestParam(name = "firstName", required = false, defaultValue = "") String firstName,
                                 @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName,
                                 @RequestParam(name = "sortField", required = false, defaultValue = "") String sortField,
                                 @RequestParam(name = "sortDir", required = false, defaultValue = "") String sortDir) {
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
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("sortField", sortField);
        model.addAttribute("nextSortDir", sortDir.isEmpty() ? "ASC" : "ASC".equals(sortDir) ? "DESC" : "");

        model.addAttribute("idSortField", "employee_id");
        model.addAttribute("firstNameSortField", "first_name");
        model.addAttribute("lastNameSortField", "last_name");

        model.addAttribute("employees", employeeService.getEmployeesByStudioWithFiltration(
                parsedStudioID,
                firstName,
                lastName,
                sortField,
                sortDir
            )
        );
        model.addAttribute("studios", studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("accounts", accountService.getEmailsWithEmployeeIDs());

        Filtration filtration = new Filtration();
        filtration.addFilter("studio", null, studio);
        filtration.addFilter("firstName", null, firstName);
        filtration.addFilter("lastName", null, lastName);
        String filters = filtration.buildPath();
        model.addAttribute("filters", filters);
        model.addAttribute("urlFilterless", "/employees");
        model.addAttribute("urlFilter", "/employees?" + filters);

        return "studios/employees";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("account") Account account,
                              Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/studios/new_employee";
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult1,
                                 @Valid @ModelAttribute("account") Account account,
                                 BindingResult bindingResult2,
                                 Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());

        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "/studios/new_employee";
        }

        employeeService.newEmployee(employee, account);
        return "redirect:/employees";
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable("id") Long employeeID) {
        model.addAttribute("employeeID", employeeID);
        model.addAttribute("employee", employeeService.getEmployeeByID(employeeID));
        model.addAttribute("account", accountService.findAccountByEmployeeID(employeeID));
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/studios/edit_employee";
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult1,
                                 @Valid @ModelAttribute("account") Account account,
                                 BindingResult bindingResult2,
                                 Model model,
                                 @PathVariable("id") Long employeeID) {
        model.addAttribute("employeeID", employeeID);
        model.addAttribute("studios", studioService.getAllStudios());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());

        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "/studios/edit_employee";
        }

        employeeService.updateEmployee(employeeID, employee, account);
        return "redirect:/employees";
    }
}
