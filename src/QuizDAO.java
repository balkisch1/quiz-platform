import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private Connection connection;

    public QuizDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour récupérer tous les quiz de la base de données
    public List<Quiz> getAllQuizzes() throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT id, title FROM quiz";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                List<Question> questions = getQuestionsForQuiz(id); // Récupérer les questions associées au quiz
                quizzes.add(new Quiz(id, title, questions));
            }
        }
        return quizzes;
    }

    // Méthode pour récupérer les questions associées à un quiz spécifique
    private List<Question> getQuestionsForQuiz(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT id, text, correct_answer FROM question WHERE quiz_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String text = resultSet.getString("text");
                    String correctAnswer = resultSet.getString("correct_answer");
                    // Récupérer les options de réponse associées à la question si nécessaire
                    List<String> options = getOptionsForQuestion(id);
                    questions.add(new Question(id, text, correctAnswer, options));
                }
            }
        }
        return questions;
    }	
    // Méthode pour récupérer les options de réponse associées à une question spécifique
    private List<String> getOptionsForQuestion(int questionId) throws SQLException {
        List<String> options = new ArrayList<>();
        // Implémenter la logique pour récupérer les options de réponse de la base de données
        return options;
    }
    public void insertQuiz(Quiz quiz) throws SQLException {
        String insertQuery = "INSERT INTO quiz (title) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, quiz.getTitle());
            statement.executeUpdate();
        }
    }
    // Autres méthodes pour créer, mettre à jour et supprimer des quiz selon les besoins
}
