import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizSessionDAO {
    private Connection connection;

    public QuizSessionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertQuizSession(QuizSession session) throws SQLException {
        String query = "INSERT INTO QuizSession (quiz_id, student_id, question_id, student_answer, questions, answers, score) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, session.getQuiz_id());
            statement.setInt(2, session.getStudent_id());
            statement.setInt(3, session.getQuestion_id());
            statement.setString(4, session.getStudent_answer());
            statement.setString(5, session.getQuestions());
            statement.setString(6, session.getAnswers());
            statement.setInt(7, session.getScore());		
            statement.executeUpdate();
        }
    }

    public List<QuizSession> getAllQuizSessions() throws SQLException {
        List<QuizSession> sessions = new ArrayList<>();
        String query = "SELECT * FROM QuizSession";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int session_id = resultSet.getInt("session_id");
                int quiz_id = resultSet.getInt("quiz_id");
                int student_id = resultSet.getInt("student_id");
                int question_id = resultSet.getInt("question_id");
                String student_answer = resultSet.getString("student_answer");
                String questions = resultSet.getString("questions");
                String answers = resultSet.getString("answers");
                int score = resultSet.getInt("score");
                // Ajouter le paramètre manquant pour answers
                QuizSession session = new QuizSession(session_id, quiz_id, student_id, question_id, student_answer, score);
                sessions.add(session);
            }
        }
        return sessions;
    }


    // Autres méthodes pour mettre à jour, supprimer, etc., selon vos besoins
}
