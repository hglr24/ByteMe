package data.external;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ImageChooser {

    private static final String PNG = "png";
    private static final String JPG = "jpg";
    private static final String GIF = "gif";
    private static final List<String> IMAGE_EXTENSIONS = List.of(PNG, JPG, GIF);
    private static final String WILDCARD = "*.";

    private String myPrefix;
    private DataManager myDataManager;

    public ImageChooser(String prefix){
        myDataManager = new DataManager();
        myPrefix = prefix;
    }

    public String uploadImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        addExtensionsFilter(fileChooser);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            String savedName = myPrefix + selectedFile.getName();
            myDataManager.saveImage(myPrefix + selectedFile.getName(), selectedFile);
            return savedName;
        }
        return myPrefix;
    }

    private void addExtensionsFilter(FileChooser chooser) {
        for(String extension : IMAGE_EXTENSIONS){
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(extension, WILDCARD + extension);
            chooser.getExtensionFilters().add(extensionFilter);
        }
    }

}
