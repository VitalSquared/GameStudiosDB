package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.model.entity.Studio;
import ru.nsu.spirin.gamestudios.model.entity.Test;
import ru.nsu.spirin.gamestudios.model.entity.TestAppResult;
import ru.nsu.spirin.gamestudios.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;
    private final TestAppService testAppService;
    private final GenreService genreService;
    private final TestStatusService testStatusService;
    private final TestAppResultService testAppResultService;
    private final StudioService studioService;

    @Autowired
    public TestController(TestService testService,
                          TestAppService testAppService,
                          GenreService genreService,
                          TestStatusService testStatusService,
                          TestAppResultService testAppResultService,
                          StudioService studioService) {
        this.testService = testService;
        this.testAppService = testAppService;
        this.genreService = genreService;
        this.testStatusService = testStatusService;
        this.testAppResultService = testAppResultService;
        this.studioService = studioService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        model.addAttribute("all_statuses", testStatusService.getAllStatuses());
        return "testings/testings";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String viewTest(Model model,
                           @PathVariable(name = "id") Long testID) {
        model.addAttribute("test", testService.getTestByID(testID));
        model.addAttribute("apps", testAppService.getAppsForTest(testID));
        model.addAttribute("genres", genreService.getGenresByTestID(testID));
        model.addAttribute("all_statuses", testStatusService.getAllStatuses());
        model.addAttribute("all_results", testAppResultService.getAllResults());
        model.addAttribute("all_studios", studioService.getAllStudios());
        return "testings/view_testing";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newTest(@ModelAttribute("test") Test test) {
        return "/testings/new_testing";
    }

    @RequestMapping(path = "/new",  method = RequestMethod.POST)
    public String createTest(@Valid @ModelAttribute("test") Test test, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/testings/new_testing";
        }

        test.setStatusID(0L);
        testService.newTest(test);
        return "redirect:/tests";
    }

    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editTest(Model model, @PathVariable("id") Long testID) {
        model.addAttribute("testID", testID);
        model.addAttribute("test", testService.getTestByID(testID));
        return "/testings/edit_testing";
    }

    @RequestMapping(path = "/{id}/edit",  method = RequestMethod.POST)
    public String updateTest(@Valid @ModelAttribute("test") Test test,
                             BindingResult bindingResult,
                             Model model,
                             @PathVariable("id") Long testID) {
        model.addAttribute("testID", testID);

        if (bindingResult.hasErrors()) {
            return "/testings/edit_testing";
        }

        testService.updateTest(testID, test);
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/start_test", method = RequestMethod.GET)
    public String startTest(@PathVariable("id") Long id) {
        testService.startTest(id);
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/finish_test", method = RequestMethod.GET)
    public String finishTest(@PathVariable("id") Long id) {
        testService.finishTest(id);
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/results_test", method = RequestMethod.GET)
    public String resultsReadyTest(@PathVariable("id") Long id) {
        testService.resultsReadyTest(id);
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/cancel_test", method = RequestMethod.GET)
    public String cancelTest(@PathVariable("id") Long id) {
        testService.cancelTest(id);
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/delete_test", method = RequestMethod.GET)
    public String deleteTest(@PathVariable("id") Long id) {
        testService.deleteTest(id);
        return "redirect:/tests";
    }

    @RequestMapping(path = "/{id}/add_genre", method = RequestMethod.GET)
    public String addGenreGet(@ModelAttribute("genre") Genre genre, Model model,
                              @PathVariable(name = "id") Long testID) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("testID", testID);
        return "/testings/add_genre";
    }

    @RequestMapping(path = "/{id}/genre", method = RequestMethod.POST)
    public String addGenrePost(@ModelAttribute("genre") Genre genre,
                               @PathVariable(name = "id") Long testID) {
        testService.addGenre(testID, genre.getGenreID());
        return "redirect:/tests/{id}";
    }

    @RequestMapping(path = "/{id}/remove_genre/{id1}", method = RequestMethod.GET)
    public String removeGenreGet(@PathVariable(name = "id") Long testID,
                                 @PathVariable(name = "id1") Long genreID) {
        testService.removeGenre(testID, genreID);
        return "redirect:/tests/{id}";
    }
}
