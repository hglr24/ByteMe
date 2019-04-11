package runner.internal;

import data.external.DataManager;
import engine.external.Level;
import engine.external.component.*;
import engine.external.Entity;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import runner.external.Game;
import runner.external.GameRunner;

import java.util.ArrayList;
import java.util.List;

public class RunnerTester extends Application {
    List<Entity> myDummyEntities = new ArrayList<>();

    public static void main(String[] args){launch(args);}

    @Override
    public void start (Stage primaryStage) throws Exception{
       // addDummyEntities();
//        Game dummyGame = new Game();
//        initializeGame(dummyGame);
     //   System.out.println("testing");
      //  DataManager dm = new DataManager();
       // dm.createGameFolder("game1");
       // dm.saveGameData("game1", dummyGame);

        GameRunner runner = new GameRunner("YeetGame1");
    }

//    private void initializeGame(Game dummyGame) {
//        Level level1 = new Level();
//        addDummyEntities(level1);
//        dummyGame.addLevel(level1);
//
//    }
//
//    private void addDummyEntities(Level level) {
//        Entity dummy1 = new Entity();
//        Entity dummy2 = new Entity();
//        Entity dummy3 = new Entity();
//        dummy1.addComponent(new XPositionComponent(30.0));
//        dummy1.addComponent(new YPositionComponent(30.0));
//        dummy1.addComponent(new ZPositionComponent(0.0));
//        dummy2.addComponent(new XPositionComponent(60.0));
//        dummy2.addComponent(new YPositionComponent(60.0));
//        dummy2.addComponent(new ZPositionComponent(0.0));
//        dummy3.addComponent(new XPositionComponent(90.0));
//        dummy3.addComponent(new YPositionComponent(90.0));
//        dummy3.addComponent(new ZPositionComponent(0.0));
//
//        dummy1.addComponent(new WidthComponent(50.0));
//        dummy1.addComponent(new HeightComponent(50.0));
//        dummy2.addComponent(new WidthComponent(30.0));
//        dummy2.addComponent(new HeightComponent(30.0));
//        dummy3.addComponent(new WidthComponent(80.0));
//        dummy3.addComponent(new HeightComponent(20.0));
//
//        dummy1.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy2.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy3.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//
//
//        level.addEntity(dummy1);
//        level.addEntity(dummy2);
//        level.addEntity(dummy3);
//    }

}
