package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * View showing the graphical representation of the pause menu.
 * {@inheritDoc}
 */
public class PauseMenuView implements Viewable {

    /** The batch to draw all the graphical components on */
    private final Batch batch;

    /** The sprite that represents the "Resume Game"-button */
    private final Sprite resumeSprite;

    /** The sprite that represents the "Restart Game"-button */
    private final Sprite restartSprite;

    /** The sprite that represents the "Main menu"-button */
    private final Sprite toMenuSprite;

    /** The sprite that represents the "Settings"(Gear)-button */
    private final Sprite settingsSprite;

    public PauseMenuView(){
        batch = new SpriteBatch();

        List<Texture> textures = AssetsManager.getInstance().getPauseMenuTextures();
        resumeSprite = new Sprite(textures.get(0));
        restartSprite = new Sprite(textures.get(1));
        toMenuSprite = new Sprite(textures.get(2));
        settingsSprite = new Sprite(textures.get(3));

        setUpSpriteSize();
        setUpSpritePosition();
    }
    @Override
    public void update() {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        resumeSprite.draw(batch);
        restartSprite.draw(batch);
        toMenuSprite.draw(batch);
        settingsSprite.draw(batch);
        batch.end();
    }

    /**
     * Setting up all de sprites sizes.
     */
    private void setUpSpriteSize(){
        float buttonWidth = Constants.getPauseMenuButtonWidth();
        float buttonHeight = Constants.getPauseMenuButtonHeight();

        resumeSprite.setSize(buttonWidth, buttonHeight);
        restartSprite.setSize(buttonWidth, buttonHeight);
        toMenuSprite.setSize(buttonWidth, buttonHeight);

        float settingsLength = Constants.getSettingsButtonDimension();
        settingsSprite.setSize(settingsLength, settingsLength);
    }

    /**
     * Setting the position on all sprites.
     */
    private void setUpSpritePosition(){
        Vector2 resumePos = Constants.getResumeButtonPosition();
        resumeSprite.setPosition(resumePos.x, resumePos.y);

        Vector2 restartPos = Constants.getRestartButtonPosition();
        restartSprite.setPosition(restartPos.x, restartPos.y);

        Vector2 toMenuPos = Constants.getToMenuPosition();
        toMenuSprite.setPosition(toMenuPos.x, toMenuPos.y);

        Vector2 settingsPos = Constants.getSettingsButtonPosition();
        settingsSprite.setPosition(settingsPos.x, settingsPos.y);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
