package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.utils.Constants;

/**
 * Created by simonkarrman on 2017-05-08.
 */
public class PauseMenuController {
    private Button resumeButton;
    private Button restartButton;
    private Button toMenuButton;
    private Button settingsButton;

    private Stage stage;

    public PauseMenuController(GameHolder gameHolder){
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
    public Stage getStage() {
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

    private void addButtonsToStage(){
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(toMenuButton);
        stage.addActor(settingsButton);
    }
}
