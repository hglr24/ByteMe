package runner.internal;

import engine.external.Entity;
import engine.external.component.AudioComponent;
import engine.external.component.PlayAudioComponent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Plays game audio
 * @author Feroze
 */
public class AudioManager {
    ExecutorService soundPool;

    ArrayList<MediaPlayer> myMediaPlayers;

    /**
     * Constructor to create a simple thread pool.
     *
     * @param numberOfThreads - number of threads to use media players in the map.
     */
    public AudioManager(int numberOfThreads) {
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
        myMediaPlayers = new ArrayList<>();
    }

    /**
     * Find the entity's sound and play it.
     *
     */
    public void playSound(Entity entity) {
        entity.removeComponent(PlayAudioComponent.class);
        MediaPlayer mp = new MediaPlayer((Media) entity.getComponent(AudioComponent.class).getValue());
        myMediaPlayers.add(mp);
        threadPlaySound(mp);
    }

    /**
     * Pause all MediaPlayers currently playing
     */
    public void pauseAllSound(){
        int i = 0;
        for(MediaPlayer mp: myMediaPlayers){
            if(mp.getStatus()== MediaPlayer.Status.PLAYING){
                mp.pause();
            }
            i++;
        }
    }

    /**
     * Resume all MediaPlayers that have been paused
     */
    public void resumeAllSound(){
        for(MediaPlayer mp: myMediaPlayers){
            if(mp.getStatus()== MediaPlayer.Status.PAUSED){
                threadPlaySound(mp);
            }
        }
    }

    /**
     * Stop all MediaPlayers, whether they are playing or not
     */
    public void stopAllSound(){
        for(MediaPlayer mp: myMediaPlayers){
            mp.stop();
        }
    }

    /**
     * Assigns a MediaPlayer to one thread to play the sound
     * @param mp the MediaPlayer to play
     */
    private void threadPlaySound(MediaPlayer mp){
        Runnable soundPlay = () -> {
            mp.play();
        };
        soundPool.execute(soundPlay);
    }

    /**
     * Stop all threads and media players.
     */
    public void shutdown() {
        stopAllSound();
        soundPool.shutdown();
    }

}
