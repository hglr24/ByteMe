package runner.internal;

import engine.external.Entity;
import engine.external.component.AudioComponent;
import engine.external.component.PlayAudioComponent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Plays game audio
 * @author Feroze
 */
public class AudioManager {
    ExecutorService soundPool;

    /**
     * Constructor to create a simple thread pool.
     *
     * @param numberOfThreads - number of threads to use media players in the map.
     */
    public AudioManager(int numberOfThreads) {
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
    }


    /**
     * Find the entity's sound and play it.
     *
     */
    public void playSound(Entity entity) {
        Runnable soundPlay = () -> {
            entity.removeComponent(PlayAudioComponent.class);
            Media me = (Media) entity.getComponent(AudioComponent.class).getValue();
            MediaPlayer mp = new MediaPlayer(me);
            mp.play();

        };
        soundPool.execute(soundPlay);
    }

    /**
     * Stop all threads and media players.
     */
    public void shutdown() {
        soundPool.shutdown();
    }

}
