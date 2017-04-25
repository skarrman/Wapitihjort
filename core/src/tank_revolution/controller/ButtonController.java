package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import tank_revolution.Utils.ButtonObserver;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;


/**
 * Created by JakobErlandsson on 2017-04-24.
 */
public class ButtonController implements ButtonObserver {
    private GameView view;
    private GameSession currentGame;
    private Stage stage;
    private MoveButton rightButton;
    private MoveButton leftButton;

    public ButtonController(GameView view, GameSession currentGame){
        this.view = view;
        this.currentGame = currentGame;
        stage = new Stage();
        leftButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")),this);
        rightButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")), this);
        leftButton.setBounds(0, 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        rightButton.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10), 0,
                Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        stage.addActor(leftButton);
        stage.addActor(rightButton);
    }

    public void placeButtons(){
        view.placeButtons(leftButton, rightButton, stage);
    }

    public Stage getStage(){
        return stage;
    }


    public void rightMoveButtonListener(){
        currentGame.moveTank(1);
    }

    public void leftMoveButtonListener(){
         currentGame.moveTank(-1);
    }

    public void leftChangeWeaponButtonListener(){

    }

    public void rightChangeWeaponButtonListener(){

    }

    public void openMenuButtonListener(){

    }

    @Override
    public void actOnPress(InputEvent e) {
        if(e.getTarget().equals(leftButton)){
            currentGame.moveTank(-1);
        }else if(e.getTarget().equals(rightButton)){
            currentGame.moveTank(1);
        }else{
            System.out.println(e.getTarget().toString());
        }
    }
}


