import entities.Category;
import entities.Product;
import entities.Transaction;
import enums.Type;
import repository.DBConnection;
import service.CategoryService;
import service.ProductService;
import service.TransactionService;
import serviceImpls.CategoryServiceImpl;
import serviceImpls.ProductServiceImpl;
import serviceImpls.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DBConnection dbConnection = new DBConnection();
    private static final CategoryService categoryService = new CategoryServiceImpl(dbConnection);
    private static final ProductService productService = new ProductServiceImpl(dbConnection, categoryService);
    private static final TransactionService transactionService = new TransactionServiceImpl(dbConnection, productService);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Store Inventory Management ===");
            System.out.println("1. Product Menu");
            System.out.println("2. Category Menu");
            System.out.println("3. Transaction Menu");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> productMenu();
                case 2 -> categoryMenu();
                case 3 -> transactionMenu();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void productMenu() {
        System.out.println("\n--- Product Menu ---");
        System.out.println("""
                1. Add Product
                2. View All Products
                3. Get Product by ID
                4. Get Product by Name
                5. Get Low Stock Products
                6. Update Product
                7. Delete Product
                8. Update Stock
                0. Back
                """);

        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1 -> {
                List<Category> categories = categoryService.getAllCategories(10,0);
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter product price: ");
                BigDecimal price = scanner.nextBigDecimal();
                scanner.nextLine();
                System.out.print("Enter stock: ");
                Integer stock = scanner.nextInt();
                scanner.nextLine();
                categories.forEach(category -> {
                    System.out.println("ID: "+category.getId()+" Name: "+category.getName());
                });
                System.out.print("Enter category Id you wish to add your product into: ");
                Integer categoryId = scanner.nextInt();
                Category category = categoryService.getCategoryById(categoryId);
                Product product = new Product(name, price, stock, category);
                System.out.println(productService.addProduct(product));
            }
            case 2 -> {
                List<Product> productList = productService.getAllProducts(10, 0);
                productList
                        .forEach(
                                product -> {
                                    System.out.println("Product Info:");
                                    System.out.println("Id: "+product.getId());
                                    System.out.println("Name: "+product.getName());
                                    System.out.println("Category: "+product.getCategory().getName());
                                    System.out.println("Price: "+ product.getPrice());
                                    System.out.println("Available Stock: "+product.getStock());
                                    System.out.println(" ");
                                    System.out.println("---------------");
                                    System.out.println(" ");
                                }
                        );
            }
            case 3 -> {
                System.out.print("Enter product ID: ");
                Integer id = scanner.nextInt();
                Product product =  productService.getProductById(id);
                System.out.println("Product Info:");
                System.out.println("Id: "+product.getId());
                System.out.println("Name: "+product.getName());
                System.out.println("Category: "+product.getCategory().getName());
                System.out.println("Price: "+ product.getPrice());
                System.out.println("Available Stock: "+product.getStock());
                System.out.println(" ");
                System.out.println("---------------");
                System.out.println(" ");
            }
            case 4 -> {
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                Product product = productService.getProductByName(name);
                System.out.println("Product Info:");
                System.out.println("Id: "+product.getId());
                System.out.println("Name: "+product.getName());
                System.out.println("Category: "+product.getCategory().getName());
                System.out.println("Price: "+ product.getPrice());
                System.out.println("Available Stock: "+product.getStock());
                System.out.println(" ");
                System.out.println("---------------");
                System.out.println(" ");
            }
            case 5 -> {
                List<Product>  productList =  productService.getLowStockProducts(10, 0);
                productList
                        .forEach(
                                product -> {
                                    System.out.println("Product Info:");
                                    System.out.println("Id: "+product.getId());
                                    System.out.println("Name: "+product.getName());
                                    System.out.println("Category: "+product.getCategory().getName());
                                    System.out.println("Price: "+ product.getPrice());
                                    System.out.println("Available Stock: "+product.getStock());
                                    System.out.println(" ");
                                    System.out.println("---------------");
                                    System.out.println(" ");
                                }
                        );
            }
            case 6 -> {
                System.out.print("Enter product ID to update: ");
                int id = Integer.parseInt(scanner.nextLine());
                Product existing = productService.getProductById(id);
                if (existing == null) {
                    System.out.println("Product not found.");
                    return;
                }
                System.out.print("New name (or press Enter to keep " + existing.getName() + "): ");
                String newName = scanner.nextLine();
                System.out.print("New price (or press Enter to keep " + existing.getPrice() + "): ");
                String priceInput = scanner.nextLine();
                System.out.print("New stock (or press Enter to keep " + existing.getStock() + "): ");
                String stockInput = scanner.nextLine();
                existing.setName(newName.isEmpty() ? existing.getName() : newName);
                existing.setPrice(priceInput.isEmpty() ? existing.getPrice() : new BigDecimal(priceInput));
                existing.setStock(stockInput.isEmpty() ? existing.getStock() : Integer.parseInt(stockInput));
                System.out.println(productService.updateProduct(existing));
            }
            case 7 -> {
                System.out.print("Enter product ID to delete: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.println(productService.deleteProduct(id));
            }
            case 8 -> {
                System.out.print("Enter product ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter quantity to update: ");
                int qty = Integer.parseInt(scanner.nextLine());
                System.out.println(productService.updateStock(id, qty));
            }
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void categoryMenu() {
        System.out.println("\n--- Category Menu ---");
        System.out.println("""
                1. Add Category
                2. View All Categories
                3. Get Category by ID
                4. Get Category by Name
                5. Update Category
                6. Delete Category
                0. Back
                """);

        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1 -> {
                System.out.print("Enter category name: ");
                String name = scanner.nextLine();
                System.out.println("Enter description for the category: ");
                String description = scanner.nextLine();
                System.out.println(categoryService.addCategory(new Category(name,description)));
            }
            case 2 -> {
                List<Category> categoryList = categoryService.getAllCategories(10, 0);
                categoryList.forEach(category -> {
                    System.out.println("Category Info:");
                    System.out.println("Id: "+category.getId());
                    System.out.println("Name: "+category.getName());
                    System.out.println("Description: "+category.getDescription());
                    System.out.println(" ");
                    System.out.println("---------------");
                    System.out.println(" ");
                });
            }
            case 3 -> {
                System.out.print("Enter category ID: ");
                int id = scanner.nextInt();
                Category category = categoryService.getCategoryById(id);
                System.out.println("Category Info:");
                System.out.println("Id: "+category.getId());
                System.out.println("Name: "+category.getName());
                System.out.println("Description: "+category.getDescription());
                System.out.println(" ");
                System.out.println("---------------");
                System.out.println(" ");
            }
            case 4 -> {
                System.out.print("Enter category name: ");
                String name = scanner.nextLine();
                Category category = categoryService.getCategoryByName(name);
                System.out.println("Category Info:");
                System.out.println("Id: "+category.getId());
                System.out.println("Name: "+category.getName());
                System.out.println("Description: "+category.getDescription());
                System.out.println(" ");
                System.out.println("---------------");
                System.out.println(" ");
            }
            case 5 -> {
                System.out.print("Enter category ID to update: ");
                int id = Integer.parseInt(scanner.nextLine());
                Category cat = categoryService.getCategoryById(id);
                if (cat == null) {
                    System.out.println("Category not found.");
                    return;
                }
                System.out.print("Enter new name (or press Enter to keep " + cat.getName() + "): ");
                String newName = scanner.nextLine();
                System.out.println("Enter a new description if want to change:");
                String description = scanner.nextLine();
                if (!newName.isEmpty()) cat.setName(newName);
                if (!description.isEmpty()) cat.setDescription(description);
                System.out.println(categoryService.updateCategory(cat));
            }
            case 6 -> {
                System.out.print("Enter category ID to delete: ");
                int id = scanner.nextInt();
                System.out.println(categoryService.deleteCategory(id));
            }
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }

    private static void transactionMenu() {
        System.out.println("\n--- Transaction Menu ---");
        System.out.println("""
                1. Add Transaction
                2. View All Transactions
                3. Get Transaction by ID
                4. Get Transactions by Product
                5. Get Transactions by Category
                6. Get Transactions by Date
                7. Update Transaction
                8. Delete Transaction
                0. Back
                """);

        System.out.println("Enter choice:");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1 -> {
                System.out.print("Enter product ID: ");
                int pid = scanner.nextInt();
                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();

                scanner.nextLine();
                System.out.print("Enter type (IN/OUT): ");
                String inputType = scanner.nextLine().trim().toUpperCase();

                Type type = null;
                if (inputType.equals("IN")) type = Type.IN;
                else if (inputType.equals("OUT")) type = Type.OUT;
                else {
                    System.out.println("Invalid type input. Please try again.");
                    return; // or return;
                }

                Product product = productService.getProductById(pid);
                if (product == null) {
                    System.out.println("No product found with product ID "+pid);
                    break;
                }
                System.out.println("=============="+product.getId());
                Transaction transaction = new Transaction(product,qty,type,LocalDateTime.now());
                System.out.println(transactionService.addTransaction(transaction));

            }
            case 2 -> {
                List<Transaction> transactionList =  transactionService.getAllTransactions(10, 0);
                transactionList.forEach(
                        transaction -> {
                            System.out.println("Transaction Info:");
                            System.out.println("Id: "+transaction.getId());
                            System.out.println("Product Name: "+transaction.getProduct().getName());
                            System.out.println("Transaction type:  "+transaction.getType().name());
                            System.out.println("Quantity transacted: "+transaction.getQuantity());
                            System.out.println("Time: "+transaction.getLocalDateTime());
                            System.out.println(" ");
                            System.out.println("---------------");
                            System.out.println(" ");
                        }
                );
            }
            case 3 -> {
                System.out.print("Enter transaction ID: ");
                int id = scanner.nextInt();
                Transaction transaction = transactionService.getTransaction(id);
                System.out.println("Transaction Info:");
                System.out.println("Id: "+transaction.getId());
                System.out.println("Product Name: "+transaction.getProduct().getName());
                System.out.println("Transaction type:  "+transaction.getType().name());
                System.out.println("Quantity transacted: "+transaction.getQuantity());
                System.out.println("Time"+transaction.getLocalDateTime());
                System.out.println(" ");
                System.out.println("---------------");
                System.out.println(" ");
            }
            case 4 -> {
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                List<Transaction> transactionList = transactionService.getTransactionsByProduct(name);
                transactionList.forEach(
                        transaction -> {
                            System.out.println("Transaction Info:");
                            System.out.println("Id: "+transaction.getId());
                            System.out.println("Product Name: "+transaction.getProduct().getName());
                            System.out.println("Transaction type:  "+transaction.getType().name());
                            System.out.println("Quantity transacted: "+transaction.getQuantity());
                            System.out.println("Time"+transaction.getLocalDateTime());
                            System.out.println(" ");
                            System.out.println("---------------");
                            System.out.println(" ");
                        }
                );
            }
            case 5 -> {
                System.out.print("Enter category name: ");
                String name = scanner.nextLine();
                List<Transaction> transactionList=  transactionService.getTransactionsByCategory(name);
                transactionList.forEach(
                        transaction -> {
                            System.out.println("Transaction Info:");
                            System.out.println("Id: "+transaction.getId());
                            System.out.println("Product Name: "+transaction.getProduct().getName());
                            System.out.println("Transaction type:  "+transaction.getType().name());
                            System.out.println("Quantity transacted: "+transaction.getQuantity());
                            System.out.println("Time"+transaction.getLocalDateTime());
                            System.out.println(" ");
                            System.out.println("---------------");
                            System.out.println(" ");
                        }
                );
            }
            case 6 -> {
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                List<Transaction> transactionList =  transactionService.getTransactionsByDate(LocalDateTime.parse(date + "T00:00:00"));
                transactionList.forEach(
                        transaction -> {
                            System.out.println("Transaction Info:");
                            System.out.println("Id: "+transaction.getId());
                            System.out.println("Product Name: "+transaction.getProduct().getName());
                            System.out.println("Transaction type:  "+transaction.getType().name());
                            System.out.println("Quantity transacted: "+transaction.getQuantity());
                            System.out.println("Time"+transaction.getLocalDateTime());
                            System.out.println(" ");
                            System.out.println("---------------");
                            System.out.println(" ");
                        }
                );
            }
            case 7 -> {
                System.out.print("Enter transaction ID to update: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new type (IN/OUT): ");
                Type type = Type.valueOf(scanner.nextLine().toUpperCase());
                System.out.println(transactionService.updateTransaction(new Transaction(id, null, qty, type, LocalDateTime.now())));
            }
            case 8 -> {
                System.out.print("Enter transaction ID to delete: ");
                int id = scanner.nextInt();
                System.out.println(transactionService.deleteTransaction(id));
            }
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
}
