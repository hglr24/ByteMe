package ui.panes;

import engine.external.Entity;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.*;
import ui.manager.ObjectManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This is a page that will display all of the types created by the user
 */
public class UserCreatedTypesPane extends VBox {
    private EntityMenu myEntityMenu;
    private static final ResourceBundle myResources = ResourceBundle.getBundle("default_entity_type");
    private static final ResourceBundle myGeneralResources = ResourceBundle.getBundle("authoring_general");
    private ObjectManager myObjectManager;
    private DefaultTypesFactory myDefaultTypesFactory;
    private Entity myDraggedEntity;
    private AuthoringEntity myDraggedAuthoringEntity;


    /**
     * Creates a pane that displayes the user created types
     * @param objectManager
     */
    public UserCreatedTypesPane(ObjectManager objectManager){
        myObjectManager = objectManager;
        String title = myResources.getString("UserCreatedTitle");
        myEntityMenu = new EntityMenu(title);
        myDefaultTypesFactory = new DefaultTypesFactory();
        populateCategories();
        this.getChildren().add(myEntityMenu);
    }

    /**
     * Used by Viewer to get the dragged Entity
     * @return Entity
     */
    public Entity getDraggedEntity(){
        return myDraggedEntity;
    }

    /**
     * Used by Viewer to get the dragged AuthoringEntity
     * @return AuthoringEntity
     */
    public AuthoringEntity getDraggedAuthoringEntity(){
        return  myDraggedAuthoringEntity;
    }
    private void populateCategories() {
        for(String s : myDefaultTypesFactory.getCategories()){
            myEntityMenu.addDropDown(s);
        }
    }

    public void addUserDefinedType(String category, Entity entity,String basedOn){
        String label = (String) entity.getComponent(NameComponent.class).getValue();
        System.out.println("Label: " + label);
        String imageName = (String) entity.getComponent(SpriteComponent.class).getValue();
        try {
            AuthoringEntity originalAuthoringEntity = new AuthoringEntity(entity, myObjectManager);
            originalAuthoringEntity.getPropertyMap().put(EntityField.LABEL, label);
            String assetImagesFilePath = myGeneralResources.getString("images_filepath");
            ImageWithEntity imageWithEntity = new ImageWithEntity(new FileInputStream(assetImagesFilePath + "/" + imageName), originalAuthoringEntity);
            UserDefinedTypeSubPane subPane = new UserDefinedTypeSubPane(imageWithEntity, label, originalAuthoringEntity);
            List<Pane> paneList = new ArrayList<>();
            paneList.add(subPane);
            myEntityMenu.addToDropDown(category, paneList);
            subPane.setOnDragDetected(mouseEvent -> {
                myDraggedEntity = myDefaultTypesFactory.getDefaultEntity(category, basedOn);
                myDraggedEntity.addComponent(new NameComponent(originalAuthoringEntity.getPropertyMap().get(EntityField.LABEL)));
                myDraggedEntity.addComponent(new SpriteComponent(imageName));
                myDraggedAuthoringEntity = originalAuthoringEntity;
                Utility.setupDragAndDropImage(imageWithEntity);
            });
        } catch (FileNotFoundException e) {
            //TODO: deal with this
            e.printStackTrace();
        }
    }
}
