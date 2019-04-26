package data.external;

public class GameRating {
    private String myUsername;
    private String myGameName;
    private String myAuthorName;
    private int myNumberOfStars;
    private String myComment;

    public GameRating(String username, String gameName, String authorName, int numberOfStars, String comment) {
        myUsername = username;
        myGameName = gameName;
        myAuthorName = authorName;
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

    public String getAuthorName() {
        return myAuthorName;
    }

}
