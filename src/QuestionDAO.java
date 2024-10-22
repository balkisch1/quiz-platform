import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionDAO {
    private Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertQuestion(Question question, int quizId) throws SQLException {
        String query = "INSERT INTO question (quiz_id, text, correct_answer) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            statement.setString(2, question.getText());
            statement.setString(3, question.getCorrectAnswer());
            statement.executeUpdate();
        }
    }
}
