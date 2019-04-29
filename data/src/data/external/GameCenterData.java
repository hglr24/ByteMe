package data.external;

public class GameCenterData {
    private String myTitle;
    private String myImageLocation;
    private String myDescription;
    private String myAuthorName;

    public GameCenterData(String title, String description, String imageLocation, String authorName){
        myTitle = title;
        myDescription = description;
        myImageLocation = imageLocation;
        myAuthorName = authorName;
    }

    public GameCenterData() {

    }

    public String getTitle() {
        return myTitle;
    }

    public void setTitle(String title) {
        myTitle = title;
    }

    public String getImageLocation() {
        return myImageLocation;
    }

    public void setImageLocation(String imageLocation) {
        myImageLocation = imageLocation;
    }

    public String getDescription() {
        return myDescription;
    }

    public void setDescription(String description) {
        myDescription = description;
    }

    public String getAuthorName() {
        return myAuthorName;
    }

    public void setAuthorName(String authorName) {
        myAuthorName = authorName;
    }

    @Deprecated
    public GameCenterData(String folder, String title, String description, String imageLocation, String authorName){

    }

    @Deprecated
    public String getFolderName() {
        return "";
    }

    @Deprecated
    public void setFolderName(String folderName) {
    }
}
