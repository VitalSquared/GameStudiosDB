package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.TestDAO;
import ru.nsu.spirin.gamestudios.model.entity.Test;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/testings")
public class TestController {
    private final TestDAO testDAO;

    @Autowired
    public TestController(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @GetMapping("")
    public String indexTests(Model model, Principal principal) {
        List<Test> tests = testDAO.getAllTests();
        model.addAttribute("tests", tests);
        return "testings/testings";
    }

    @GetMapping("/new")
    public String newTest(@ModelAttribute("test") Test test,
                              Model model, Principal principal) {

        return "/testings/new_testing";
    }

    @PostMapping("")
    public String create(@ModelAttribute("test") Test test,
                         Model model, Principal principal) throws SQLException {
        testDAO.newTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/edit")
    public String editTest(Model model, @PathVariable("id") Long testID) {
        model.addAttribute("test", testDAO.getTestByID(testID));
        return "/testings/edit_testing";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("test") Test test,
                         Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        testDAO.updateTest(id, test);
        return "redirect:/testings";
    }
}
