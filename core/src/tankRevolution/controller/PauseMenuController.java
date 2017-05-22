package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.services.Constants;

/**
 * The controller to handle user input on the pause menu screen.
 */
class PauseMenuController {

    /** The resume game button */
    private final Button resumeButton;

    /** The restart game Button */
    private final Button restartButton;

    /** The main menu button */
    private final Button toMenuButton;

    /** The settings button. No functionality yet though */
    private final Button settingsButton;

    /** The stage that the buttons are in */
    private Stage stage;

    /**
     * Initializing.
     * @param gameHolder The current game holder. Necessary to be able to switch screens.
     */
    PauseMenuController(GameHolder gameHolder){
        resumeButton = new Button();
        restartButton = new Button();
        toMenuButton = new Button();
        settingsButton = new Button();

        stage = new Stage();

        setUpButtonBounds();
        setUpButtonListener(gameHolder);
        addButtonsToStage();
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
    private void setUpButtonBounds(){
        float buttonWidth = Constants.getPauseMenuButtonWidth();
        float buttonHeight = Constants.getPauseMenuButtonHeight();

        Vector2 resumePos = Constants.getResumeButtonPosition();
        resumeButton.setBounds(resumePos.x, resumePos.y, buttonWidth, buttonHeight);

        Vector2 restartPos = Constants.getRestartButtonPosition();
        restartButton.setBounds(restartPos.x, restartPos.y, buttonWidth, buttonHeight);

        Vector2 toMenuPos = Constants.getToMenuPosition();
        toMenuButton.setBounds(toMenuPos.x, toMenuPos.y, buttonWidth, buttonHeight);

        float settingsLength = Constants.getSettingsButtonDimension();
        Vector2 settingsPos = Constants.getSettingsButtonPosition();
        settingsButton.setBounds(settingsPos.x, settingsPos.y, settingsLength, settingsLength);

    }

    /**
     * Listeners determining what each button will do when pressed.
     */
    private void setUpButtonListener(final GameHolder gameHolder){
        resumeButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setGameMode();
                return true;
            }
        });

        restartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.restartGame();
                return true;
            }
        });

        toMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setStartMenuMode();
                return true;
            }
        });

        settingsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Settings Button pressed");
                return true;
            }
        });
    }

    /**
     * Adding the buttons to the stage.
     */
    private void addButtonsToStage(){
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(toMenuButton);
        stage.addActor(settingsButton);
    }
}
