package hrda.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url = "jdbc:mysql://localhost:3306/hrda";
    private final String user = "atharva.haptiq";
    private final String password = "pass1234";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        try (Connection connection = db.getConnection()) {
            System.out.println("DB connected successfully!");

            /*
            String query = "SELECT * FROM Department WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, 1);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String department = resultSet.getString("department");

                        System.out.println("ID: " + id);
                        System.out.println("Department: " + department);
                    }
                }
            }
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
