package engine.internal.systems;

import data.external.DataManager;
import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.AudioComponent;
import engine.external.component.Component;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import voogasalad.util.reflection.ReflectionException;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * @Author Hsingchih Tang
 * Reads the sound file path from SoundComponent of Entities, retrieves corresponding InputStream,
 * and converts them into Media to be stored in AudioComponents
 */
public class AudioSystem extends VoogaSystem {

    DataManager myDataManager;
    HashMap<Entity, String> myEntityPastSound;
    HashMap<String, Media> myMedia;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     *
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine             the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public AudioSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
        myDataManager = new DataManager();
        myEntityPastSound = new HashMap<>();
        myMedia = new HashMap<>();
    }


    protected void run() throws ReflectionException {
        for (Entity entity : this.getEntities()) {
            if (!entity.hasComponents(AUDIO_COMPONENT_CLASS)) {
                generateAudio(entity);
            }
        }
    }


    private void generateAudio(Entity entity) {
        String audioName = (String) getComponentValue(SOUND_COMPONENT_CLASS, entity);
        if (!myEntityPastSound.containsKey(entity) || !myEntityPastSound.get(entity).equals(audioName)) {
//            System.out.println("generating Audio for "+audioName);
            myEntityPastSound.put(entity, audioName);
            retrieveAudio(audioName);
            if (myMedia.get(audioName) != null) {
                entity.addComponent(new AudioComponent(myMedia.get(audioName)));
            }
        }
    }


    private void retrieveAudio(String audioName) {
        if (!myMedia.containsKey(audioName)) {
            InputStream audioStream = myDataManager.loadSound(audioName);
            if (audioStream == null) {
                System.out.println("Audio file " + audioName + " not found in database");
                myMedia.put(audioName, null);
                return;
            }
            Media audio;
            try {
                audio = new Media(convertInputStreamToFile(audioStream).toURI().toString());
            } catch (IOException e) {
                System.out.println("AudioSystem couldn't create audio file for " + audioName);
                myMedia.put(audioName, null);
                return;
            }
            myMedia.put(audioName, audio);
        }
    }

    private File convertInputStreamToFile(InputStream inputStream) throws IOException {
        try {
            File audioFile = File.createTempFile("myAudio", ".mp3");
            FileOutputStream outputStream = new FileOutputStream(audioFile);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return audioFile;
        } catch (IOException e) {
            throw e;
        }
    }

}
