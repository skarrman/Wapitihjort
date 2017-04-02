package tank_revolution.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import tank_revolution.model.GameModel;
import tank_revolution.model.GameSession;
import tank_revolution.view.GameView;

public class TankRevolutionController implements ApplicationListener, InputProcessor {
	private GameModel model;
	private GameView view;
	private GameSession currentGame;
	private float touchX;
	private float touchY;

	@Override
	public void create() {
		model = new GameModel();
		currentGame = model.newGame();
		view = new GameView(currentGame);

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
		currentGame.shoot(screenX-touchX,screenY-touchY);
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