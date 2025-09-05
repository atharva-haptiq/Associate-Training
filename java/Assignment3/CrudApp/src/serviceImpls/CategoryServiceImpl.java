package serviceImpls;

import entities.Category;
import repository.DBConnection;
import service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryServiceImpl implements CategoryService {

    private final DBConnection dbConnection;

    public CategoryServiceImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public String addCategory(Category category) {
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int rows = preparedStatement.executeUpdate();
            return rows > 0 ? "Category added successfully." : "Failed to add category.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<Category> getAllCategories(int limit , int offset) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories LIMIT ? OFFSET ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);)
              {

                  preparedStatement.setInt(1, limit);
                  preparedStatement.setInt(2, offset);
                  ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        String query  ="SELECT * FROM categories WHERE id = ?";
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, categoryId);

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
        String query = "SELECT * FROM categories WHERE name = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                return category;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateCategory(Category updatedCategory) {
        Category originalCategory = getCategoryById(updatedCategory.getId());
        if (originalCategory == null) return "Category not found.";

        StringBuilder query = new StringBuilder("UPDATE categories SET ");
        List<Object> parameters = new ArrayList<>();

        if (updatedCategory.getName() != null && !updatedCategory.getName().equals(originalCategory.getName())) {
            query.append("name = ?, ");
            parameters.add(updatedCategory.getName());
        }
        if (updatedCategory.getDescription() != null && !updatedCategory.getDescription().equals(originalCategory.getDescription())) {
            query.append("description = ?, ");
            parameters.add(updatedCategory.getDescription());
        }

        if (parameters.isEmpty()) return "Nothing to update.";

        query.setLength(query.length() - 2); // remove trailing comma
        query.append(" WHERE id = ?");
        parameters.add(updatedCategory.getId());

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? "Category updated successfully." : "Update failed.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public String deleteCategory(Integer categoryId) {
        String query = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, categoryId);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "Category deleted successfully." : "Delete failed. Category may not exist.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}

