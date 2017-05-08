package tankRevolution.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.utils.ButtonObserver;


/**
 * A UI button with listeners and observers.
 */
public class UIButton extends Button {


    public UIButton(final ButtonObserver observer) {
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


