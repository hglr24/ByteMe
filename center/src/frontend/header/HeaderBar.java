/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose This code encompasses the general aspects of the header bar so that it can be displayed. For now, this is
 * simply a title, but may eventually contain a settings button.
 * @Dependencies javafx and Utilities.
 * @Uses: Used in CenterMain to display the title
 */

package frontend.header;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import frontend.Utilities;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class HeaderBar {
    private Pane myHeaderLayout;
    private ResourceBundle myLanguageBundle;
    private static final String TITLE_SELECTOR = "titlefont";

    /**
     * @purpose constructor that initializes the resource bundle and sets the layout of the pane.
     */
    public HeaderBar() {
        myLanguageBundle = ResourceBundle.getBundle("languages/English");
        myHeaderLayout = new BorderPane();
        initializeLayouts();
    }

    /**
     * @purpose share the header layout with CenterMain
     * @return the current layout of the pane.
     */
    public Pane getHeaderLayout() {
        return myHeaderLayout;
    }

    private void initializeLayouts() {
        Text title = new Text(Utilities.getValue(myLanguageBundle, "titleText"));
        title.getStyleClass().add(TITLE_SELECTOR);
        StackPane headerLayout = new StackPane();
        BorderPane titleLayout = new BorderPane();
        titleLayout.setCenter(title);
        headerLayout.getChildren().add(titleLayout);
        myHeaderLayout = headerLayout;
    }

}
