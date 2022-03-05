package ru.nsu.spirin.gamestudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.spirin.gamestudios.dao.*;
import ru.nsu.spirin.gamestudios.model.entity.Category;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin_panel/categories")
public class CategoryController {
    private final StudioDAO studioDAO;
    private final CategoryDAO categoryDAO;
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public CategoryController(StudioDAO studioDAO,
                                CategoryDAO categoryDAO,
                                DepartmentDAO departmentDAO,
                                EmployeeDAO employeeDAO,
                                AccountDAO accountDAO) {
        this.studioDAO = studioDAO;
        this.categoryDAO = categoryDAO;
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String indexCategories(Model model, Principal principal) {
        List<Category> categories = categoryDAO.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("url", "/admin_panel/categories");
        return "admin/categories";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newCategory(@ModelAttribute("category") Category category, Model model, Principal principal) {
        return "/admin/new_category";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute("category") Category category, Principal principal) throws SQLException {
        categoryDAO.newCategory(category);
        return "redirect:/admin_panel/categories";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCategory(Model model, @PathVariable("id") Long categoryID, Principal principal) {
        model.addAttribute("category", categoryDAO.getCategoryByID(categoryID));
        return "/admin/edit_category";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("category") Category category, Principal principal,
                         @PathVariable("id") Long id) throws SQLException {
        categoryDAO.updateCategory(id, category);
        return "redirect:/admin_panel/categories";
    }
}
