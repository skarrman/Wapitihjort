package tank_revolution.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;


/**
 * Created by JakobErlandsson on 2017-04-24.
 */
public class ButtonController {
    private GameView view;
    private GameSession currentSession;
    private Stage stage;
    private MoveButton rightButton;
    private MoveButton leftButton;

    public ButtonController(){
        stage = new Stage();
        leftButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));
        rightButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));leftButton.setBounds(0, 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        rightButton.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10), 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        stage.addActor(leftButton);
        stage.addActor(rightButton);
    }


    public void rightMoveButtonListener(){
        currentSession.moveTank(1);
    }

    public void leftMoveButtonListener(){
         currentSession.moveTank(-1);
    }

    public void leftChangeWeaponButtonListener(){

    }

    public void rightChangeWeaponButtonListener(){

    }

    public void openMenuButtonListener(){

    }
}


