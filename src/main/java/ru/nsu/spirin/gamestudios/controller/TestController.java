package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.GenreDAO;
import ru.nsu.spirin.gamestudios.dao.TestAppDAO;
import ru.nsu.spirin.gamestudios.dao.TestDAO;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.model.entity.Test;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestDAO testDAO;
    private final TestAppDAO testAppDAO;
    private final GenreDAO genreDAO;

    @Autowired
    public TestController(TestDAO testDAO, TestAppDAO testAppDAO, GenreDAO genreDAO) {
        this.testDAO = testDAO;
        this.testAppDAO = testAppDAO;
        this.genreDAO = genreDAO;
    }

    @GetMapping("")
    public String indexTests(Model model, Principal principal) {
        List<Test> tests = testDAO.getAllTests();
        model.addAttribute("tests", tests);
        return "testings/testings";
    }

    @GetMapping("/{id}")
    public String viewTest(Model model, Principal principal,
                           @PathVariable(name = "id") Long testID) {
        model.addAttribute("test", testDAO.getTestByID(testID));
        model.addAttribute("apps", testAppDAO.getAppsForTest(testID));
        model.addAttribute("genres", genreDAO.getGenresByTestID(testID));
        return "testings/view_testing";
    }

    @GetMapping("/new")
    public String newTest(@ModelAttribute("test") Test test,
                              Model model, Principal principal) {

        return "/testings/new_testing";
    }

    @PostMapping("")
    public String create(@ModelAttribute("test") Test test,
                         Model model, Principal principal) throws SQLException {
        test.setStatusID(0L);
        testDAO.newTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/edit")
    public String editTest(Model model, @PathVariable("id") Long testID) {
        model.addAttribute("test", testDAO.getTestByID(testID));
        return "/testings/edit_testing";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("test") Test test,
                         Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        testDAO.updateTest(id, test);
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/start_test")
    public String startTest(@PathVariable("id") Long id) {
        testDAO.startTest(id);
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/finish_test")
    public String finishTest(@PathVariable("id") Long id) {
        testDAO.finishTest(id);
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/results_test")
    public String resultsReadyTest(@PathVariable("id") Long id) {
        testDAO.resultsReadyTest(id);
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/cancel_test")
    public String cancelTest(@PathVariable("id") Long id) {
        testDAO.cancelTest(id);
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/delete_test")
    public String deleteTest(@PathVariable("id") Long id) {
        testDAO.deleteTest(id);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/add_genre")
    public String addGenreGet(@ModelAttribute("genre") Genre genre,
                              Model model, Principal principal, @PathVariable(name = "id") Long testID) {
        model.addAttribute("genres", genreDAO.getAllGenres());
        model.addAttribute("testID", testID);
        return "/testings/add_genre";
    }

    @PostMapping("/{id}/genre")
    public String addGenrePost(@ModelAttribute("genre") Genre genre,
                               Model model, Principal principal, @PathVariable(name = "id") Long testID) throws SQLException {
        testDAO.addGenre(testID, genre.getGenreID());
        return "redirect:/tests/{id}";
    }

    @GetMapping("/{id}/remove_genre/{id1}")
    public String removeGenreGet(Model model, Principal principal,
                                 @PathVariable(name = "id") Long testID,
                                 @PathVariable(name = "id1") Long genreID) {
        testDAO.removeGenre(testID, genreID);
        return "redirect:/tests/{id}";
    }
}
