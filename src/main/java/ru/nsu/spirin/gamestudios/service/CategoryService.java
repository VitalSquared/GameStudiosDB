package ru.nsu.spirin.gamestudios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.nsu.spirin.gamestudios.model.entity.Category;
import ru.nsu.spirin.gamestudios.repository.CategoryRepository;

import java.util.List;

@Controller
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByID(Long categoryID) {
        return this.categoryRepository.findByID(categoryID);
    }

    public void createNewCategory(Category category) {
        if (category.getName() == null) {
            return;
        }
        this.categoryRepository.save(category);
    }

    public void updateCategory(Long categoryID, Category category) {
        if (category.getName() == null) {
            return;
        }
        this.categoryRepository.update(categoryID, category);
    }
}
