package com.stlang.bakery_shop.services;


import com.stlang.bakery_shop.domains.Category;
import com.stlang.bakery_shop.exceptions.DataExistingException;
import com.stlang.bakery_shop.exceptions.DataNotFoundException;
import com.stlang.bakery_shop.repositories.CategoryRepository;
import com.stlang.bakery_shop.services.iservices.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        if(existingCategory != null) {
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Category addCategory(String name) throws DataExistingException {
        categoryRepository.findByName(name.trim()).orElseThrow(
                () -> new DataExistingException("Category already exists !")
        );
        categoryRepository.save(Category.builder().name(name.trim()).build());
        return null;
    }

    @Override
    public void deleteCategory(long id) throws DataNotFoundException {
        categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Category not found !"));
        categoryRepository.deleteById(id);
    }
}
