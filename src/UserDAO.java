import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public Utilsateurs login(String username, String password) throws SQLException {
        String query = "SELECT * FROM 	utilsateurs	 WHERE username=? AND password=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userTypeStr = rs.getString("userType");
                    UserType userType = UserType.valueOf(userTypeStr);
                    return new Utilsateurs(username, password, userType);
                }
            }
        }
        return null; // Les informations de connexion sont incorrectes
    }
    public void saveScore(String username, int score) throws SQLException {
        String queryUpdate = "UPDATE utilsateurs SET score = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(queryUpdate)) {
            pstmt.setInt(1, score);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

}
