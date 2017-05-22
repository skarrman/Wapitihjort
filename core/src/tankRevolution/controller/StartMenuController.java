package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.model.Options;
import tankRevolution.services.Constants;


/**
 * Controller class for the menu that shows up when the game is first started.
 */
class StartMenuController {

    /** The button to start a quick game */
    private final Button quickStartButton;

    /** The button to go into world mode. The game mode not yet implemented */
    private final Button worldButton;

    /** The game to go to the custom game screen */
    private final Button customStartButton;

    /** The button to go to the high score screen. Not yet implemented though.*/
    private final Button highScoreButton;

    /** The button to go to settings. Not yet implemented. */
    private final Button settingsButton;

    /** The stage that the buttons are in. */
    private final Stage stage;

    /**
     * Initializing
     * @param gameHolder The current game holder. Necessary to be able to switch screens.
     */
    StartMenuController(final GameHolder gameHolder) {
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

    /**
     * @return the stage in which all the buttons's inputs is registered
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Sets up the bounds (dimensions and position) of the buttons.
     */
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

    /**
     * Adds listeners to the buttons.
     * @param gameHolder The current game holder. So that screens can be switched.
     */
    private void setUpButtonListener(final GameHolder gameHolder){
        quickStartButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Options options = new Options();
                options.setupQuick();
                gameHolder.startNewGame(options);
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
                gameHolder.setCustomGameMode();
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

    /**
     * Adds the buttons to the stage.
     */
    private void addButtonsToStage(){
        stage.addActor(quickStartButton);
        stage.addActor(worldButton);
        stage.addActor(customStartButton);
        stage.addActor(highScoreButton);
        stage.addActor(settingsButton);
    }
}
