package rwanda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class District {
    private static final String URL = "jdbc:postgresql://localhost:5432/rwanda";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mupro";
    public static void main(String[] args)throws IOException {
        Connection conn = null;
        Scanner scanner = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Established connection");

            String sql = "INSERT INTO district (id, provinceId, districtName) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            scanner = new Scanner(new BufferedReader(new FileReader("District.txt")));
            scanner.useDelimiter("[,\n]");

            while (scanner.hasNext()){
                int id = scanner.nextInt();
                int provinceId = scanner.nextInt();
                String districtName = scanner.next().trim();

                System.out.println("id: " + id + "Province referenced: " + provinceId + "District: " + districtName);
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, provinceId);
                preparedStatement.setString(3, districtName);
                preparedStatement.executeUpdate();
            }
            System.out.println("Entered data successfully !!");

        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }catch (FileNotFoundException e){
            System.out.println("Did not get file " + e.getMessage());
        }
    }
}