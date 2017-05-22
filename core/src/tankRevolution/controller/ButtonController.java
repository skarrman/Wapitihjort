package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.services.Constants;
import tankRevolution.framework.Environment;
import tankRevolution.view.GameView;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController {
    private final Environment environment;
    private final GameHolder gameHolder;
    private final GameView gameView;

    private final Stage stage;

    //All the different buttons around the UI, names self-explanatory
    private final Button rightMoveButton;
    private final Button leftMoveButton;
    private final Button pauseMenuButton;
    private final Button toMenuButton;
    private final Button rightWeaponButton;
    private final Button leftWeaponButton;
    /**
     * Keeps track of wether or not the moveButtons are being pressed
     */
    private boolean isPressed;
    /**
     * Changes depending on which moveButton is being pressed
     * value = 1 if right button, -1 if left button, 0 if no button.
     */
    private int direction;
    /**
     * Keeps track of if game is over or not
     */
    private boolean gameOverMode;

    ButtonController(GameView gameView, Environment environment, GameHolder gameHolder) {
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
    public void update() {
        gameView.setPressed(isPressed);
        if (!environment.gameOver()) {
            if (isPressed && environment.tankCanMove()) {
                environment.moveTank(direction);
            } else{
                environment.stopTank();
            }
        } else {
            if (!gameOverMode) {
                setGameOverMode();
                gameOverMode = true;
            }
        }
    }

    /**
     * @return The stage in which all the buttons' inputs are registered
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Sets the size and position of the buttons around the screen. All sizes and positions are based on the size
     * of the screen.
     */
    private void setUpButtonBounds() {
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

    /**
     * Listeners determining what each button will do when pressed.
     */
    private void setUpButtonListeners() {
        leftMoveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                direction = -1;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
                direction = 0;
            }
        });
        rightMoveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                direction = 1;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
                direction = 0;
            }
        });
        pauseMenuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setPauseMenuMode();
                return true;
            }
        });

        leftWeaponButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                environment.setPreviousWeapon();
                return true;
            }
        });

        rightWeaponButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                environment.setNextWeapon();
                return true;
            }
        });
    }

    private void addButtonsToStage() {
        stage.addActor(leftMoveButton);
        stage.addActor(rightMoveButton);
        stage.addActor(pauseMenuButton);
        stage.addActor(leftWeaponButton);
        stage.addActor(rightWeaponButton);
    }

    /**
     * Called when only one tank is remaining, will display the winner and a button to
     * take the user back to the main menu.
     */
    private void setGameOverMode() {
        toMenuButton.addListener(new InputListener() {
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


