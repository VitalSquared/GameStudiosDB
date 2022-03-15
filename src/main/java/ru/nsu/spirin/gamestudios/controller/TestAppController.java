package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.spirin.gamestudios.model.entity.Employee;
import ru.nsu.spirin.gamestudios.model.entity.TestApp;
import ru.nsu.spirin.gamestudios.service.*;

import java.sql.Date;
import java.time.Instant;

@Controller
@RequestMapping("/tests")
public class TestAppController {
    private final TestAppService testAppService;
    private final TestService testService;
    private final EmployeeService employeeService;
    private final TestAppResultService testAppResultService;
    private final StudioService studioService;

    @Autowired
    public TestAppController(TestAppService testAppService,
                             TestService testService,
                             EmployeeService employeeService,
                             TestAppResultService testAppResultService,
                             StudioService studioService) {
        this.testAppService = testAppService;
        this.employeeService = employeeService;
        this.testService = testService;
        this.testAppResultService = testAppResultService;
        this.studioService = studioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR', 'DEVELOPER')")
    @RequestMapping(path = "/apps/{id}", method = RequestMethod.GET)
    public String viewApp(Model model, @PathVariable(name = "id") Long appID) {
        TestApp app = testAppService.getAppByID(appID);
        model.addAttribute("app", app);
        model.addAttribute("test", testService.getTestByID(app.getTestID()));
        model.addAttribute("employees", employeeService.getEmployeesByTestApp(appID));
        model.addAttribute("curDate", Date.from(Instant.now()));
        model.addAttribute("all_results", testAppResultService.getAllResults());
        model.addAttribute("all_studios", studioService.getAllStudios());
        return "testings/view_testapp";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/accept", method = RequestMethod.GET)
    public String acceptApp(@PathVariable(name = "id") Long appID) {
        testAppService.acceptApp(appID);
        return "redirect:/tests/apps/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/decline", method = RequestMethod.GET)
    public String declineApp(@PathVariable(name = "id") Long appID) {
        testAppService.declineApp(appID);
        return "redirect:/tests";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/add_employee", method = RequestMethod.GET)
    public String addEmployeeGet(@ModelAttribute("employee") Employee employee, Model model,
                                 @PathVariable(name = "id") Long appID) {
        TestApp app = testAppService.getAppByID(appID);
        model.addAttribute("employees", employeeService.getEmployeesByStudio(app.getStudioID()));
        model.addAttribute("appID", appID);
        return "/testings/testapp_add_employee";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/employee", method = RequestMethod.POST)
    public String addEmployeePost(@ModelAttribute("employee") Employee employee,
                                  @PathVariable(name = "id") Long appID) {
        testAppService.addEmployee(appID, employee.getEmployeeID());
        return "redirect:/tests/apps/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR', 'STUDIO_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/remove_employee/{id1}", method = RequestMethod.GET)
    public String removeEmployeeGet(@PathVariable(name = "id") Long appID,
                                    @PathVariable(name = "id1") Long employeeID) {
        testAppService.removeEmployee(appID, employeeID);
        return "redirect:/tests/apps/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/success", method = RequestMethod.GET)
    public String successEndResult(@PathVariable(name = "id") Long appID) {
        testAppService.successEndResult(appID);
        return "redirect:/tests/apps/{id}";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GENERAL_DIRECTOR')")
    @RequestMapping(path = "/apps/{id}/failure", method = RequestMethod.GET)
    public String failureEndResult(@PathVariable(name = "id") Long appID) {
        testAppService.failureEndResult(appID);
        return "redirect:/tests/apps/{id}";
    }
}
