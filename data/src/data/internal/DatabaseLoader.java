package data.internal;

import data.external.DataManager;
import data.external.DatabaseEngine;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader {

    public static void main(String[] args) {
        DatabaseEngine.getInstance().open();
//        createUser("Ryan", "testPassword");
//        createUser("Person2", "passwordTwo");
//        validateUsers();
//        System.out.println();
//        createUsers();
//        setProfPic("Megan", "Images/megan_prof_pic.png");
        setBio("ICouldAlwaysEat", "You can just press control+alt+any random letter and IntelliJ will make something " +
                "good happen to your code");
        setBio("gamez_n_gainz", "Mind yo modules");
        setBio("DimaFayyad", "Relatable content");
        setBio("MeganPHibbones", "Hey! My name is Megan, follow me on LinkedIn!");
        setBio("fzero", "I'm just trying to be a Prime Citizen");
        setBio("carrie", "Hey Megan *dramatic pause* want to play smash?");
        setBio("harry", "If you need help with CSS, my office hours are 4-6...am");
        setBio("hsing", "If you want to get an A take 270 but if you are actually trying to learn something take a different class");
        setBio("louis", "Hey! My name is Megan, follow me on LinkedIn!");
        setBio("michaelzhang", "It's time");
        setBio("ryryculhane", "Hey! My name is Megan, follow me on LinkedIn!");
        setBio("Megan", "Hey! My name is Megan, follow me on LinkedIn!");

        DatabaseEngine.getInstance().close();
    }

    private static void createUser(String userName, String password){
        DataManager dm = new DataManager();
        System.out.println(dm.createUser(userName, password));
    }

    private static void setBio(String userName, String bio) {
        DataManager dm = new DataManager();
        try {
            dm.setBio(userName, bio);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void setProfPic(String userName, String path) {
        DataManager dm = new DataManager();
        try {
            dm.setProfilePic(userName, new File(path));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUsers(){
        createUser("ICouldAlwaysEat", "OrNotEat");
        createUser("gamez_n_gainz", "bruh");
        createUser("DimaFayyad", "cs725)@5wt3");
        createUser("MeganPHibbones", "welcome2thaCenter");
        createUser("fzero", "coachesdontplay");
        createUser("carrie","morelikecarrietheteam");
        createUser("harry", "morelikeharrytheteam");
        createUser("hsing", "letmejustmakeapasswordsystem");
        createUser("louis", "rowingandgrowing");
        createUser("michaelzhang", "RIP");
        createUser("ryryculhane", "noJDBCdriver");
        createUser("Megan", "password");
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
