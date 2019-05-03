package manager;

import data.external.GameCenterData;

@FunctionalInterface

public interface SwitchToNewGameAuthoring {
    /**
     * In the future, this functional interface will take in a GameCenter object as a parameter in order to load in
     * a particular game for the authoring environment or generate a new game. Currently it helps to switch to the authoring
     * environment by referring to a method in SceneManager
     * @author Anna Darwish
     */
    void switchScene(GameCenterData myGameData);
}
