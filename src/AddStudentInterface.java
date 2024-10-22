import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class AddStudentInterface extends Application {

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la base de données
        connectToDatabase();
        Image backgroundImage = new Image("file:///C:/Users/balki/Downloads/images.jpg");

        // Créer un StackPane pour placer le fond et la VBox centrée
        StackPane rootPane = new StackPane();
        rootPane.setBackground(new Background(new BackgroundImage(backgroundImage, null, null, null, null)));
        
        // Interface graphique pour ajouter un étudiant
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Champs de saisie pour le nom d'utilisateur et le mot de passe
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        TextField user_typeField = new TextField();
        user_typeField.setPromptText("le type de l'utilisateur");
        

        // Bouton pour ajouter l'étudiant
        Button addButton = new Button("Ajouter étudiant");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        addButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String user_type=user_typeField.getText();
            if (!username.isEmpty() && !password.isEmpty()&& !user_type.isEmpty() ){
                addStudentToDatabase(username, password, user_type);
                System.out.println("Étudiant ajouté avec succès !");
            } else {
                System.out.println("Veuillez saisir un nom d'utilisateur et un mot de passe.");
            }
        });

        // Ajouter les composants à la VBox
        Label label = new Label("Ajouter un nouvel étudiant");
        label.setStyle("-fx-text-fill: white;"); // Définit la couleur du texte en blanc

        root.getChildren().addAll(label, usernameField, passwordField, user_typeField, addButton);


        // Ajouter la VBox au StackPane
        rootPane.getChildren().add(root);

        // Créer la scène
        Scene scene = new Scene(rootPane, 300, 200);

        // Définir la scène
        primaryStage.setScene(scene);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Ajouter un étudiant");

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
    
    private void addStudentToDatabase(String username, String password, String user_type) {
        String query = "INSERT INTO utilsateurs (username, password,user_type) VALUES (?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, user_type);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'étudiant à la base de données : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
