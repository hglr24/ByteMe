package ui.main;

import data.external.DatabaseEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.ErrorBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainTester extends Application {
    private static final ResourceBundle GENERAL_RESOURCES = ResourceBundle.getBundle("authoring_general");

    @Override
    public void start(Stage stage) {
        MainGUI testGUI = new MainGUI();
        testGUI.launch();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DatabaseEngine.getInstance().close();
        clearFolder(GENERAL_RESOURCES.getString("images_filepath"));
        clearFolder(GENERAL_RESOURCES.getString("audio_filepath"));
    }

    //outerDirectory - folder that needs sub-folders "defaults" and "user-uploaded"
    private void clearFolder(String outerDirectoryPath){
        File outerDirectory = new File(outerDirectoryPath);
        System.out.println("Directory: " + outerDirectory.getName());
        List<File> didntDelete = new ArrayList<>();
        for(File file : outerDirectory.listFiles()){
            file.delete();
        }
//        if(didntDelete.size() > 0){
//            StringBuilder stringBuilder = new StringBuilder();
//            for(File f : didntDelete){
//                stringBuilder.append(f.getName() + "\n");
//            }
//            ErrorBox errorBox = new ErrorBox("Didn't delete", stringBuilder.toString());
//            errorBox.display();
//        }
    }
}
