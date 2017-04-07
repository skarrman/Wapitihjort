package tank_revolution.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.model.TankRevolution;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

/**
 * Main controller class for both game sessions and menu screens, updates view, model
 * and active game session based on input from user.
 */

public class TankRevolutionController implements ApplicationListener, InputProcessor {
    private TankRevolution model;
    private GameView view;
    private GameSession currentGame;
    private MoveButton leftButton;
    private MoveButton rightButton;
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
        model = new TankRevolution();
        currentGame = model.newGame();
        view = new GameView(currentGame);
        leftButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));
        rightButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));
        leftButton.setBounds(0, 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        rightButton.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10), 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        currentGame.update();
        view.update();
        view.placeButtons(leftButton, rightButton);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(leftButton.isPressed()){
            currentGame.moveTank(-1);
        }else if(rightButton.isPressed()){
            currentGame.moveTank(1);
        }else {
            touchX = screenX;
            touchY = screenY;
        }return true;
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

        }
        return false;
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
        currentGame.getEnvironment().dispose();
        view.dispose();
    }

    private void newGame() {
        dispose();
        currentGame = model.newGame();
        view = new GameView(currentGame);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            newGame();
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