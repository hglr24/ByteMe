package data.internal;

import data.external.DataManager;
import data.external.DatabaseEngine;

import java.io.File;
import java.util.List;

public class DatabaseLoader {

    public static void main(String[] args) {
        DatabaseEngine.getInstance().open();
//        createUser("Ryan", "testPassword");
//        createUser("Person2", "passwordTwo");
//        validateUsers();
//        System.out.println();
//        loadGameCenterDataFromCreatedGames();
        loadImage();
        DatabaseEngine.getInstance().close();
    }

    private static void createUser(String userName, String password){
        DataManager dm = new DataManager();
        System.out.println(dm.createUser(userName, password));
    }

    private static void validateUsers(){
        DataManager dm = new DataManager();
        System.out.println(dm.validateUser("Ryan", "testPassword"));
        System.out.println(dm.validateUser("Ryan", "wrongPassword"));
        System.out.println(dm.validateUser("FakePerson", "testPassword"));
        System.out.println(dm.validateUser("FakePerson", "wrongPassword"));
    }

    private static void loadImage(){
        DataManager dm = new DataManager();
        dm.saveImage("mushroom.png", new File("Images/mushroom.png"));
    }

    private static void loadGameCenterImages() {
        DataManager dm = new DataManager();
        List<String> imagesToLoad = List.of("celeste.jpg", "default_game.png", "downwell.jpg", "fez.jpg", "inside" +
                ".jpg", "limbo.jpg", "mario.jpg", "ori.jpg", "smb.jpg", "spelunky.png", "yooka.jpg");
        for (String image : imagesToLoad){
            File imageFile = new File("center/data/game_information/images/" + image);
            dm.saveImage("center/data/game_information/images/" + image, imageFile);
        }
    }

    private static void loadGameCenterDataFromCreatedGames() {
        DataManager dm = new DataManager();
        List<String> gamesToLoad = List.of("celeste","downwell","fez","inside","limbo","mario","ori","spelunky",
                "supermeatboy","yooka");
        for (String game: gamesToLoad){
            dm.saveGameDataFromFolder(game);
        }
    }
}
