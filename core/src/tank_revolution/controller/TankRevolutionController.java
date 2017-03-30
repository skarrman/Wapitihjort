package tank_revolution.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import tank_revolution.model.GameModel;
import tank_revolution.view.GameView;

import java.util.HashMap;
import java.util.Map;

public class TankRevolutionController implements ApplicationListener, InputProcessor {
	private GameModel model;
	private GameView view;
	private float deltaX;
	private float deltaY;
	private float touchX;
	private float touchY;
	private float releaseX;
	private float releaseY;

	@Override
	public void create() {
		//mmodel = new GameModel();
		view = new GameView(model);
	}

	@Override
	public void render() {
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
		releaseX = screenX;
		releaseY = screenY;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		deltaX = Math.abs(releaseX - touchX);
		deltaY = Math.abs(releaseY - touchY);
		return true;
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