package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.service.GenreService;


@Controller
@RequestMapping("/admin_panel/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexGenres(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("url", "/admin_panel/genres");
        return "admin/genres";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newGenre(@ModelAttribute("genre") Genre genre) {
        return "/admin/new_genre";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public String createGenre(@ModelAttribute("genre") Genre genre) {
        genreService.createNewGenre(genre);
        return "redirect:/admin_panel/genres";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editGenre(Model model, @PathVariable("id") Long genreID) {
        model.addAttribute("genre", genreService.getGenreByID(genreID));
        return "/admin/edit_genre";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public String updateGenre(@ModelAttribute("genre") Genre genre, @PathVariable("id") Long id) {
        genreService.updateGenre(id, genre);
        return "redirect:/admin_panel/genres";
    }
}
