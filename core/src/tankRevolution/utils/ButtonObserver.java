package tankRevolution.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * Observer class that listens to buttons around the UI
 */
public interface ButtonObserver {

    /**
     * Called when a button is pressed
     * @param e The event fired by the press.
     */
    void actOnPress(InputEvent e);

    /**
     * Called when button is no longer pressed.
     */
    void actOnRelease();
}
