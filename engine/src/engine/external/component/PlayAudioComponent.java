package engine.external.component;

/**
 * @author Hsingchih Tang
 * @author Lucas Liu
 * Stores a boolean variable indicating whether the audio file carried by
 * the same Entity via AudioComponent should be played
 * <p>
 * An example of the component class. Very short/concise. Provides a default for error handling
 * instantiaion.
 */
public class PlayAudioComponent extends Component<Boolean> {
    private final static boolean DEFAULT = false;

    public PlayAudioComponent(Boolean play) {
        super(play);
    }

    public PlayAudioComponent() {
        super(DEFAULT);
    }
}
