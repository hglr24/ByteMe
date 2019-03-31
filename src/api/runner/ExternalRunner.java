package api.runner;

public interface ExternalRunner {

    /**
     * Available to center environment in order for user to evaluate the current state of the game they
     * are working on and see if they wish to make further edits, also avialable to game
     * center to run whatever game the user selects
     * @param filePath path to files of the game that has been created to play
     */
    void run(String filePath);
}
