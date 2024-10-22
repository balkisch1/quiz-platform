import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnx {
    private static Connection conn;

    // Méthode pour obtenir une instance de connexion à la base de données
    public static Connection getInstance() {
        if (conn == null) {
            try {
                // Charger le pilote JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Informations de connexion à la base de données
                String url = "jdbc:mysql://localhost:3306/quiz";
                String username = "root";
                String password = "";

                // Créer la connexion
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the database!");
            } catch (ClassNotFoundException e) {
                System.out.println("Failed to load JDBC driver!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database!");
                e.printStackTrace();
            }
        }
        return conn;
    }
}
