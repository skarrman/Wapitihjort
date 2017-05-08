package tankRevolution;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.controller.MainController;
import tankRevolution.framework.Environment;
import tankRevolution.model.TankRevolution;
import tankRevolution.model.Options;
import tankRevolution.view.GameView;
import tankRevolution.view.StartMenuView;
import tankRevolution.view.Viewable;

/**
 * Main controller class for both game sessions and menu screens, updates view, model
 * and active game session based on input from user.
 */

public class GameHolder implements ApplicationListener {
    private Viewable view;
    private TankRevolution currentGame;
    private Environment environment;
    private MainController mainController;
    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        AssetsManager.getInstance().loadStartingAssets(2);
        view = new StartMenuView();
        mainController = new MainController();
        mainController.setMenuMode(this);
    }

    /**
     * Called when the {@link Application} should render itself.
     */
    @Override
    public void render() {
            view.update();
    }

    public void setGameMode(){
        currentGame = new Options().newGame();
        environment = new Environment(Constants.getMapWidth(), currentGame);
        view = new GameView(currentGame, environment);
        mainController.setGameMode(currentGame, environment,view);
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

    }
}
