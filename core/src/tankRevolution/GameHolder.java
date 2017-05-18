package tankRevolution;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import tankRevolution.services.AssetsManager;
import tankRevolution.controller.MainController;
import tankRevolution.framework.Environment;
import tankRevolution.model.TankRevolution;
import tankRevolution.view.*;

/**
 * Main controller class for both game sessions and menu screens, updates view, model
 * and active game session based on input from user.
 */

public class GameHolder implements ApplicationListener {
    private Viewable view;
    private Environment environment;
    private MainController mainController;

    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        AssetsManager.getInstance().loadMapNames();
        mainController = new MainController();
        setStartMenuMode();
    }

    /**
     * Called when the {@link Application} should render itself.
     */
    @Override
    public void render() {
        view.update();
        mainController.update();
    }

    /**
     * Shows the main menu where the user can chose between starting a quick game, a custom game, show highscore
     * and show the world for the campaign mode.
     */
    public void setStartMenuMode() {
        AssetsManager.getInstance().loadStartMenuAssets();
        view = new StartMenuView();
        mainController.setStartMenuMode(this);
    }

    /**
     * Shows the in-game UI and connects the relevant model, view and controller.
     */
    public void setGameMode() {
        view = new GameView(environment);
        mainController.setGameMode(environment, view, this);
    }

    /**
     * Sets up classes to start a new game.
     *
     * @param currentGame The current game model.
     */
    public void startNewGame(TankRevolution currentGame, String mapName) {
        AssetsManager.getInstance().loadNewGameAssets(currentGame.getCharacterList().size(), mapName);
        environment = new Environment(currentGame, mapName);
        setGameMode();
    }

    /**
     * Called when the pause menu button is pressed
     */
    public void setPauseMenuMode() {
        AssetsManager.getInstance().getPauseMenuTextures();
        mainController.setPauseMenuMode(this);
        view = new PauseMenuView();
    }

    public void setCustomGameMode() {
        AssetsManager.getInstance().loadCustomGameAssets();
        view = new CustomGameView();
        mainController.setCustomGameMode(this, (CustomGameView) view);
    }

    /**
     * Called when the {@link Application} is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to {@link #create()}.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Called when the {@link Application} is paused, usually when it's not active or visible on screen. An Application is also
     * paused before it is destroyed.
     */
    @Override
    public void pause() {

    }

    /**
     * Called when the {@link Application} is resumed from a paused state, usually when it regains focus.
     */
    @Override
    public void resume() {

    }

    /**
     * Called when the {@link Application} is destroyed. Preceded by a call to {@link #pause()}.
     */
    @Override
    public void dispose() {
        view.dispose();
    }
}
