package tank_revolution.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import tank_revolution.model.GameModel;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

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
		if(Math.sqrt((screenX-touchX)*(screenX-touchX) + (screenY-touchY)*(screenY-touchY)) > 3){
			currentGame.shoot(screenX-touchX,screenY-touchY);
		}else{
			// write code to be able to press buttons around the GUI
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		view.dispose();
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

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}