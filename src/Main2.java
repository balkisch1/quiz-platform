import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main2 extends Application {
	
	  private Connection connection;
	  private static String loggedInUsername;
    @Override
    public void start(Stage primaryStage) {
        connectToDatabase();
       
        // Créer une image de fond
    	Image backgroundImage = new Image("file:///C:/Users/balki/Downloads/images.jpg");

    	StackPane root = new StackPane();
    	root.setBackground(new Background(new BackgroundImage(backgroundImage, null, null, null, null)));

    	// Créer un conteneur pour les champs de texte et les boutons
    	VBox vbox = new VBox(10);
    	vbox.setAlignment(Pos.CENTER);
    	vbox.setPadding(new Insets(20));

    	// Champ de texte pour le nom d'utilisateur
    	// Champ de texte pour le nom d'utilisateur
    	TextField usernameField = new TextField();
    	usernameField.setPromptText("Nom d'utilisateur");
    	usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-text-fill: #000000;");

    	// Champ de texte pour le mot de passe
    	PasswordField passwordField = new PasswordField();
    	passwordField.setPromptText("Mot de passe");
    	passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-text-fill: #000000;");


    	// Bouton de connexion
    	Button loginButton = new Button("Connexion");
    	loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Vert
    	loginButton.setOnAction(e -> {
    	    // Ajoutez ici votre logique pour la connexion
    		 String username = usernameField.getText();
    	     String password = passwordField.getText();
    	    
    	    // Supposons que vous avez une méthode pour vérifier le type d'utilisateur
    	    // et que vous avez une classe Utilisateur avec un attribut user_type
    	    
    	    String user_type = getuser_type(username, password);
    	    if (user_type != null) {
    	        if (user_type.equals("admin")) {
    	            // Redirection vers l'interface admin
    	            // Par exemple, vous pouvez utiliser ProfInterface
    	            ProfInterface profInterface = new ProfInterface();
    	            Stage profStage = new Stage();
    	            profInterface.start(profStage);
    	            //&& socre e
    	        } else if (user_type.equals("student")) {
    	            // Redirection vers l'interface étudiant
    	            // Par exemple, vous pouvez utiliser StudentQuiz
    	            StudentQuiz studentQuiz = new StudentQuiz(username);
    	            Stage quizStage = new Stage();
    	            studentQuiz.start(quizStage);
    	             // Mettre à jour le score dans la base de données
    	            ;
    	        } else {
    	            System.out.println("Type d'utilisateur non reconnu");
    	        }
    	    } else {
    	        System.out.println("Nom d'utilisateur ou mot de passe incorrect");
    	    }
    	    
    	});


    	// Bouton de sortie
    	Button exitButton = new Button("Quitter");
    	exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"); // Rouge
    	exitButton.setOnAction(e -> {
    	    Alert alert = new Alert(AlertType.CONFIRMATION);
    	    alert.setTitle("Confirmation");
    	    alert.setHeaderText("Voulez-vous vraiment quitter ?");
    	    alert.setContentText("Toute progression non sauvegardée sera perdue.");

    	    Optional<ButtonType> result = alert.showAndWait();
    	    if (result.isPresent() && result.get() == ButtonType.OK) {
    	        primaryStage.close();
    	    }
    	});


    	// Ajouter les composants au conteneur
    	vbox.getChildren().addAll(usernameField, passwordField, loginButton, exitButton);

    	// Ajouter le conteneur à la racine de la scène
    	root.getChildren().add(vbox);

    	// Créer la scène
    	Scene scene = new Scene(root, 600, 400);

    	// Définir la scène
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Connexion");
    	primaryStage.show();
    	

    }
    
  public String getuser_type(String username, String password) {
        String user_type = null;
        try {
            // Créer une déclaration SQL pour exécuter la requête
            String query = "SELECT user_type FROM utilsateurs WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Définir les paramètres de la requête
            statement.setString(1, username);
            statement.setString(2, password);
            
            // Exécuter la requête et obtenir le résultat
            ResultSet resultSet = statement.executeQuery();
            
            // Vérifier si un utilisateur correspondant a été trouvé
            if (resultSet.next()) {
                // Récupérer le type d'utilisateur de la colonne appropriée
                user_type = resultSet.getString("user_type");
            }
            
            // Fermer les ressources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_type;
    }
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
	public static void main(String[] args) {
        launch(args);
    }

	public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }
}
