package data.external;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataManager implements ExternalData{



    @Override
    public Object loadGameDisplay(String gameName) {
        return null;
    }

    @Override
    public void saveGameDisplay(String gameName) {

    }

    @Override
    public Object loadGame(String gameName) {
        return null;
    }

    @Override
    public Object continueGame(String gameName) {
        return null;
    }

    @Override
    public void saveGame(String gameName) {

    }

    @Override
    public void createFolder(String folderName) {
        try {
            Files.createDirectories(Paths.get("created_games" + File.separator + folderName));
        } catch (IOException e) {
            System.out.println("Could not create directory");
            e.printStackTrace();
        }
    }
}
