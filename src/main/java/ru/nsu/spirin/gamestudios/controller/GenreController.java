package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.entity.Genre;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin_panel/genres")
public class GenreController {
    private final StudioDAO studioDAO;
    private final GenreDAO genreDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public GenreController(StudioDAO studioDAO,
                           GenreDAO genreDAO,
                              DepartmentDAO departmentDAO,
                              EmployeeDAO employeeDAO,
                              AccountDAO accountDAO) {
        this.studioDAO = studioDAO;
        this.genreDAO = genreDAO;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String indexGenres(Model model, Principal principal) {
        List<Genre> genres = genreDAO.getAllGenres();
        model.addAttribute("genres", genres);
        model.addAttribute("url", "/admin_panel/genres");
        return "admin/genres";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newGenre(@ModelAttribute("genre") Genre genre, Model model, Principal principal) {
        return "/admin/new_genre";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute("genre") Genre genre, Principal principal) throws SQLException {
        genreDAO.newGenre(genre);
        return "redirect:/admin_panel/genres";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editGenre(Model model, @PathVariable("id") Long genreID, Principal principal) {
        model.addAttribute("genre", genreDAO.getGenreByID(genreID));
        return "/admin/edit_genre";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("genre") Genre genre, Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        genreDAO.updateGenre(id, genre);
        return "redirect:/admin_panel/genres";
    }
}
