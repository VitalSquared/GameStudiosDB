package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.service.GenreService;

import javax.validation.Valid;

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
        model.addAttribute("genres", this.genreService.getAllGenres());
        model.addAttribute("url", "/admin_panel/genres");
        return "admin/genres";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newGenre(@ModelAttribute("genre") Genre genre) {
        return "/admin/new_genre";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createGenre(@Valid @ModelAttribute("genre") Genre genre, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new_genre";
        }
        this.genreService.createNewGenre(genre);
        return "redirect:/admin_panel/genres";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editGenre(Model model, @PathVariable("id") Long genreID) {
        model.addAttribute("genreID", genreID);
        model.addAttribute("genre", this.genreService.getGenreByID(genreID));
        return "/admin/edit_genre";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateGenre(@Valid @ModelAttribute("genre") Genre genre,
                              BindingResult bindingResult,
                              Model model,
                              @PathVariable("id") Long genreID) {
        model.addAttribute("genreID", genreID);

        if (bindingResult.hasErrors()) {
            return "admin/edit_genre";
        }

        this.genreService.updateGenre(genreID, genre);
        return "redirect:/admin_panel/genres";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
    public String deleteGenre(@PathVariable("id") Long genreID) {
        this.genreService.deleteGenre(genreID);
        return "redirect:/admin_panel/genres";
    }
}
