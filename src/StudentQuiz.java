import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentQuiz extends Application {
	String user_name;
    private Connection connection;
    StudentQuiz(String user_name){
    	this.user_name=user_name;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        connectToDatabase();
        AtomicInteger score = new AtomicInteger(0);

        List<Question> questions = getQuestionsFromDatabase();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        for (Question question : questions) {
            Label instructionLabel = new Label("Consigne : " + question.getInstruction());
            instructionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label questionLabel = new Label("Question : " + question.getText());
            questionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            VBox questionBox = new VBox(5, instructionLabel, questionLabel);

            ToggleGroup toggleGroup = new ToggleGroup();
            List<RadioButton> optionButtons = new ArrayList<>();
            for (String option : question.getOptions()) {
                RadioButton optionButton = new RadioButton(option);
                optionButton.setToggleGroup(toggleGroup);
                optionButtons.add(optionButton);
                optionButton.setStyle("-fx-font-size: 14px;");
            }

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(event -> {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    String selectedOption = selectedRadioButton.getText();
                    if (selectedOption.equals(question.getCorrectAnswer())) {
                        score.incrementAndGet();
                        System.out.println("Correct Answer! Score: " + score);
                    } else {
                        System.out.println("Wrong Answer! Score: " + score);
                    }
                } else {
                    System.out.println("Please select an option for question: " + question.getText());
                }
                updateUserScore(this.user_name, score.get());

            });
            submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

            HBox optionsBox = new HBox(10);
            optionsBox.getChildren().addAll(optionButtons);

            VBox questionContainer = new VBox(10, questionBox, optionsBox, submitButton);
            root.getChildren().add(questionContainer);
        }

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Student Quiz");
        primaryStage.show();

        // Mise à jour du score pour l'utilisateur "balkis"
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    private List<Question> getQuestionsFromDatabase() {
        List<Question> questions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM question");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                String correctAnswer = resultSet.getString("correctanswer");
                String optionsString = resultSet.getString("options");
                List<String> options = Arrays.asList(optionsString.split(","));
                questions.add(new Question(id, text, correctAnswer, options));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private void updateUserScore(String username, int newScore) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE utilsateurs SET score = ? WHERE username = ?");
            updateStatement.setInt(1, newScore);
            updateStatement.setString(2, username);
            updateStatement.executeUpdate();
            System.out.println("Score updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}