package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.utils.Constants;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by simonkarrman on 2017-05-08.
 */
public class PauseMenuView implements Viewable {
    private Batch batch;

    private Sprite resumeSprite;
    private Sprite restartSprite;
    private Sprite toMenuSprite;
    private Sprite settingsSprite;

    public PauseMenuView(){
        batch = new SpriteBatch();

        resumeSprite = new Sprite(new Texture(Gdx.files.internal("ResumeButton.png")));
        restartSprite = new Sprite(new Texture(Gdx.files.internal("RestartButton.png")));
        toMenuSprite = new Sprite(new Texture(Gdx.files.internal("MainMenuButton.png")));
        settingsSprite = new Sprite(new Texture(Gdx.files.internal("Kugghjul.png")));

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

    private void setUpSpriteSize(){
        float buttonWidth = Constants.getPauseMenuButtonWidth();
        float buttonHeight = Constants.getPauseMenuButtonHeight();

        resumeSprite.setSize(buttonWidth, buttonHeight);
        restartSprite.setSize(buttonWidth, buttonHeight);
        toMenuSprite.setSize(buttonWidth, buttonHeight);

        float settingsLength = Constants.getSettingsButtonDimension();
        settingsSprite.setSize(settingsLength, settingsLength);
    }

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
}
