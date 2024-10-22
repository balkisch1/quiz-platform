import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AddQuestionInterface extends Application {

    private Connection connection;


    @Override
    public void start(Stage primaryStage) {
        connectToDatabase(); // Connexion à la base de données
        
        // Interface graphique pour ajouter un étudiant
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

Label titleLabel = new Label("Ajouter un étudiant");
titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField idField = new TextField();
        idField.setPromptText("Saisissez l'ID");
        // Champs de saisie pour la question et les options de réponse
        TextField textField = new TextField();
        textField.setPromptText("Saisissez la question");
        TextField option1Field = new TextField();
        option1Field.setPromptText("Option 1");
        TextField option2Field = new TextField();
        option2Field.setPromptText("Option 2");
        TextField option3Field = new TextField();
        option3Field.setPromptText("Option 3");
        TextField correctAnswerField = new TextField();
        correctAnswerField.setPromptText("Réponse correcte");
        // Bouton pour ajouter la question
        Button addButton = new Button("Ajouter question");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        addButton.setOnAction(e -> {
            try {
                // Récupérer les valeurs des champs de texte
                int id = Integer.parseInt(idField.getText());
                String text = textField.getText();
                String option1 = option1Field.getText();
                String option2 = option2Field.getText();
                String option3 = option3Field.getText();
                String correctAnswer = correctAnswerField.getText();

                // Vérifier si le champ ID est vide
                if (idField.getText().isEmpty()) {
                    // Gérer le cas où le champ ID est vide
                    System.out.println("Veuillez saisir un ID valide.");
                    return; // Arrêter l'exécution de la méthode
                }

                // Créer une instance de Question avec les valeurs récupérées
                List<String> options = Arrays.asList(option1, option2, option3);
                Question question = new Question(id, text, correctAnswer, options);

                // Ajouter la question à la base de données
                try {
                    insertQuestion(question);
                    System.out.println("Question ajoutée avec succès !");
                } catch (SQLException ex) {
                    System.out.println("Erreur lors de l'ajout de la question : " + ex.getMessage());
                }

                // Effacer les champs de texte après l'ajout
                idField.clear();
                textField.clear();
                option1Field.clear();
                option2Field.clear();
                option3Field.clear();
                correctAnswerField.clear();
            } catch (NumberFormatException ex) {
                // Gérer les exceptions liées à la conversion en entier
                System.out.println("Veuillez saisir un ID valide.");
            }
        });

        // Ajouter les éléments à la VBox
        root.getChildren().addAll(
                new Label("Ajouter une nouvelle question"),
                idField,
                textField,
                option1Field,
                option2Field,
                option3Field,
                correctAnswerField,
                addButton
        );

        // Créer la scène
        Scene scene = new Scene(root, 400, 400);

        // Définir la scène
        primaryStage.setScene(scene);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Ajouter une question");

        // Afficher la fenêtre
        primaryStage.show();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    public void insertQuestion(Question question) throws SQLException {
        String query = "INSERT INTO question (text, correctanswer, options) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, question.getText());
            statement.setString(2, question.getCorrectAnswer());
            statement.setString(3, String.join(",", question.getOptions())); // Join options with a comma
            statement.executeUpdate();
        }
    }

    public List<Question> getAllQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM question";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                String correctAnswer = resultSet.getString("correctanswer");
                String optionsString = resultSet.getString("options");
                List<String> options = Arrays.asList(optionsString.split(",")); // Split options by comma
                Question question =new Question(id, text, correctAnswer, options);
                questions.add(question);
            }
        }
        return questions;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
