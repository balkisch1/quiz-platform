public class Utilsateurs {
    private String username;
    private String password;
    private UserType userType;
    private int score;

    public Utilsateurs(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

	

    public Utilsateurs(String username, int score) {
        this.username = username;
        this.score = score;
    }
    


    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }


    // Getters and setters
}
