import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private List<Question> questions;

    public Quiz(int id, String title, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
    // Getters and setters
}
