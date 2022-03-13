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
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.AccountService;
import ru.nsu.spirin.gamestudios.service.EmployeeService;
import ru.nsu.spirin.gamestudios.service.StudioService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/studios")
public class StudioController {
    private final StudioService studioService;
    private final EmployeeService employeeService;
    private final AccountService accountService;

    @Autowired
    public StudioController(StudioService studioService,
                            EmployeeService employeeService,
                            AccountService accountService) {
        this.studioService = studioService;
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexStudios(Model model, Principal principal,
                               @RequestParam(name = "studio", required = false, defaultValue = "0") String studioID) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findAccountByEmail(user.getUsername());
        Employee employee = employeeService.getEmployeeByID(account.getEmployeeID());

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

        model.addAttribute("myStudio", studioService.getStudioByID(employee.getStudioID()));
        model.addAttribute("studios", studioService.getStudiosListByID(parsedStudioID));
        return "studios/studios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newStudio(@ModelAttribute("studio") Studio studio) {
        return "/studios/new_studio";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("studio") Studio studio, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/studios/new_studio";
        }
        studioService.newStudio(studio);
        return "redirect:/studios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") Long studioID) {
        model.addAttribute("studioID", studioID);
        model.addAttribute("studio", studioService.getStudioByID(studioID));
        return "/studios/edit_studio";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("studio") Studio studio,
                         BindingResult bindingResult,
                         Model model,
                         @PathVariable("id") Long studioID) {
        model.addAttribute("studioID", studioID);

        if (bindingResult.hasErrors()) {
            return "/studios/edit_studio";
        }

        studioService.updateStudio(studioID, studio);
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
