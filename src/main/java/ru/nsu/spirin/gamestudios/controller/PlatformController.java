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
import ru.nsu.spirin.gamestudios.model.entity.Platform;
import ru.nsu.spirin.gamestudios.service.PlatformService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin_panel/platforms")
public class PlatformController {
    private final PlatformService platformService;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexPlatforms(Model model) {
        List<Platform> platforms = platformService.getAllPlatforms();
        model.addAttribute("platforms", platforms);
        model.addAttribute("url", "/admin_panel/platforms");
        return "admin/platforms";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newPlatform(@ModelAttribute("platform") Platform platform) {
        return "/admin/new_platform";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createPlatform(@Valid @ModelAttribute("platform") Platform platform, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new_platform";
        }
        platformService.createNewPlatform(platform);
        return "redirect:/admin_panel/platforms";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editPlatform(Model model, @PathVariable("id") Long platformID) {
        model.addAttribute("platformID", platformID);
        model.addAttribute("platform", platformService.getPlatformByID(platformID));
        return "/admin/edit_platform";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updatePlatform(@Valid @ModelAttribute("platform") Platform platform,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable("id") Long platformID) {
        model.addAttribute("platformID", platformID);

        if (bindingResult.hasErrors()) {
            return "admin/edit_platform";
        }

        platformService.updatePlatform(platformID, platform);
        return "redirect:/admin_panel/platforms";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
    public String deletePlatform(@PathVariable("id") Long platformID) {
        platformService.deletePlatform(platformID);
        return "redirect:/admin_panel/platforms";
    }
}
