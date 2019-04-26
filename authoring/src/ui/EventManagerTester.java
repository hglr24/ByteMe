package ui;


import engine.external.actions.*;
import engine.external.component.XVelocityComponent;
import engine.external.component.YVelocityComponent;
import engine.external.conditions.GreaterThanCondition;
import engine.external.conditions.LessThanCondition;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.events.Event;
import engine.external.events.LeftCollisionEvent;
import engine.external.events.RightCollisionEvent;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ui.manager.EventManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry Ross
 */
public class EventManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        ObjectManager manager = new ObjectManager(new SimpleObjectProperty<>());
        addTestLabels(manager.getLabelManager());
        AuthoringEntity testEntity = populateTestObjects(manager);
        testEntity.getInteractionListing().add("object2");
        testEntity.getInteractionListing().add("object3");
        testEntity.getEvents().add(new Event("object1"));
        testEntity.getEvents().add(new Event(testEntity.getPropertyMap().get(EntityField.LABEL)));
        makeTestEvents(testEntity);
        EventManager testEventManager = new EventManager(testEntity);

        testEventManager.show();
    }

    private void addTestLabels(LabelManager testLabels) {
        testLabels.addLabel(EntityField.GROUP, "Enemies");
        testLabels.addLabel(EntityField.GROUP, "Platforms");
    }

    private AuthoringEntity populateTestObjects(ObjectManager manager) {
        AuthoringEntity a = new AuthoringEntity("object1", manager);
        manager.addEntityType(a, "");
        return a;
    }

    private void makeTestEvents(AuthoringEntity currentEntity){
        List<Event> myTesterEvents = new ArrayList<>();
        RightCollisionEvent myRightCollisionEvent =  new RightCollisionEvent(currentEntity.getPropertyMap().get(EntityField.LABEL),
               "object2");
        myRightCollisionEvent.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE, -10.0));
        myRightCollisionEvent.addActions(new HealthAction(NumericAction.ModifyType.RELATIVE, -10.0));
        myTesterEvents.add(myRightCollisionEvent);
        LeftCollisionEvent myLeftCollisionEvent =  new LeftCollisionEvent(currentEntity.getPropertyMap().get(EntityField.LABEL),
                "object3");
        myLeftCollisionEvent.addActions(new SpriteAction("happySprite"));

        myTesterEvents.add(myLeftCollisionEvent);
        Event myMultipleConditionEvent = new Event("object1");
        GreaterThanCondition myXBound = new GreaterThanCondition(new XPositionComponent(0.0).getClass(),30.0);
        LessThanCondition myYBound = new LessThanCondition(new YPositionComponent(20.0).getClass(),0.0);
        myMultipleConditionEvent.addActions(new SpriteAction("Happy Sprite"));

        Event myHealthEvent = new Event("object1");
        GreaterThanCondition myXVel = new GreaterThanCondition(new XVelocityComponent(10.0).getClass(),2.0);
        GreaterThanCondition myYVel = new GreaterThanCondition(new YVelocityComponent(10.0).getClass(),2.0);
        myHealthEvent.addConditions(myXVel);
        myHealthEvent.addConditions(myYVel);
        myHealthEvent.addActions(new YAccelerationAction(NumericAction.ModifyType.RELATIVE,10.0));
        myTesterEvents.add(myHealthEvent);
        myMultipleConditionEvent.addConditions(myXBound);
        myMultipleConditionEvent.addConditions(myYBound);
        myTesterEvents.add(myMultipleConditionEvent);
        currentEntity.getEvents().addAll(myTesterEvents);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
