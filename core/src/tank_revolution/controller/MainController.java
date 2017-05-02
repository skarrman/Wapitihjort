package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

/**
 * Main controller class, creates the different controllers and assigns their purpose.
 */
public class MainController {

    public MainController(GameSession currentGame, Environment environment, GameView gameView) {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new ButtonController(gameView, currentGame, environment).getStage());
        inputMultiplexer.addProcessor(new AimController(gameView, currentGame, environment));
        Gdx.input.setInputProcessor(inputMultiplexer);

    }
}
