package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import tank_revolution.Utils.ButtonObserver;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;


/**
 * Created by JakobErlandsson on 2017-04-24.
 */
public class ButtonController implements ButtonObserver {
    private GameView view;
    private GameSession currentGame;
    private Environment environment;
    private Stage stage;
    private MoveButton rightButton;
    private MoveButton leftButton;

    public ButtonController(GameView view, GameSession currentGame, Environment environment){
        this.view = view;
        this.currentGame = currentGame;
        this.environment = environment;
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

    @Override
    public void actOnPress(InputEvent e) {
        if(e.getTarget().equals(leftButton)){
            environment.moveTank(-1);
            currentGame.moveTank(-1);
        }else if(e.getTarget().equals(rightButton)){
            environment.moveTank(1);
            currentGame.moveTank(1);
        }else{
            System.out.println(e.getTarget().toString());
        }
    }

    @Override
    public void actOnRelease(){
        environment.stopTank();
    }
}


