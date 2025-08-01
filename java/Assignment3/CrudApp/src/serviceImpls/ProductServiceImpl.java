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

    public ProductServiceImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public String addProduct(Product product) {
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
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        return null;
    }

    @Override
    public List<Product> getLowStockProducts() {
        return Collections.emptyList();
    }

    @Override
    public String updateProduct(Product product) {
        return "";
    }

    @Override
    public String deleteProduct(Integer productId) {
        return "";
    }

    @Override
    public String reduceStock(Integer productId) {
        return "";
    }

    @Override
    public String updateStock(Integer productId, Integer quantity) {
        return "";
    }
}
