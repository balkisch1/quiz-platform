import java.util.List;
import java.util.Map;

public class QuizSession {
    private int session_id;
    private int quiz_id;
    private int student_id;
    private int question_id;
    private String student_answer;
    private String questions;
    private String answers;
    private int score;

    // Constructeur
    public QuizSession(int session_id, int quiz_id, int student_id, int question_id, String student_answer, int score) {
        this.session_id = session_id;
        this.quiz_id = quiz_id;
        this.student_id = student_id;
        this.question_id = question_id;
        this.student_answer = student_answer;
        this.questions = questions;
        this.answers = answers;
        this.score = score;
    }


	// Getters et setters
    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getStudent_answer() {
        return student_answer;
    }

    public void setStudent_answer(String student_answer) {
        this.student_answer = student_answer;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
   

    // Méthode pour mettre à jour le score en fonction des réponses de l'étudiant
    public void updateScore(List<Question> questions, List<String> studentAnswers) {
        if (questions.size() != studentAnswers.size()) {
            throw new IllegalArgumentException("Le nombre de questions et de réponses ne correspond pas.");
        }

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            String studentAnswer = studentAnswers.get(i);
            if (studentAnswer.equals(question.getCorrectAnswer())) {
                correctAnswers++;
            }
        }

        // Calculer le score en pourcentage
        score = (int) Math.round((double) correctAnswers / totalQuestions * 100);
    }
}
