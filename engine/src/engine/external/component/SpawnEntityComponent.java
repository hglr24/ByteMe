package engine.external.component;

import engine.external.Entity;

public class SpawnEntityComponent extends Component<Entity> {

    public SpawnEntityComponent(){
        super(new Entity());
    }
    public SpawnEntityComponent(Entity entity) {
        super(entity);
    }

}
