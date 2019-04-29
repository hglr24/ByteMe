package data.external;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameRating) {
            GameRating other = (GameRating) obj;
            Boolean[] conditions = {myUsername.equals(other.myUsername), myAuthorName.equals(other.myAuthorName), myComment.equals(other.myComment), myGameName.equals(other.myGameName), myNumberOfStars == other.myNumberOfStars};
            return Arrays.stream(conditions).allMatch(val -> Boolean.TRUE.equals(val));
        }
        return false;
    }
}
