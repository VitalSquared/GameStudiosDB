package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexEmployees(Model model, Principal principal,
                                 @RequestParam(name = "studio", required = false, defaultValue = "-1") String studio,
                                 @RequestParam(name = "firstName", required = false, defaultValue = "") String firstName,
                                 @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName,
                                 @RequestParam(name = "category", required = false, defaultValue = "") String category,
                                 @RequestParam(name = "birthDate", required = false, defaultValue = "") String birthDate,
                                 @RequestParam(name = "sortField", required = false, defaultValue = "") String sortField,
                                 @RequestParam(name = "sortDir", required = false, defaultValue = "") String sortDir) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());

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
        model.addAttribute("category", category);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("sortField", sortField);
        model.addAttribute("nextSortDir", sortDir.isEmpty() ? "ASC" : "ASC".equals(sortDir) ? "DESC" : "");

        model.addAttribute("idSortField", "employee_id");
        model.addAttribute("firstNameSortField", "first_name");
        model.addAttribute("lastNameSortField", "last_name");
        model.addAttribute("birthDateSortField", "birth_date");

        model.addAttribute("employees", this.employeeService.getEmployeesByStudioWithFiltration(
                parsedStudioID,
                firstName,
                lastName,
                category,
                birthDate,
                sortField,
                sortDir
            )
        );
        model.addAttribute("studios", this.studioService.getStudiosListByID(employee.getStudioID()));
        model.addAttribute("all_accounts", this.accountService.getAllAccounts());
        model.addAttribute("all_studios", this.studioService.getAllStudios());
        model.addAttribute("all_departments", this.departmentService.getAllDepartments());
        model.addAttribute("all_categories", this.categoryService.getAllCategories());

        Filtration filtration = new Filtration();
        filtration.addFilter("studio", null, studio);
        filtration.addFilter("firstName", null, firstName);
        filtration.addFilter("lastName", null, lastName);
        filtration.addFilter("category", null, category);
        filtration.addFilter("birthDate", null, birthDate);
        String filters = filtration.buildPath();
        model.addAttribute("filters", filters);
        model.addAttribute("urlFilterless", "/employees");
        model.addAttribute("urlFilter", "/employees?" + filters);

        return "studios/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("account") Account account,
                              Model model) {
        model.addAttribute("studios", this.studioService.getAllStudios());
        model.addAttribute("departments", this.departmentService.getAllDepartments());
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "/studios/new_employee";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult1,
                                 @Valid @ModelAttribute("account") Account account,
                                 BindingResult bindingResult2,
                                 Model model) {
        model.addAttribute("studios", this.studioService.getAllStudios());
        model.addAttribute("departments", this.departmentService.getAllDepartments());
        model.addAttribute("categories", this.categoryService.getAllCategories());

        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "/studios/new_employee";
        }

        this.employeeService.newEmployee(employee, account);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable("id") Long employeeID) {
        model.addAttribute("employeeID", employeeID);
        model.addAttribute("employee", this.employeeService.getEmployeeByID(employeeID));
        model.addAttribute("account", this.accountService.findAccountByEmployeeID(employeeID));
        model.addAttribute("studios", this.studioService.getAllStudios());
        model.addAttribute("departments", this.departmentService.getAllDepartments());
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "/studios/edit_employee";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult1,
                                 @Valid @ModelAttribute("account") Account account,
                                 BindingResult bindingResult2,
                                 Model model,
                                 @PathVariable("id") Long employeeID) {
        model.addAttribute("employeeID", employeeID);
        model.addAttribute("studios", this.studioService.getAllStudios());
        model.addAttribute("departments", this.departmentService.getAllDepartments());
        model.addAttribute("categories", this.categoryService.getAllCategories());

        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "/studios/edit_employee";
        }

        this.employeeService.updateEmployee(employeeID, employee, account);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable("id") Long employeeID) {
        this.employeeService.deleteEmployee(employeeID);
        return "redirect:/employees";
    }
}
