package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import tank_revolution.GameHolder;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;
import tank_revolution.view.Viewable;

/**
 * Main controller class, creates the different controllers and assigns their purpose.
 */
public class MainController {

    InputMultiplexer inputMultiplexer;

    public MainController() {
       inputMultiplexer = new InputMultiplexer();
    }

    public void setGameMode(GameSession currentGame, Environment environment, Viewable gameView){
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(new ButtonController(gameView, currentGame, environment).getStage());
        inputMultiplexer.addProcessor(new AimController((GameView) gameView, currentGame, environment));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    public void setMenuMode(GameHolder gameHolder){
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(new StartMenuController(gameHolder).getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
