package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.sun.tools.doclint.Env;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

/**
 * Main controller class, creates the different controllers and assigns their purpose.
 */
public class MainController {

    GameSession currentGame;
    Environment environment;
    GameView gameView;
    ButtonController buttonController;
    AimController aimController;

    public MainController(GameSession currentGame, Environment environment, GameView gameView) {
        this.currentGame = currentGame;
        this.environment = environment;
        this.gameView = gameView;
        buttonController =  new ButtonController(gameView, currentGame, environment);
        aimController =  new AimController(gameView, currentGame, environment);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(buttonController.getStage());
        inputMultiplexer.addProcessor(aimController);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }
}
