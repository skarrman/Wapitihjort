package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import tank_revolution.Utils.ButtonObserver;
import tank_revolution.Utils.Constants;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;
import tank_revolution.view.Viewable;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController implements ButtonObserver {
    private Viewable view;
    private GameSession currentGame;
    private Environment environment;
    private Stage stage;
    private UIButton rightButton;
    private UIButton leftButton;

    public ButtonController(Viewable view, GameSession currentGame, Environment environment){
        this.view = view;
        this.currentGame = currentGame;
        this.environment = environment;
        stage = new Stage();
        leftButton = new UIButton(this);
        rightButton = new UIButton(this);
        leftButton.setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        rightButton.setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        stage.addActor(leftButton);
        stage.addActor(rightButton);
    }

    /**
     * Tells the view to draw the buttons in a specified place.
     */
    public void placeButtons(){
       // view.placeButtons(leftButton, rightButton, stage);
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void actOnPress(InputEvent e) {
        if(currentGame.tankCanMove()) {
            if (e.getTarget().equals(leftButton)) {
                environment.moveTank(-1);
            } else if (e.getTarget().equals(rightButton)) {
                environment.moveTank(1);
            } else {
                System.out.println(e.getTarget().toString());
            }
        }
        currentGame.reduceFuel();
    }

    @Override
    public void actOnRelease(){
        environment.stopTank();
    }
}


