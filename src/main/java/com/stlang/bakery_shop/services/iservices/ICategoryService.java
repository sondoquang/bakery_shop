package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();
    Category findCategoryById(long id);
    Category updateCategory(Category category);
    Category addCategory(String name);
    void deleteCategory(long id);

}
