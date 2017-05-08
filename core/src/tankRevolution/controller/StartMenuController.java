package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.utils.Constants;


/**
 * Created by simonkarrman on 2017-05-04.
 */
public class StartMenuController {
    private Button quickStartButton;
    private Button worldButton;
    private Button customStartButton;
    private Button highScoreButton;
    private Button settingsButton;
    Stage stage;

    public StartMenuController(final GameHolder gameHolder) {
        quickStartButton = new Button();
        worldButton = new Button();
        customStartButton = new Button();
        highScoreButton = new Button();
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
        float sideLength = Constants.getStartMenuButtonDimension();

        Vector2 quickStartPos = Constants.getQuickStartButtonPosition();
        quickStartButton.setBounds(quickStartPos.x, quickStartPos.y, sideLength, sideLength);

        Vector2 worldPos = Constants.getWorldButtonPosition();
        worldButton.setBounds(worldPos.x, worldPos.y, sideLength, sideLength);

        Vector2 customStartPos = Constants.getCustomStartButtonPosition();
        customStartButton.setBounds(customStartPos.x, customStartPos.y, sideLength, sideLength);

        Vector2 highScorePos = Constants.getHighScoreButtonPosition();
        highScoreButton.setBounds(highScorePos.x, highScorePos.y, sideLength, sideLength);

        Vector2 settingsPos = Constants.getSettingsButtonPosition();
        float settingsLength = Constants.getSettingsButtonDimension();
        settingsButton.setBounds(settingsPos.x, settingsPos.y, settingsLength, settingsLength);
    }

    private void setUpButtonListener(final GameHolder gameHolder){
        quickStartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameHolder.setGameMode();
                return true;
            }
        });

        worldButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("World Button pressed");
                return true;
            }
        });

        customStartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Custom Start Button pressed");
                return true;
            }
        });


        highScoreButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("High Score Button pressed");
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
        stage.addActor(quickStartButton);
        stage.addActor(worldButton);
        stage.addActor(customStartButton);
        stage.addActor(highScoreButton);
        stage.addActor(settingsButton);
    }
}