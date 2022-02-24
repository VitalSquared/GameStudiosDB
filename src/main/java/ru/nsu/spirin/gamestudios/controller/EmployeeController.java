package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.spirin.gamestudios.dao.EmployeeDAO;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("")
    public String indexEmployees(Model model) {
        model.addAttribute("employees", employeeDAO.getEmployees());
        return "employees/index";
    }
}
