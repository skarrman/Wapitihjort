package tank_revolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tank_revolution.GameHolder;
import tank_revolution.Utils.Constants;


/**
 * Created by simonkarrman on 2017-05-04.
 */
public class MenuController {
    Button newGameButton;
    Stage stage;

    public MenuController(final GameHolder gameHolder) {
        newGameButton = new Button();
        stage = new Stage();
        Vector2 pos = Constants.getNewGameButtonPosition();
        newGameButton.setBounds(pos.x, pos.y, Constants.getNewGameButtonDimention(), Constants.getNewGameButtonDimention());
        newGameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchDown(event, x, y, pointer, button);
                gameHolder.setGameMode();
                return true;
            }
        });
        stage.addActor(newGameButton);
    }

    public Stage getStage() {
        return stage;
    }
}
