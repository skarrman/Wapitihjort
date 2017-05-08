package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.GameHolder;
import tank_revolution.Utils.Constants;

/**
 * Created by simonkarrman on 2017-05-08.
 */
public class PauseMenuView implements Viewable {
    Batch batch;

    Sprite resumeSprite;
    Sprite restartSprite;
    Sprite toMenuSprite;
    Sprite settingsSprite;

    public PauseMenuView(){
        batch = new SpriteBatch();

        resumeSprite = new Sprite(new Texture(Gdx.files.internal("PauseMenuDefaultButton.png")));
        restartSprite = new Sprite(new Texture(Gdx.files.internal("PauseMenuDefaultButton.png")));
        toMenuSprite = new Sprite(new Texture(Gdx.files.internal("PauseMenuDefaultButton.png")));
        settingsSprite = new Sprite(new Texture(Gdx.files.internal("Kugghjul.png")));

        setUpSpriteSize();
        setUpSpritePosition();
    }
    @Override
    public void update() {

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
    }
}
