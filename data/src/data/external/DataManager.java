package data.external;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DataManager implements ExternalData{

    private static final String CREATED_GAMES_DIRECTORY = "created_games";
    public static final String XML_EXTENSION = ".xml";
    public static final String GAME_DATA = "game_data";
    private static final String GAME_INFO = "game_info";

    private XStream mySerializer;
    private DatabaseEngine myDatabaseEngine;

    public DataManager(){
        mySerializer = new XStream(new DomDriver());
//        myDatabaseEngine = new DatabaseEngine();
    }

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

    public void createGameFolder(String folderName, String gameName){

        createGameFolder(folderName);
//        if (!myDatabaseEngine.open()){
////            System.out.println("Couldn't connect to database");
//            return;
//        }
//        myDatabaseEngine.createEntryForNewGame(gameName);
//        myDatabaseEngine.close();
    }

    @Override
    public void createGameFolder(String folderName) {
        try {
            Files.createDirectories(Paths.get(CREATED_GAMES_DIRECTORY + File.separator + folderName));
        } catch (IOException e) {
            System.out.println("Could not create directory");
            e.printStackTrace();
        }
    }

    @Override
    public void saveObjectToXML(String path, Object objectToBeSaved) {
        String myRawXML = mySerializer.toXML(objectToBeSaved);
        writeToXML(path, myRawXML);
    }

    @Override
    public Object loadObjectFromXML(String path) {
        String rawXML = readFromXML(path);
        return mySerializer.fromXML(rawXML);
    }

    @Override
    public void saveGameData(String gameName, Object gameObject) {
        String path = transformGameNameToPath(gameName, GAME_DATA);
        saveObjectToXML(path, gameObject);
//        String myRawXML = mySerializer.toXML(gameObject);
//        if (! myDatabaseEngine.open()){
//            System.out.println("Couldn't load to database because couldn't connect");
//        }
//        myDatabaseEngine.updateGameEntryData(gameName, myRawXML);
//        myDatabaseEngine.close();

    }

    public Object loadGameInfo(String gameName){
        return loadObjectFromXML(transformGameNameToPath(gameName, GAME_INFO));
    }

    public void saveGameInfo(String gameName, Object gameInfoObject){
        String path = transformGameNameToPath(gameName, GAME_INFO);
        saveObjectToXML(path, gameInfoObject);
//        String myRawXML = mySerializer.toXML(gameInfoObject);
//        if (! myDatabaseEngine.open()){
//            System.out.println("Couldn't load to database because couldn't connect");
//        }
//        myDatabaseEngine.updateGameEntryInfo(gameName, myRawXML);
//        myDatabaseEngine.close();
    }

    @Override
    public void saveAssets(String gameName, List<String> assets) {

    }

    @Override
    public Object loadGameData(String gameName) {

        return loadObjectFromXML(transformGameNameToPath(gameName, GAME_DATA));
//        if(!myDatabaseEngine.open()){
//            System.out.println("Couldn't connect");
//        }
//        Object ret = mySerializer.fromXML(myDatabaseEngine.loadGameData(gameName));
//        myDatabaseEngine.close();
//        return ret;
    }

    private String readFromXML(String path) {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        StringBuilder rawXML = new StringBuilder();
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                rawXML.append(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Cannot read XML file");;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                System.out.println("Could not close files");
            }
        }
        return rawXML.toString();
    }

    public void printGameNames(){
        File file = new File(CREATED_GAMES_DIRECTORY);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        System.out.println(Arrays.toString(directories));
    }

    private String transformGameNameToPath(String gameName, String filename) {
        return CREATED_GAMES_DIRECTORY + File.separator + gameName + File.separator + filename + XML_EXTENSION;
    }

    private void writeToXML(String path, String rawXML){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(path));
            fileWriter.write(rawXML);
        } catch (IOException exception){
            System.out.println("In catch block");
            exception.printStackTrace();
        } finally {
            System.out.println("In finally block");
            try {
                if (fileWriter != null){
                    fileWriter.close();
                }
            } catch (IOException e){
                System.out.println("Couldn't close file");
            }
        }
    }
}
