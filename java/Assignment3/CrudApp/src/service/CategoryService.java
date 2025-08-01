package service;

import entities.Category;

import java.util.List;

public interface CategoryService {

    String addCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Integer categoryId);
    Category getCategoryByName(String categoryName);
    String updateCategory(Category category);
    String deleteCategory(Integer categoryID);
}
