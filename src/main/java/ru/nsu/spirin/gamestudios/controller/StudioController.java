package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/studios")
public class StudioController {
    private final StudioDAO studioDAO;
    private final CategoryDAO categoryDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public StudioController(StudioDAO studioDAO,
                            CategoryDAO categoryDAO,
                            DepartmentDAO departmentDAO,
                            EmployeeDAO employeeDAO,
                            AccountDAO accountDAO) {
        this.studioDAO = studioDAO;
        this.categoryDAO = categoryDAO;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    public String indexStudios(Model model, Principal principal,
                               @RequestParam(name = "studio", required = false, defaultValue = "0") String studioID) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountDAO.findUserAccount(user.getUsername());
        Employee employee = employeeDAO.getEmployeeByID(account.getEmployeeID());

        Long parsedStudioID;
        try {
            parsedStudioID = Long.parseLong(studioID);
        }
        catch (Exception e) {
            return "redirect:/studios";
        }

        if ((employee.getStudioID() > 0) && !(employee.getStudioID().equals(parsedStudioID))) {
            return "redirect:/studios?studio="+employee.getStudioID();
        }

        model.addAttribute("myStudio", studioDAO.getStudioByID(employee.getStudioID()));
        model.addAttribute("studios", studioDAO.getStudiosListByID(parsedStudioID));
        return "studios/studios";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newStudio(@ModelAttribute("studio") Studio studio, Model model, Principal principal) {
        return "/studios/new_studio";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute("studio") Studio studio, Principal principal) throws SQLException {
        studioDAO.newStudio(studio);
        return "redirect:/studios";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(Model model, @PathVariable("id") Long studioID) {
        model.addAttribute("studio", studioDAO.getStudioByID(studioID));
        List<Employee> employeeList = employeeDAO.getEmployeesByStudio(studioID);
        model.addAttribute("employeesCount", employeeList.size());
        return "/studios/edit_studio";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("studio") Studio studio, Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        studioDAO.updateStudio(id, studio);
        return "redirect:/studios";
    }

    /*@GetMapping(value = "/studios/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteStudio(@PathVariable("id") Long studioID, Model model, Principal principal) throws SQLException {
        if (studioID == 0) {
            return "redirect:/studios";
        }
        List<Employee> employeeList = employeeDAO.getEmployeesByStudio(studioID);
        if (employeeList.size() > 0) {
            return "redirect:/studios";
        }
        studioDAO.removeStudio(studioID);
        return "redirect:/studios";
    }*/
}
