package center.external;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui_components.CenterView;

public class CenterMain extends Application {
    private static final double STAGE_WIDTH = 1250;
    private static final double STAGE_HEIGHT = 750;
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);

    public static void main(String[] args){
        launch(args);
    }

    public void start (Stage myStage) throws Exception {
        CenterView view = new CenterView();
        myStage.setScene(view.getScene());
        myStage.show();
    }
}
