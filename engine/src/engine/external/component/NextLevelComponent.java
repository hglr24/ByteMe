package engine.external.component;

public class NextLevelComponent extends Component<Double> {
    private final static double DEFAULT = 1.0;
    public NextLevelComponent(){
        super(DEFAULT);
    }
    public NextLevelComponent(Double level){
        super(level);
    }
}
