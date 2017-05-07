package tank_revolution.controller;

import com.badlogic.gdx.InputProcessor;
import tank_revolution.Exception.CantShootException;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

/**
 * Controller class responsible for handling input from when the user drags it's finger across the screen
 * to aim their shot.
 */
public class AimController implements InputProcessor {
    // x and y-coordinates for the initial press.
    private float touchX;
    private float touchY;
    private boolean touchAllowed;

    private GameView gameView;
    private Environment environment;

    public AimController(GameView gameView, GameSession currentGame, Environment environment) {
        touchX = 0;
        touchY = 0;
        this.gameView = gameView;
        this.environment = environment;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchX = screenX;
        touchY = screenY;
        touchAllowed = environment.isInputAllowed();
        return true;
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
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (touchAllowed) {
            if (Math.sqrt((screenX - touchX) * (screenX - touchX) + (screenY - touchY) * (screenY - touchY)) > 6) {
                gameView.createArrow(touchX, touchY, screenX, screenY);
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
