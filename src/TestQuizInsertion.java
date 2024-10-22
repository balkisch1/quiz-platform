import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestQuizInsertion {
    public static void main(String[] args) throws SQLException {
        // Créer une connexion à la base de données
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "")) {
            // Créer un objet QuizDAO avec la connexion
            QuizDAO quizDAO = new QuizDAO(connection);
            Scanner scanner = new Scanner(System.in);
            
            // Demander à l'utilisateur de saisir l'ID du nouveau quiz
         /// Demander à l'utilisateur de saisir le titre du nouveau quiz
            System.out.print("Entrez le titre du nouveau quiz : ");
            String title = scanner.nextLine();

            // Demander à l'utilisateur de saisir une question pour le nouveau quiz
            List<Question> questions = new ArrayList<>();
            System.out.println("Entrez une question pour le nouveau quiz : ");

            // Demander à l'utilisateur de saisir l'ID de la question
         // Demander à l'utilisateur de saisir l'ID du nouveau quiz
            System.out.print("Entrez l'ID du nouveau quiz : ");
            String idStr = scanner.nextLine();
            int id = Integer.parseInt(idStr);

            // Demander à l'utilisateur de saisir le texte de la question
            System.out.print("Texte de la question : ");
            String questionText = scanner.nextLine();

         // Demander à l'utilisateur de saisir la réponse correcte à la question
            System.out.print("Réponse correcte à la question : ");
            String correctAnswer = scanner.nextLine();

            // Créer un objet Question avec les détails saisis, y compris la réponse correcte
            Question question = new Question(id, questionText, null, correctAnswer);
            questions.add(question);

            // Insérer la nouvelle question dans la base de données
            quizDAO.insertQuestion(question, id); // Assurez-vous de passer l'ID du quiz associé à cette question


       

            // Créer un nouveau quiz avec l'ID, le titre et la question saisie
            Quiz quiz = new Quiz(id, title, questions);

            // Insérer le nouveau quiz dans la base de données
            quizDAO.insertQuiz(quiz);

            System.out.println("Le nouveau quiz a été inséré avec succès !");

}
    }
}
