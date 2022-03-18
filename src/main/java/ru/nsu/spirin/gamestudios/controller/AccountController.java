package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.PasswordChangeStore;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.DepartmentService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountController {
    private final EmployeeService employeeService;
    private final AccountService accountService;
    private final StudioService studioService;
    private final DepartmentService departmentService;

    @Autowired
    public AccountController(EmployeeService employeeService, AccountService accountService, StudioService studioService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.accountService = accountService;
        this.studioService = studioService;
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("employee", employee);
        model.addAttribute("account", account);
        model.addAttribute("all_studios", studioService.getAllStudios());
        model.addAttribute("all_departments", departmentService.getAllDepartments());
        return "myaccount/index";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/userInfo/change_password", method = RequestMethod.GET)
    public String changePasswordGet(@ModelAttribute("password") PasswordChangeStore passwordChangeStore) {
        return "myaccount/change_passwd";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/userInfo/change_password", method = RequestMethod.POST)
    public String changePasswordPost(@Valid @ModelAttribute("password") PasswordChangeStore passwordChangeStore,
                                     BindingResult bindingResult,
                                     Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());

        if (passwordChangeStore.getOldPassword() != null && !passwordChangeStore.getOldPassword().isEmpty()) {
            if (!(new BCryptPasswordEncoder(12).matches(passwordChangeStore.getOldPassword(), account.getPasswordHash()))) {
                bindingResult.rejectValue("oldPassword", "error.oldPassword", "Old password mismatch!");
            }
        }

        if (passwordChangeStore.getNewPassword() != null && passwordChangeStore.getNewPasswordRepeat() != null &&
                !passwordChangeStore.getNewPassword().isEmpty() && !passwordChangeStore.getNewPasswordRepeat().isEmpty()) {
            if (!passwordChangeStore.getNewPassword().equals(passwordChangeStore.getNewPasswordRepeat())) {
                bindingResult.rejectValue("newPassword", "error.newPassword", "Different new and repeated passwords");
                bindingResult.rejectValue("newPasswordRepeat", "error.newPassword", "Different new and repeated passwords");
            }
        }

        if (bindingResult.hasErrors()) {
            return "myaccount/change_passwd";
        }

        this.accountService.update(user.getUsername(), passwordChangeStore.getNewPassword());
        return "redirect:/userInfo";
    }
}
