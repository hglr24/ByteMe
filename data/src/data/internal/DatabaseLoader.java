package data.internal;

import data.external.DataManager;

import java.io.File;
import java.util.List;

public class DatabaseLoader {

    public static void main(String[] args) {
        loadGameCenterDataFromCreatedGames();
//        loadGameCenterImages();
        loadImage();
    }

    private static void loadImage(){
        DataManager dm = new DataManager();
        dm.saveImage("flappy_bird", new File("Images/flappy_bird.png"));
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