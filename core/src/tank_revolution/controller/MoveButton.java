package tank_revolution.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import tank_revolution.Utils.ButtonObserver;


/**
 * A UI button with listeners and observers.
 */
public class MoveButton extends ImageButton {


    public MoveButton(Texture image, final ButtonObserver observer) {
        super(new TextureRegionDrawable(new TextureRegion(image)));
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                observer.actOnPress(event);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                observer.actOnRelease();
            }
        });

    }
}


