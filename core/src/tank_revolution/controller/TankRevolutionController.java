package tank_revolution.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import tank_revolution.model.Character;
import tank_revolution.model.GameModel;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

import java.util.List;

/**
 * Main controller class for both game sessions and menu screens, updates view, model
 * and active game session based on input from user.
 */

public class TankRevolutionController implements ApplicationListener, InputProcessor {
    private GameModel model;
    private GameView view;
    private GameSession currentGame;
    /**
     * x-coordinate of the users initial input.
     */
    private float touchX;
    /**
     * y-coordinate of the users initial input.
     */
    private float touchY;

    @Override
    public void create() {
        model = new GameModel();
        currentGame = model.newGame();
        view = new GameView(currentGame);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        currentGame.update();
        view.update();
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
            view.removeVector();
            currentGame.shoot(touchX - screenX, screenY - touchY);
        } else {
            // write code to be able to press buttons around the GUI
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!currentGame.isProjectileFlying()) {
            if (Math.sqrt((screenX - touchX) * (screenX - touchX) + (screenY - touchY) * (screenY - touchY)) > 6) {
                view.createArrow(touchX, touchY, screenX, screenY);
                return true;
            }

        }return false;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        currentGame.dispose();
        view.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            //List<Character> characters =  currentGame.getCharacterList();
            //for (Character character : currentGame.getCharacterList()) {
            for (int i = 0; i < currentGame.getCharacterList().size(); i++) {
                Body body = currentGame.getCharacterList().get(i).getTank().getBody();
                if (i == 0) {
                    body.getPosition().set(5f, 7f);
                } else if (i == 1) {
                    body.getPosition().set(currentGame.getMapWidth() - 5f, 7f);
                }
                body.setLinearVelocity(0, 0);
                body.setAngularVelocity(0f);
            }
            currentGame.setProjectileHasHit(true);
            return true;
        } else
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

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}