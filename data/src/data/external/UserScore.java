package data.external;

public class UserScore implements Comparable<UserScore> {

    private String myUserName;
    private Double myScore;

    public UserScore(String userName, Double score) {
        myUserName = userName;
        myScore = score;
    }

    public String getUserName() {
        return myUserName;
    }

    public Double getScore() {
        return myScore;
    }

    @Override
    public int compareTo(UserScore o) {
        return myScore.compareTo(o.myScore);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserScore) {
            UserScore otherScore = (UserScore) obj;
            return myUserName.equals(otherScore.myUserName) && myScore.equals(otherScore.myScore);
        }
        return false;
    }
}
