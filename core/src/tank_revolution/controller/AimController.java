package tank_revolution.controller;

import com.badlogic.gdx.InputProcessor;
import tank_revolution.framework.Environment;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

/**
 * Created by JakobErlandsson on 2017-04-27.
 */
public class AimController implements InputProcessor {

    private float touchX;
    private float touchY;
    private GameView gameView;
    private GameSession currentGame;
    private Environment environment;

    public AimController(GameView gameView, GameSession currentGame, Environment environment) {
        touchX = 0;
        touchY = 0;
        this.gameView = gameView;
        this.currentGame = currentGame;
        this.environment = environment;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchX = screenX;
        touchY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Math.sqrt((screenX - touchX) * (screenX - touchX) + (screenY - touchY) * (screenY - touchY)) > 6) {
            gameView.removeVector();
            environment.shoot();
            currentGame.shoot(touchX - screenX, screenY - touchY);
            return true;
        }return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!currentGame.isProjectileFlying()) {
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
