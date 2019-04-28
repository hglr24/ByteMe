package runner.internal.runnerSystems;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.PizzaComponent;
import javafx.stage.Stage;
import runner.internal.LevelRunner;
import runner.internal.browser_src.browser.BrowserMain;

import java.util.Collection;

public class PizzaSystem extends RunnerSystem {

    public PizzaSystem(Collection<Class<? extends Component>> requiredComponents, LevelRunner lr) {
        super(requiredComponents, lr);
    }
    @Override
    public void run() {
        for (Entity e: this.getEntities()) {
            if (e.hasComponents(PizzaComponent.class)) {
                orderPizza();
                e.removeComponent(PizzaComponent.class);
            }
        }
    }

    private void orderPizza() {
        getLevelRunner().pauseGame();
        BrowserMain bm = new BrowserMain();
        bm.start(new Stage());
    }
}
