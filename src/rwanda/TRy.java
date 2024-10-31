package rwanda;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class TRy {
    private static final String URL = "jdbc:postgresql://localhost:5432/rwanda";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mupro";

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");

            String sql = "INSERT INTO district (id, provinceId, districtName) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            scanner = new Scanner(new BufferedReader(new FileReader("districts.txt")));
            scanner.useDelimiter("[,\n]");

            while (scanner.hasNext()) {
                int id = scanner.nextInt(); // Integer for district code
                int provinceId = scanner.nextInt();
                String districtName = scanner.next();

                // Display read data for confirmation
                System.out.println(id + " " + provinceId + " " + districtName);

                // Set parameters and execute insert
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, provinceId);
                preparedStatement.setString(3, districtName);
                preparedStatement.executeUpdate();
            }

            System.out.println("Data successfully inserted into the database.");

        } catch (SQLException e) {
            System.out.println("Database connection or query error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        } finally {
            // Close scanner and database resources
            try {
                if (scanner != null) scanner.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing database resources: " + e.getMessage());
            }
        }
    }
}
