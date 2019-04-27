package engine.external.component;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for custom variables in the game. This component holds a map
 *
 * @author Lucas Liu 
 */
public class VariablesComponent extends Component<Map<String, Double>> {

    public VariablesComponent(){
            super(new HashMap<>());
    }

    public VariablesComponent(Map<String, Double> map){
        super(map);
    }

}
