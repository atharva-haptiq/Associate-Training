package serviceImpls;

import entities.Category;
import repository.DBConnection;
import service.CategoryService;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {


    private DBConnection dbConnection = new DBConnection();

    @Override
    public String addCategory(Category category) {
        return "";
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        String query  ="SELECT * FROM categories WHERE id = ?";
        Integer id  =1;
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Integer productId = resultSet.getInt("id");
                String name =  resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category();
                category.setId(productId);
                category.setName(name);
                category.setDescription(description);
                return category;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return null;
    }

    @Override
    public String updateCategory(Category category) {
        return "";
    }

    @Override
    public String deleteCategory(Integer categoryID) {
        return "";
    }
}
