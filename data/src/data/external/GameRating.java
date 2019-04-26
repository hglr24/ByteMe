package data.external;

public class GameRating {
    private String myUsername;
    private String myGameName;
    private int myNumberOfStars;
    private String myComment;

    public GameRating(String username, String gameName, int numberOfStars, String comment) {
        myUsername = username;
        myGameName = gameName;
        myNumberOfStars = numberOfStars;
        myComment = comment;
    }

    public String getUsername() {
        return myUsername;
    }

    public String getGameName() {
        return myGameName;
    }

    public int getNumberOfStars() {
        return myNumberOfStars;
    }

    public String getComment() {
        return myComment;
    }

}
