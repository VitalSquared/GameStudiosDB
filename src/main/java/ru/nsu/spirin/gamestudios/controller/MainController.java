package ru.nsu.spirin.gamestudios.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    public MainController() {}

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

    /*
    @RequestMapping(path = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        String message = "You do not have permission to access this page!";
        model.addAttribute("message", message);
        return "errorPage";
    }*/
}
