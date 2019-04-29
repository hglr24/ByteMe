package data.external;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class ImageChooser {

    private static final String PNG = "png";
    private static final String JPG = "jpg";
    private static final String GIF = "gif";
    private static final List<String> IMAGE_EXTENSIONS = List.of(PNG, JPG, GIF);
    private static final String WILDCARD = "*.";

    private String myUserName;
    private DataManager myDataManager;

    public ImageChooser(String userName){
        myDataManager = new DataManager();
        myUserName = userName;
    }

    public String uploadImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        addExtensionsFilter(fileChooser);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            String savedName = myUserName + selectedFile.getName();
            try {
                System.out.println(selectedFile);
                myDataManager.setProfilePic(myUserName, selectedFile);
            } catch (SQLException e) {
                e.printStackTrace(); // Just for testing
            }
            return savedName;
        }
        return null;
    }

    private void addExtensionsFilter(FileChooser chooser) {
        for(String extension : IMAGE_EXTENSIONS){
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(extension, WILDCARD + extension);
            chooser.getExtensionFilters().add(extensionFilter);
        }
    }
}
