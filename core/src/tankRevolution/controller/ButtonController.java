package tankRevolution.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import tankRevolution.GameHolder;
import tankRevolution.utils.Constants;
import tankRevolution.framework.Environment;
import tankRevolution.model.TankRevolution;
import tankRevolution.view.Viewable;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController {
    private Viewable view;
    private TankRevolution currentGame;
    private Environment environment;
    private GameHolder gameHolder;
    private Stage stage;
    private Button rightMoveButton;
    private Button leftMoveButton;
    private Button pauseMenuButton;
    private boolean isPressed;
    private int direction;

    public ButtonController(Viewable view, TankRevolution currentGame, Environment environment, GameHolder gameHolder){
        this.gameHolder = gameHolder;
        this.view = view;
        this.currentGame = currentGame;
        this.environment = environment;
        isPressed = false;
        stage = new Stage();
        leftMoveButton = new Button();
        rightMoveButton = new Button();
        pauseMenuButton = new Button();
        setUpButtonBounds();
        setUpButtonListeners();
        addButtonsToStage();
    }

    public void update(){
        if(isPressed){
            environment.moveTank(direction);
        }else{
            environment.stopTank();
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
    }
}


