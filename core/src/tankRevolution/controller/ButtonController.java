package tankRevolution.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import tankRevolution.utils.ButtonObserver;
import tankRevolution.utils.Constants;
import tankRevolution.framework.Environment;
import tankRevolution.model.TankRevolution;
import tankRevolution.view.Viewable;


/**
 * Controller class responsible for handling input from buttons around the UI
 */
public class ButtonController implements ButtonObserver {
    private Viewable view;
    private TankRevolution currentGame;
    private Environment environment;
    private Stage stage;
    private UIButton rightButton;
    private UIButton leftButton;

    public ButtonController(Viewable view, TankRevolution currentGame, Environment environment){
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


