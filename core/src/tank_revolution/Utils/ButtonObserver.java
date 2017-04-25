package tank_revolution.Utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import tank_revolution.controller.MoveButton;

/**
 * Created by JakobErlandsson on 2017-04-25.
 */
public interface ButtonObserver {
    void actOnPress(InputEvent e);
    void actOnRelease();
}
