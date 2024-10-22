import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Créer une image de fond
        Image backgroundImage = new Image("file:///C:/Users/balki/Downloads/images.jpg");

        // Créer un StackPane pour placer le fond et la VBox centrée
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundImage(backgroundImage, null, null, null, null)));

        // Créer un VBox pour aligner les boutons verticalement
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

	        // Créer les boutons
     // Créer les boutons
        Button addStudentButton = new Button("Ajouter étudiant");	
        addStudentButton.setOnAction(e -> {
            // Créer et afficher l'interface pour ajouter un étudiant
            AddStudentInterface addStudentInterface = new AddStudentInterface();
            Stage addStudentStage = new Stage();
            addStudentInterface.start(addStudentStage);
        });
        addStudentButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button addQuestionButton = new Button("Ajouter question");
        addQuestionButton.setOnAction(e -> {
            // Créer et afficher l'interface pour ajouter un étudiant
            AddQuestionInterface AddQuestionInterface = new AddQuestionInterface();		
            Stage addQuestionStage = new Stage();
            AddQuestionInterface.start(addQuestionStage);
        });
        addQuestionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        Button viewListButton = new Button("Voir liste");
        viewListButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        viewListButton.setOnAction(event -> {
            // Create a new instance of StudentScoresTable
            StudentScoresTable studentScoresTable = new StudentScoresTable();
            Stage studentScoresTableStage = new Stage();

            // Call the start method to display the table
            studentScoresTable.start(studentScoresTableStage);
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

        // Ajouter les boutons à la VBox
        vbox.getChildren().addAll(addStudentButton, addQuestionButton, viewListButton,exitButton);

        // Ajouter la VBox au StackPane
        root.getChildren().add(vbox);

        // Créer la scène
        Scene scene = new Scene(root, 800, 600);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Interface Professeur");

        // Définir la scène
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
