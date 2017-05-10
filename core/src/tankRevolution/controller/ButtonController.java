package tankRevolution.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.utils.Constants;
import tankRevolution.framework.Environment;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController {
    private Environment environment;
    private GameHolder gameHolder;
    private Stage stage;
    private Button rightMoveButton;
    private Button leftMoveButton;
    private Button pauseMenuButton;
    private Button toMenuButton;
    private boolean isPressed;
    private int direction;
    private boolean gameOverMode;

    public ButtonController(Environment environment, GameHolder gameHolder){
        this.gameHolder = gameHolder;
        this.environment = environment;
        gameOverMode = false;
        isPressed = false;
        stage = new Stage();
        leftMoveButton = new Button();
        rightMoveButton = new Button();
        pauseMenuButton = new Button();
        toMenuButton = new Button();
        setUpButtonBounds();
        setUpButtonListeners();
        addButtonsToStage();
    }

    /**
     * Keeps track of when buttons are being held down and when they're released.
     * Called 60 times/second from GameHolder.
     */
    public void update(){
        if(!environment.gameOver()) {
            if (isPressed && environment.tankCanMove()) {
                environment.moveTank(direction);
                System.out.println(environment.getCurrentTank().getFuel());
            } else {
                environment.stopTank();
            }
        }else {
            if (!gameOverMode) {
                setGameOverMode();
                gameOverMode = true;
            }
        }
    }

    public Stage getStage(){
        return stage;
    }


    private void setUpButtonBounds(){
        leftMoveButton.setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        rightMoveButton.setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        pauseMenuButton.setBounds(Constants.getSettingsButtonPosition().x, Constants.getSettingsButtonPosition().y,
                Constants.getSettingsButtonDimension(), Constants.getSettingsButtonDimension());
        toMenuButton.setBounds(Constants.getToMenuPosition().x, Constants.getToMenuPosition().y,
                Constants.getPauseMenuButtonWidth(), Constants.getPauseMenuButtonHeight());
    }

    private void setUpButtonListeners(){
        leftMoveButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                direction = -1;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isPressed = false;
                direction = 0;
            }
        });
        rightMoveButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                direction = 1;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                isPressed = false;
                direction = 0;
            }
        });
        pauseMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setPauseMenuMode();
                return true;
            }
        });
    }

    private void addButtonsToStage(){
        stage.addActor(leftMoveButton);
        stage.addActor(rightMoveButton);
        stage.addActor(pauseMenuButton);
        stage.addActor(toMenuButton);
    }

    private void setGameOverMode(){
        toMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setStartMenuMode();
                return true;
            }
        });
        leftMoveButton.removeListener(leftMoveButton.getClickListener());
        rightMoveButton.removeListener(rightMoveButton.getClickListener());
        pauseMenuButton.removeListener(pauseMenuButton.getClickListener());
    }
}


