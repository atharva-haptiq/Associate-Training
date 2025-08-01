import entities.Category;
import entities.Product;
import repository.DBConnection;
import service.CategoryService;
import service.ProductService;
import serviceImpls.CategoryServiceImpl;
import serviceImpls.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DBConnection dbConnection = new DBConnection();
        ProductService productService = new ProductServiceImpl(dbConnection);

        CategoryService categoryService = new CategoryServiceImpl();

        List<Product> productList = productService.getAllProducts(10, 0);
        for (Product product: productList) System.out.println(product);

    }
}