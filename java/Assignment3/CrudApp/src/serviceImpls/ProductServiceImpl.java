package serviceImpls;

import entities.Category;
import entities.Product;
import repository.DBConnection;
import service.CategoryService;
import service.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private DBConnection dbConnection;
    private CategoryService categoryService;

    public ProductServiceImpl(DBConnection dbConnection, CategoryService categoryService) {
        this.dbConnection = dbConnection;
        this.categoryService = categoryService;
    }


    @Override
    public String addProduct(Product product) {
        if (product == null) return "Product is null";
        if (product.getStock() == null) return "Product stock is null";
        if (product.getCategory() == null || product.getCategory().getId() == null) return "Product category or category ID is null";
        String query = "INSERT INTO products (name, price, stock, category_id)\n" +
                "VALUES (?, ?, ?, ?)";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2,product.getPrice());
            preparedStatement.setInt(3, product.getStock());
            preparedStatement.setInt(4, product.getCategory().getId());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted>0) return "Product has been added succesfully!";
            else return "Product failed to add";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public List<Product> getAllProducts(int limit, int offset) {
        String query = "SELECT id, name, price, stock, category_id FROM products LIMIT ? OFFSET ?";
        List<Product> productList = new ArrayList<>();

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    product.setStock(resultSet.getInt("stock"));
                    int categoryId = resultSet.getInt("category_id");
                    Category category = categoryService.getCategoryById(categoryId);
                    product.setCategory(category);
                    productList.add(product);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productList;
    }


    @Override
    public Product getProductByName(String name) {

        String query  = "SELECT * FROM products WHERE name = ? ";

        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setStock(resultSet.getInt("stock"));
                int categoryId = resultSet.getInt("category_id");
                Category category = categoryService.getCategoryById(categoryId);
                product.setCategory(category);
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        String query  = "SELECT * FROM products WHERE id = ? ";
        Product product = new Product();

        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setStock(resultSet.getInt("stock"));
                int categoryId = resultSet.getInt("category_id");
                Category category = categoryService.getCategoryById(categoryId);
                product.setCategory(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public List<Product> getLowStockProducts(int limit, int offset ) {
        String query = "SELECT * FROM products WHERE stock <=10 LIMIT ? OFFSET ?";
        List<Product> productList = new ArrayList<>();

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    product.setStock(resultSet.getInt("stock"));
                    int categoryId = resultSet.getInt("category_id");
                    Category category = categoryService.getCategoryById(categoryId);
                    product.setCategory(category);
                    productList.add(product);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }

    @Override
    public String updateProduct(Product updatedProduct) {
        Product originalProduct = getProductById(updatedProduct.getId());
        if (originalProduct == null) return "Product not found.";

        StringBuilder query = new StringBuilder("UPDATE products SET ");
        List<Object> parameters = new ArrayList<>();

        if (updatedProduct.getName() != null && !updatedProduct.getName().equals(originalProduct.getName())) {
            query.append("name = ?, ");
            parameters.add(updatedProduct.getName());
        }

        if (updatedProduct.getPrice() != null && updatedProduct.getPrice().compareTo(originalProduct.getPrice()) != 0) {
            query.append("price = ?, ");
            parameters.add(updatedProduct.getPrice());
        }

        if (updatedProduct.getStock() != null && !updatedProduct.getStock().equals(originalProduct.getStock())) {
            query.append("stock = ?, ");
            parameters.add(updatedProduct.getStock());
        }

        if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null &&
                !updatedProduct.getCategory().getId().equals(originalProduct.getCategory().getId())) {
            query.append("category_id = ?, ");
            parameters.add(updatedProduct.getCategory().getId());
        }

        if (parameters.isEmpty()) return "Nothing to update.";

        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        parameters.add(updatedProduct.getId());

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? "Product updated successfully." : "Update failed.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public String deleteProduct(Integer productId) {
        String query = "DELETE FROM products WHERE id = ?";
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){
            preparedStatement.setInt(1, productId);
            int result = preparedStatement.executeUpdate();
            if(result >= 1) return "Product has been deleted successfully";
            else  return "Product failed to delete";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String reduceStock(Integer productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return "Invalid quantity to reduce.";
        }

        String query = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, quantity);     // reduce by this amount
            preparedStatement.setInt(2, productId);    // product to update
            preparedStatement.setInt(3, quantity);     // ensure enough stock exists

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "Stock reduced by " + quantity + ".";
            } else {
                return "Product not found or insufficient stock.";
            }

        } catch (SQLException e) {
            return "Error reducing stock: " + e.getMessage();
        }
    }


    @Override
    public String updateStock(Integer productId, Integer quantity) {
        if (quantity == null || quantity < 0) {
            return "Invalid stock quantity.";
        }

        String query = "UPDATE products SET stock = ? WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Stock updated successfully.";
            } else {
                return "Product not found.";
            }

        } catch (SQLException e) {
            return "Error updating stock: " + e.getMessage();
        }
    }

}
