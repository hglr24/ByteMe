package runner.internal;

import engine.external.component.Component;
import engine.external.Entity;
import engine.external.component.PositionComponent;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import runner.external.GameRunner;

import java.util.ArrayList;
import java.util.List;

public class RunnerTester extends Application {
    List<Entity> myDummyEntities = new ArrayList<>();

    @Override
    public void start (Stage primaryStage) throws Exception{
        addDummyEntities();
        System.out.println("testing");
        GameRunner runner = new GameRunner(myDummyEntities, 400, 200, primaryStage);
    }

    private void addDummyEntities() {
        Entity dummy1 = new Entity();
        Entity dummy2 = new Entity();
        Entity dummy3 = new Entity();
        dummy1.addComponent(new PositionComponent(new Point3D(20.0, 20.0, 0.0)));
        dummy2.addComponent(new PositionComponent(new Point3D(50.0, 50.0, 0.0)));
        dummy3.addComponent(new PositionComponent(new Point3D(80.0, 80.0, 0.0)));

        myDummyEntities.add(dummy1);
        myDummyEntities.add(dummy2);
        myDummyEntities.add(dummy3);
    }

}
