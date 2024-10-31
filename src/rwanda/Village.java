package rwanda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Village {
    private static final String URL = "jdbc:postgresql://localhost:5432/rwanda";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mupro";

    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Established connection");

            String sql = "INSERT INTO village (id, cellId, villageName) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            BufferedReader readFile = new BufferedReader(new FileReader("Village.txt"));
            String villages;

            while ((villages = readFile.readLine()) != null) {
                String[] data = villages.split(",");

                try {
                    int villageId = Integer.parseInt(data[0]);
                    int cellId = Integer.parseInt(data[1]);
                    String villageName = data[2];

                    preparedStatement.setInt(1, villageId);
                    preparedStatement.setInt(2, cellId);
                    preparedStatement.setString(3, villageName);
                    preparedStatement.executeUpdate();

                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in data: " + villages);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Data missing in line: " + villages);
                }
            }

            System.out.println("Data entered successfully!");

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Connection closed");
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
