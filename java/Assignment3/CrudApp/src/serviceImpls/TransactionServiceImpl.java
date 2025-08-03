package serviceImpls;

import entities.Product;
import entities.Transaction;
import enums.Type;
import repository.DBConnection;
import service.ProductService;
import service.TransactionService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final DBConnection dbConnection;

    private final ProductService productService;


    public TransactionServiceImpl(DBConnection dbConnection, ProductService productService) {
        this.dbConnection = dbConnection;
        this.productService = productService;
    }

    @Override
    public String addTransaction(Transaction transaction) {
        System.out.println(transaction.getProduct().getId()+"-----------------");

        String query = "INSERT INTO transactions (product_id, quantity, type, date) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, transaction.getProduct().getId());
                stmt.setInt(2, transaction.getQuantity());
                stmt.setString(3, transaction.getType().name()); // ✅ fix is here
                stmt.setObject(4, transaction.getLocalDateTime());

                int rows = stmt.executeUpdate();

                productService.reduceStock(transaction.getProduct().getId(), transaction.getQuantity());

                conn.commit();
                return rows > 0 ? "Transaction added successfully." : "Failed to add transaction.";
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    return "Rollback failed: " + rollbackEx.getMessage();
                }
            }
            return "Error: " + e.getMessage();

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    @Override
    public List<Transaction> getAllTransactions(int limit, int offset) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions LIMIT ? OFFSET ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
              {
                  preparedStatement.setInt(1, limit);
                  preparedStatement.setInt(2, offset);
                  ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public Transaction getTransaction(Integer transactionID) {
        String query = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTransaction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Transaction> getTransactionsByCategory(String categoryName) {
        String query = """
            SELECT t.* FROM transactions t
            JOIN products p ON t.product_id = p.id
            JOIN categories c ON p.category_id = c.id
            WHERE c.name = ?
        """;

        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByProduct(String productName) {
        String query = """
            SELECT t.* FROM transactions t
            JOIN products p ON t.product_id = p.id
            WHERE p.name = ?
        """;

        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByDate(LocalDateTime dateTime) {
        String query = "SELECT * FROM transactions WHERE DATE(date) = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(dateTime.toLocalDate()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public String updateTransaction(Transaction transaction) {
        String query = "UPDATE transactions SET product_id = ?, quantity = ?, type = ?, date = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getProduct().getId());
            stmt.setInt(2, transaction.getQuantity());
            stmt.setObject(3, transaction.getType());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getLocalDateTime()));
            stmt.setInt(5, transaction.getId());

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Transaction updated successfully." : "Transaction not found.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String deleteTransaction(Integer transactionId) {
        String query = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transactionId);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "Transaction deleted successfully." : "Transaction not found.";

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }


    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        Integer productId = rs.getInt("product_id");
        Product product = productService.getProductById(productId);
        t.setProduct(product);
        t.setQuantity(rs.getInt("quantity"));
        t.setType(Type.valueOf( rs.getString("type")));
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) {
            t.setLocalDateTime(ts.toLocalDateTime());
        } else {
            t.setLocalDateTime(null);
        }
        return t;
    }
}
