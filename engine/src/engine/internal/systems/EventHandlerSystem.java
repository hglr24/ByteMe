package engine.internal.systems;


import engine.external.IEventEngine;
import engine.external.component.Component;
import engine.external.Engine;

import java.util.ArrayList;


import java.util.Collection;


/**
 * @author Hsingchih Tang
 * Invoke all Events in a Level on each game loop update
 * Every Event takes care of its own condition checking and action effects in the excecute() call
 */
public class EventHandlerSystem extends VoogaSystem {

    Collection<IEventEngine> myEvents;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components, and a collection
     * of Events defined by the game author that should take some engine.external.actions when certain engine.external.conditions are met
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     * @param events collection of Events that need to be invoked on each game loop
     */
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine, Collection<IEventEngine> events) {
        super(requiredComponents, engine);
        myEvents = events;
    }

    /**
     * Loops through the collection of Events and invokes the Events by execute() call
     * Each Event object has its own engine.external.conditions and engine.external.actions defined and embedded in the Event class,
     * the execute() call would invoke an Event to check whether its pre-defined engine.external.conditions have
     * been met such that its engine.external.actions could be triggered
     */
    @Override
    protected void run() {
        for (IEventEngine e : myEvents) {
            e.execute(new ArrayList<>(this.getEntities()), this.getKeyCodes());
        }
    }
}
