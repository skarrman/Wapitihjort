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
    Button resumeButton;
    Button restartButton;
    Button toMenuButton;
    Button settingsButton;

    Stage stage;

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

    public Stage getStage() {
        return stage;
    }

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

    private void setUpButtonListener(final GameHolder gameHolder){
        resumeButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Resume Button pressed");
                //gameHolder.setGameMode();
                return true;
            }
        });

        restartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Restart Button pressed");
                return true;
            }
        });

        toMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("To menu Button pressed");
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
