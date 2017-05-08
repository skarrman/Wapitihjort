package tankRevolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import tankRevolution.GameHolder;
import tankRevolution.framework.Environment;
import tankRevolution.model.TankRevolution;
import tankRevolution.view.GameView;
import tankRevolution.view.Viewable;

/**
 * Main controller class, creates the different controllers and assigns their purpose.
 */
public class MainController {

    InputMultiplexer inputMultiplexer;
    ButtonController buttonController;

    public MainController() {
       inputMultiplexer = new InputMultiplexer();
    }

    public void setGameMode(TankRevolution currentGame, Environment environment, Viewable gameView, GameHolder gameHolder){
        buttonController = new ButtonController(gameView, currentGame, environment, gameHolder);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(buttonController.getStage());
        inputMultiplexer.addProcessor(new AimController((GameView) gameView, currentGame, environment));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    public void setStartMenuMode(GameHolder gameHolder){
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(new StartMenuController(gameHolder).getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void setPauseMenuMode(GameHolder gameHolder) {
        Gdx.input.setInputProcessor(new PauseMenuController(gameHolder).getStage());
    }
    
    public void update(){
        if(buttonController != null) {
            buttonController.update();
        }
    }
}
