package ru.nsu.spirin.gamestudios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status == null) {
            status = "200";
        }
        int statusCode = Integer.parseInt(status.toString());
        HttpStatus status1 = HttpStatus.resolve(statusCode);
        String errorMsg = status1 == null ? "" : status1.getReasonPhrase();
        model.addAttribute("errorMsg", statusCode + " " + errorMsg);
        return "errorPage";
    }
}
