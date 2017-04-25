package tank_revolution.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import tank_revolution.Utils.ButtonObserver;
import tank_revolution.model.GameSession;


/**
 * Created by JakobErlandsson on 2017-04-05.
 */
public class MoveButton extends ImageButton {

    private ButtonObserver observer;

    public MoveButton(Texture image) {
        super(new TextureRegionDrawable(new TextureRegion(image)));
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                observer.actOnPress(event);
                return true;
            }
        });

    }
}


