package tankRevolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import tankRevolution.GameHolder;
import tankRevolution.framework.Environment;
import tankRevolution.view.CustomGameView;
import tankRevolution.view.GameView;
import tankRevolution.view.Viewable;

/**
 * Main controller class, creates the different controllers and assigns their purpose.
 */
public class MainController {

    /**
     * The current button controller. Necessary to be able to check id player
     * is holding on a button on the in game screen.
     */
    private ButtonController buttonController;

    /**
     * Called when a new game is started, game gets input from dragging across the screen
     * and pressing buttons around the UI.
     * @param environment   Current Environment
     * @param gameView      Current View
     * @param gameHolder    The GameHolder is needed to be able to pause the game.
     */
    public void setGameMode(Environment environment, Viewable gameView, GameHolder gameHolder){
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        buttonController = new ButtonController((GameView)gameView, environment, gameHolder);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(buttonController.getStage());
        inputMultiplexer.addProcessor(new AimController((GameView) gameView, environment));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Called when the game is launched, game gets input from pressing buttons
     * around the screen.
     * @param gameHolder The GameHolder is needed to start another mode.
     */
    public void setStartMenuMode(GameHolder gameHolder){
        Gdx.input.setInputProcessor(new StartMenuController(gameHolder).getStage());
    }

    /**
     * Can be called from within the game bringing up a pause menu.
     * @param gameHolder The GameHolder is needed to respond to what the user is pressing.
     */
    public void setPauseMenuMode(GameHolder gameHolder) {
        Gdx.input.setInputProcessor(new PauseMenuController(gameHolder).getStage());
    }

    public void setCustomGameMode(GameHolder gameHolder, CustomGameView view){
        Gdx.input.setInputProcessor(new CustomGameController(gameHolder, view).getStage());
    }

    /**
     * Chaining the render method in GameHolder to the update method in ButtonController
     * Called 60 times/seconds
     */
    public void update(){
        if(buttonController != null) {
            buttonController.update();
        }
    }
}
