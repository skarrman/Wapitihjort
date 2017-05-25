package tankRevolution.controller;

import com.badlogic.gdx.InputProcessor;
import tankRevolution.framework.IEnvironment;
import tankRevolution.view.GameView;

/**
 * Controller class responsible for handling input from when the user drags it's finger across the screen
 * to aim their shot.
 */
public class AimController implements InputProcessor {

    /** The x-coordinate for the initial press. */
    private float touchX;

    /** The y-coordinate for the initial press. */
    private float touchY;

    /** This tells if a touch is allowed. Reasons it may not be is if it isn't a the users turn
     * or a projectile is in the air when the initial press is made.
     */
    private boolean touchAllowed;

    /** The current game view. Necessary to be able to draw the aiming arrow. */
    private final GameView gameView;

    /** The current environment. Necessary to be able to shoot when the drag is released. */
    private final IEnvironment environment;

    /**
     * Initializing.
     * @param gameView The current game view.
     * @param environment The current environment.
     */
    AimController(GameView gameView, IEnvironment environment) {
        touchX = 0;
        touchY = 0;
        this.gameView = gameView;
        this.environment = environment;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchAllowed = environment.isInputAllowed();
        if(touchAllowed) {
            touchX = screenX;
            touchY = screenY;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (touchAllowed) {
            if (Math.sqrt(((screenX - touchX) * (screenX - touchX)) + ((screenY - touchY) * (screenY - touchY))) > 6) {
                gameView.createArrow(touchX, touchY, screenX, screenY);
                return true;
            }
        }return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(touchAllowed){
            if (Math.sqrt((screenX - touchX) * (screenX - touchX) + (screenY - touchY) * (screenY - touchY)) > 6) {
                gameView.removeVector();
                environment.shoot(screenX, screenY, touchX, touchY);
                return true;
            }
        }return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
