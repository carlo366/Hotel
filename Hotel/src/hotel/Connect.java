package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection connectDB() throws Exception  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Ganti parameter koneksi sesuai dengan database Anda
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Lepaskan exception setelah menampilkan stack trace
        }
    }
}
