package ui.windows;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.Propertable;

import javax.sound.sampled.AudioInputStream;
import java.io.File;

public class AudioManager extends AssetManager {

    private static final String EXTENSION_KEY = "AcceptableAudioExtensions";
    private static final String TITLE_KEY = "AudioTitle";
    private static final String ASSET_AUDIO_FOLDER_PATH = GENERAL_RESOURCES.getString("audio_filepath");
    private ListView myListView;
    private Propertable myPropertable;

    public AudioManager(){
        super(ASSET_AUDIO_FOLDER_PATH, TITLE_KEY, EXTENSION_KEY);
    }

    public AudioManager(Propertable propertable){
        this();
        myPropertable = propertable;
    }

    /**
     * Method that adds a file to the manager
     * @param file File to be added
     */
    @Override
    protected void addAsset(File file) {
        Label text = new Label(file.getName());
        VBox vBox = new VBox(text);
        vBox.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    Media sound = new Media(file.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            }
        });
        myListView.getItems().add(vBox);
    }

    /**
     * Method that should create and format a pane that
     * will be the content of the Manger's scrollpane
     * @return Pane of the desired content
     */
    @Override
    protected ListView createAndFormatScrollPaneContent() {
        myListView = new ListView();
        return myListView;
    }


    /**
     * This needs to be here for reflection purposes. In creating the buttons the
     * method must exist in the subclass
     */
    @Override
    protected void handleBrowse(){
        super.handleBrowse();
    }

    /**
     * Makes sure the selected asset is null so
     * other classes cannot access the selected
     * because the user closed and didn't apply it
     */
    @Override
    protected void handleClose(){
        super.handleClose();
    }

    /**
     * Sets the appropriate variables based on the selected Asset
     * before closing the window
     */
    @Override
    protected void handleApply() {
        if(!mySelectedAssetName.equals("")){
            this.close();
        }
        else{
            handleNoAssetSelected();
        }
    }
}
