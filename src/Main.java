import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base de données
        Connection conn = Cnx.getInstance();
        if (conn != null) {
            System.out.println("Connected to the database!");

            // Demander à l'utilisateur d'entrer les détails de la question
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le texte de la question : ");
            String questionText = scanner.nextLine();
            System.out.print("Entrez la réponse correcte à la question : ");
            String correctAnswer = scanner.nextLine();

            // Insérer la nouvelle question dans la base de données
            insertQuestion(questionText, correctAnswer);
        } else {
            System.out.println("Failed to connect to the database!");
        }
    }

    public static void insertQuestion(String questionText, String correctAnswer) {
        // Créer un objet QuestionDAO avec la connexion
        QuestionDAO questionDAO = new QuestionDAO(Cnx.getInstance());

        // Créer une nouvelle question
        Question question = new Question(1, questionText, null, correctAnswer);

        // ID du quiz associé à la question
        int quizId = 1; // Assurez-vous que cet ID correspond à un quiz existant dans la base de données

        try {
            // Insérer la nouvelle question dans la base de données
            questionDAO.insertQuestion(question, quizId);
            System.out.println("La question a été insérée avec succès !");
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de l'insertion de la question : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
