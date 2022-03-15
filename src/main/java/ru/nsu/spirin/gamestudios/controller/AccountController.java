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
import ru.nsu.spirin.gamestudios.service.EmployeeService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountController {
    private final EmployeeService employeeService;
    private final AccountService accountService;

    @Autowired
    public AccountController(EmployeeService employeeService, AccountService accountService) {
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        Account account = accountService.findAccountByEmail(userName);
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("employee", employee);
        model.addAttribute("account", account);
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
        Account account = accountService.findAccountByEmail(user.getUsername());

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

        accountService.update(user.getUsername(), passwordChangeStore.getNewPassword());
        return "redirect:/userInfo";
    }
}
