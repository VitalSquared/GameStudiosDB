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
import ru.nsu.spirin.gamestudios.model.entity.Category;
import ru.nsu.spirin.gamestudios.service.CategoryService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin_panel/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String indexCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("url", "/admin_panel/categories");
        return "admin/categories";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newCategory(@ModelAttribute("category") Category category) {
        return "/admin/new_category";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            return "admin/new_category";
        }
        categoryService.createNewCategory(category);
        return "redirect:/admin_panel/categories";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editCategory(Model model,
                               @PathVariable("id") Long categoryID) {
        model.addAttribute("categoryID", categoryID);
        model.addAttribute("category", categoryService.getCategoryByID(categoryID));
        return "/admin/edit_category";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.POST)
    public String updateCategory(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable("id") Long categoryID) {
        model.addAttribute("categoryID", categoryID);

        if (bindingResult.hasErrors()) {
            return "admin/edit_category";
        }

        categoryService.updateCategory(categoryID, category);
        return "redirect:/admin_panel/categories";
    }
}
