package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.Platform;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin_panel/platforms")
public class PlatformController {
    private final StudioDAO studioDAO;
    private final PlatformDAO platformDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public PlatformController(StudioDAO studioDAO,
                              PlatformDAO platformDAO,
                              DepartmentDAO departmentDAO,
                              EmployeeDAO employeeDAO,
                              AccountDAO accountDAO) {
        this.studioDAO = studioDAO;
        this.platformDAO = platformDAO;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String indexPlatforms(Model model, Principal principal) {
        List<Platform> platforms = platformDAO.getAllPlatforms();
        model.addAttribute("platforms", platforms);
        model.addAttribute("url", "/admin_panel/platforms");
        return "admin/platforms";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newPlatform(@ModelAttribute("platform") Platform platform, Model model, Principal principal) {
        return "/admin/new_platform";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute("platform") Platform platform, Principal principal) throws SQLException {
        platformDAO.newPlatform(platform);
        return "redirect:/admin_panel/platforms";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPlatform(Model model, @PathVariable("id") Long platformID, Principal principal) {
        model.addAttribute("platform", platformDAO.getPlatformByID(platformID));
        return "/admin/edit_platform";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("platform") Platform platform, Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        platformDAO.updatePlatform(id, platform);
        return "redirect:/admin_panel/platforms";
    }
}
