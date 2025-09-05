package service;

import entities.Product;

import java.util.List;

public interface ProductService {

    String addProduct(Product product);
    List<Product> getAllProducts(int limit, int offset);
    Product getProductByName(String name);
    Product getProductById(Integer id);
    List<Product> getLowStockProducts(int limit, int offset);
    String updateProduct(Product product);
    String deleteProduct(Integer productId);
    String reduceStock(Integer productId, Integer quantity);
    String updateStock(Integer productId, Integer quantity);
}