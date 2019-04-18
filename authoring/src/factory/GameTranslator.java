package factory;

import engine.external.Entity;
import engine.external.Level;
import engine.external.component.Component;
import engine.external.component.GroupComponent;
import engine.external.component.HeightComponent;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import engine.external.component.WidthComponent;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.component.ZPositionComponent;
import engine.external.events.Event;
import runner.external.Game;
import runner.external.GameCenterData;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public class GameTranslator {

    private Game myGameBasis;
    private GameCenterData myOldGameData;
    private GameCenterData myNewGameData;
    private ObjectManager myObjectManager;

    public GameTranslator(Game gameBasis, GameCenterData gameData, ObjectManager objectManager) {
        myGameBasis = gameBasis;
        myOldGameData = gameData;
        myNewGameData = new GameCenterData();
        myObjectManager = objectManager;
    }

    public Game translate() {
        Game translatedGame = new Game(); // Game info!!!

        myNewGameData.setTitle(myOldGameData.getTitle());
        myNewGameData.setDescription(myOldGameData.getDescription());
        myNewGameData.setImageLocation(myOldGameData.getImageLocation());
        myNewGameData.setFolderName(myOldGameData.getFolderName());

        for (AuthoringLevel authLevel : myObjectManager.getLevels()) {
            translatedGame.addLevel(translateLevel(authLevel));
        }

        //TODO width, height

        return translatedGame;
    }

    private Level translateLevel(AuthoringLevel authLevel) {
        Level newLevel = new Level();

        newLevel.setBackground(authLevel.getPropertyMap().get(LevelField.BACKGROUND)); // Level properties!!!
        newLevel.setLabel(authLevel.getPropertyMap().get(LevelField.LABEL));
        newLevel.setMusic(authLevel.getPropertyMap().get(LevelField.MUSIC));
        newLevel.setWidth(Double.parseDouble(authLevel.getPropertyMap().get(LevelField.WIDTH)));
        newLevel.setHeight(Double.parseDouble(authLevel.getPropertyMap().get(LevelField.HEIGHT)));

        for (AuthoringEntity authEntity : authLevel.getEntities()) {
            newLevel.addEntity(translateEntity(authEntity));
            for (Event event : myObjectManager.getEvents(authEntity.getPropertyMap().get(EntityField.LABEL))) // Events!!!
                newLevel.addEvent(event);
        }

        return newLevel;
    }

    private Entity translateEntity(AuthoringEntity authEntity) {
        Entity basisEntity = authEntity.getBackingEntity();

        // TODO account for new property types later
        addDoubleComponent(basisEntity, authEntity, XPositionComponent.class, EntityField.X); // Components!!!
        addDoubleComponent(basisEntity, authEntity, YPositionComponent.class, EntityField.Y);
        addDoubleComponent(basisEntity, authEntity, WidthComponent.class, EntityField.XSCALE);
        addDoubleComponent(basisEntity, authEntity, HeightComponent.class, EntityField.YSCALE);
        addStringComponent(basisEntity, authEntity, NameComponent.class, EntityField.LABEL);
        addStringComponent(basisEntity, authEntity, GroupComponent.class, EntityField.GROUP);
        addStringComponent(basisEntity, authEntity, SpriteComponent.class, EntityField.IMAGE);

        basisEntity.addComponent(new ZPositionComponent(0.0)); //TODO deal with this later, this is a stopgap

        return basisEntity;
    }

    private void addDoubleComponent(Entity basisEntity, AuthoringEntity authEntity, Class<? extends Component> componentClass, EntityField field) {
        if (basisEntity.hasComponents(componentClass))
            ((Component) basisEntity.getComponent(componentClass)).setValue(Double.parseDouble(authEntity.getPropertyMap().get(field)));
        else {
            try {
                basisEntity.addComponent(componentClass.getConstructor(Double.class).newInstance(Double.parseDouble(authEntity.getPropertyMap().get(field))));
            } catch (Exception e) {
                System.out.println("Error translating components");
            }
        }
    }

    private void addStringComponent(Entity basisEntity, AuthoringEntity authEntity, Class<? extends Component> componentClass, EntityField field) {
        if (basisEntity.hasComponents(componentClass))
            ((Component) basisEntity.getComponent(componentClass)).setValue(authEntity.getPropertyMap().get(field));
        else {
            try {
                basisEntity.addComponent(componentClass.getConstructor(String.class).newInstance(authEntity.getPropertyMap().get(field)));
            } catch (Exception e) {
                System.out.println("Error translating components");
            }
        }
    }

    public GameCenterData getNewGameData() {
        return myNewGameData;
    }

}
