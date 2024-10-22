import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentScoresTable extends Application {

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        connectToDatabase(); // Connect to the database
        Image backgroundImage = new Image("file:///C:/Users/balki/Downloads/images.jpg");

        // Créer un StackPane pour placer le fond et la VBox centrée
        StackPane rootPane = new StackPane();
        rootPane.setBackground(new Background(new BackgroundImage(backgroundImage, null, null, null, null)));
        // Create table view columns
        TableColumn<Utilsateurs, String> nameColumn = new TableColumn<>("username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Utilsateurs, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableView<Utilsateurs> tableView = new TableView<>();
        tableView.getColumns().addAll(nameColumn, scoreColumn);

        // Fetch data from the database and populate the table
        try {
            ObservableList<Utilsateurs> User = getUserWithScores();
            tableView.setItems(User);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        VBox root = new VBox(tableView);
        root.setPadding(new Insets(20));
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Student Scores");
        primaryStage.show();
    }
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
        } catch (SQLException e) {	
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
    private ObservableList<Utilsateurs> getUserWithScores() throws SQLException {
        ObservableList<Utilsateurs> userList = FXCollections.observableArrayList();

        String query = "SELECT * FROM utilsateurs WHERE user_type = 'student'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                Utilsateurs user = new Utilsateurs(username, score);
                userList.add(user);
            }
        }

        return userList;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
