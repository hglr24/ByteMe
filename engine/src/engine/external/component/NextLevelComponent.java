package engine.external.component;

public class NextLevelComponent extends Component<Double> {
    private final static double DEFAULT = 1.0;
    public NextLevelComponent(Double level){
        super(level);
    }
    public NextLevelComponent(){
        super(DEFAULT);
    }
}
