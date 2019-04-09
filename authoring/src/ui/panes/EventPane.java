package ui.panes;
import events.EventFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Utility;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EventPane extends Stage {

    private static final String myResources = "event_parameters";
    private EventFactory myEventFactory = new EventFactory();
    private VBox myEventParameters = new VBox();

    public EventPane(String eventName) {
        ResourceBundle myParameterNames = ResourceBundle.getBundle(myResources);
        String eventKey = eventName.replace(" ","");
        for (String nodeType: (myParameterNames.getString(eventKey)).split(",")) {
            String methodName = nodeType.substring(0,nodeType.indexOf("::"));
            String parameterValues = nodeType.substring(nodeType.indexOf("::") + 1);
            List<String> nodeMethodParameters = Arrays.asList(parameterValues.split("::"));
            ArrayList<String> factoryParameters = new ArrayList<>(nodeMethodParameters);
            try {
                Method m = myEventFactory.getClass().getDeclaredMethod(methodName, factoryParameters.getClass());
                Object[] paramMethod = {factoryParameters};
                Object ret = m.invoke(myEventFactory,paramMethod);
                Node addedOption = (Node)ret;
                myEventParameters.getChildren().add(addedOption);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        myEventParameters.getChildren().add(makeButtons());
        myEventParameters.getStylesheets().add("default.css");
        Scene myScene = new Scene(myEventParameters);
        this.setScene(myScene);
        this.show();
    }

    private Node makeButtons(){
        Button mySaveButton = new Button("Save");
        mySaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //make functional interface to access level
            }
        });
        Button myCancelButton = new Button("Cancel");
        myCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeThisPane();
            }
        });
        List<Button> myButtons = new ArrayList<>();
        myButtons.add(mySaveButton);
        myButtons.add(myCancelButton);
        return Utility.createButtonBar(myButtons);
    }
    private void closeThisPane(){
        this.close();
    }





}
