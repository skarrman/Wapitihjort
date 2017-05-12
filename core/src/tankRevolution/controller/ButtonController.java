package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.utils.Constants;
import tankRevolution.framework.Environment;
import tankRevolution.view.GameView;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController {
    private Environment environment;
    private GameHolder gameHolder;
    private GameView gameView;
    private Stage stage;
    private Button rightMoveButton;
    private Button leftMoveButton;
    private Button pauseMenuButton;
    private Button toMenuButton;
    private Button rightWeaponButton;
    private Button leftWeaponButton;
    private boolean isPressed;
    private int direction;
    private boolean gameOverMode;

    public ButtonController(GameView gameView, Environment environment, GameHolder gameHolder){
        this.gameHolder = gameHolder;
        this.environment = environment;
        this.gameView = gameView;
        gameOverMode = false;
        isPressed = false;
        stage = new Stage();
        leftMoveButton = new Button();
        rightMoveButton = new Button();
        pauseMenuButton = new Button();
        toMenuButton = new Button();
        leftWeaponButton = new Button();
        rightWeaponButton = new Button();
        setUpButtonBounds();
        setUpButtonListeners();
        addButtonsToStage();
    }

    /**
     * Keeps track of when buttons are being held down and when they're released.
     * Called 60 times/second from GameHolder.
     */
    public void update(){
        gameView.setPressed(isPressed);
        if(!environment.gameOver()) {
            if (isPressed && environment.tankCanMove()) {
                environment.moveTank(direction);
                //System.out.println(environment.getCurrentTank().getFuel());
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

        float sideLength = Constants.getWeaponArrowDimension();

        Vector2 rightPos = Constants.getRightWeaponPosition();
        rightWeaponButton.setBounds(rightPos.x, rightPos.y, sideLength, sideLength);

        Vector2 leftPos = Constants.getLeftWeaponPosition();
        leftWeaponButton.setBounds(leftPos.x, leftPos.y, sideLength, sideLength);
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

        leftWeaponButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                environment.setPreviousWeapon();
                return true;
            }
        });

        rightWeaponButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                environment.setNextWeapon();
                return true;
            }
        });
    }

    private void addButtonsToStage(){
        stage.addActor(leftMoveButton);
        stage.addActor(rightMoveButton);
        stage.addActor(pauseMenuButton);
        stage.addActor(leftWeaponButton);
        stage.addActor(rightWeaponButton);
    }

    private void setGameOverMode(){
        toMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setStartMenuMode();
                return true;
            }
        });
        stage.addActor(toMenuButton);
        leftMoveButton.remove();
        rightMoveButton.remove();
        pauseMenuButton.remove();
        leftWeaponButton.remove();
        rightWeaponButton.remove();
    }
}


