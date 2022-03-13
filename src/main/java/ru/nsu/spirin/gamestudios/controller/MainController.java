package ru.nsu.spirin.gamestudios.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.utils.WebUtils;

@Controller
public class MainController {

    private final EmployeeService employeeService;
    private final AccountService accountService;

    @Autowired
    public MainController(EmployeeService employeeService, AccountService accountService) {
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @RequestMapping(path = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage(Principal principal) {
        return principal == null ? "loginPage" : "redirect:/";
    }

    @RequestMapping(path = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "redirect:/login";
    }

    @RequestMapping(path = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        Account account = accountService.findAccountByEmail(userName);
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("employee", employee);
        model.addAttribute("account", account);
        return "myaccount/index";
    }

    @RequestMapping(path = "/userInfo/change_password", method = RequestMethod.GET)
    public String changePasswordGet(@ModelAttribute("account") Account account) {
        return "myaccount/change_passwd";
    }

    @RequestMapping(path = "/userInfo/change_password", method = RequestMethod.POST)
    public String changePasswordPost(@ModelAttribute("account") Account account, Principal principal) {
        accountService.update(principal.getName(), account.getPasswordHash());
        return "redirect:/myaccount";
    }

    @RequestMapping(path = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "\n You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }
}
