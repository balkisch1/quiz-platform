import java.util.List;

public class Question {
    private int id;
    private String text;
    private String correctAnswer;
    private List<String> options;

    public Question(int id, String text, String correctAnswer, List<String> options) {
        this.id = id;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getInstruction() {
        return "Chaque question comporte une seule réponse.";
    }

}
