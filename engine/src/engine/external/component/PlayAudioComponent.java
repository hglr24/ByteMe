package engine.external.component;

/**
 * @Author Hsingchih Tang
 * Stores a boolean variable indicating whether the audio file carried by
 * the same Entity via AudioComponent should be played
 */
public class PlayAudioComponent extends Component<Boolean> {
    public PlayAudioComponent(Boolean play){
        super(play);
    }
}
