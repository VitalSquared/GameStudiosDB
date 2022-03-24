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
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.Genre;
import ru.nsu.spirin.gamestudios.model.entity.Test;
import ru.nsu.spirin.gamestudios.model.entity.account.Account;
import ru.nsu.spirin.gamestudios.service.*;

import javax.validation.Valid;
import java.security.Principal;
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
    private final AccountService accountService;
    private final EmployeeService employeeService;

    @Autowired
    public TestController(TestService testService,
                          TestAppService testAppService,
                          GenreService genreService,
                          TestStatusService testStatusService,
                          TestAppResultService testAppResultService,
                          StudioService studioService,
                          AccountService accountService,
                          EmployeeService employeeService) {
        this.testService = testService;
        this.testAppService = testAppService;
        this.genreService = genreService;
        this.testStatusService = testStatusService;
        this.testAppResultService = testAppResultService;
        this.studioService = studioService;
        this.accountService = accountService;
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexTests(Model model) {
        List<Test> tests = this.testService.getAllTests();
        model.addAttribute("tests", tests);
        model.addAttribute("all_statuses", this.testStatusService.getAllStatuses());
        return "testings/testings";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String viewTest(Model model, Principal principal,
                           @PathVariable(name = "id") Long testID) {
        User user = (User) ((Authentication) principal).getPrincipal();
        Account account = this.accountService.findAccountByEmail(user.getUsername());
        Employee employee = this.employeeService.getEmployeeByID(account.getEmployeeID());
        model.addAttribute("studioID", employee.getStudioID());
        model.addAttribute("test", this.testService.getTestByID(testID));
        model.addAttribute("apps", this.testAppService.getAppsForTest(testID));
        model.addAttribute("genres", this.genreService.getGenresByTestID(testID));
        model.addAttribute("all_statuses", this.testStatusService.getAllStatuses());
        model.addAttribute("all_results", this.testAppResultService.getAllResults());
        model.addAttribute("all_studios", this.studioService.getAllStudios());
        model.addAttribute("genres_left", this.genreService.getAllGenresExceptTest(testID));
        return "testings/view_testing";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newTest(@ModelAttribute("test") Test test) {
        return "/testings/new_testing";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/new",  method = RequestMethod.POST)
    public String createTest(@Valid @ModelAttribute("test") Test test, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/testings/new_testing";
        }

        test.setStatusID(0L);
        this.testService.newTest(test);
        return "redirect:/tests";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editTest(Model model, @PathVariable("id") Long testID) {
        model.addAttribute("testID", testID);
        model.addAttribute("test", testService.getTestByID(testID));
        return "/testings/edit_testing";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/edit",  method = RequestMethod.POST)
    public String updateTest(@Valid @ModelAttribute("test") Test test,
                             BindingResult bindingResult,
                             Model model,
                             @PathVariable("id") Long testID) {
        model.addAttribute("testID", testID);

        if (bindingResult.hasErrors()) {
            return "/testings/edit_testing";
        }

        this.testService.updateTest(testID, test);
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/start_test", method = RequestMethod.GET)
    public String startTest(@PathVariable("id") Long id) {
        this.testService.startTest(id);
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/finish_test", method = RequestMethod.GET)
    public String finishTest(@PathVariable("id") Long id) {
        this.testService.finishTest(id);
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/results_test", method = RequestMethod.GET)
    public String resultsReadyTest(@PathVariable("id") Long id) {
        this.testService.resultsReadyTest(id);
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/cancel_test", method = RequestMethod.GET)
    public String cancelTest(@PathVariable("id") Long id) {
        this.testService.cancelTest(id);
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/delete_test", method = RequestMethod.GET)
    public String deleteTest(@PathVariable("id") Long id) {
        this.testService.deleteTest(id);
        return "redirect:/tests";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/add_genre", method = RequestMethod.GET)
    public String addGenreGet(@ModelAttribute("genre") Genre genre, Model model,
                              @PathVariable(name = "id") Long testID) {
        model.addAttribute("genres", this.genreService.getAllGenresExceptTest(testID));
        model.addAttribute("testID", testID);
        return "/testings/add_genre";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/genre", method = RequestMethod.POST)
    public String addGenrePost(@ModelAttribute("genre") Genre genre,
                               @PathVariable(name = "id") Long testID) {
        this.testService.addGenre(testID, genre.getGenreID());
        return "redirect:/tests/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/{id}/remove_genre/{id1}", method = RequestMethod.GET)
    public String removeGenreGet(@PathVariable(name = "id") Long testID,
                                 @PathVariable(name = "id1") Long genreID) {
        this.testService.removeGenre(testID, genreID);
        return "redirect:/tests/{id}";
    }
}
