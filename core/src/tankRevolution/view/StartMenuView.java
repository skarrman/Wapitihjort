package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by simonkarrman on 2017-05-04.
 */
public class StartMenuView implements Viewable {
    Batch batch;
    Sprite quickStartSprite;
    Sprite worldSprite;
    Sprite customStartSprite;
    Sprite highScoreSprite;
    Sprite settingsSprite;



    public StartMenuView(){
        batch = new SpriteBatch();

        ArrayList<Texture> textures = AssetsManager.getInstance().getStartMenuTextures();
        quickStartSprite = new Sprite(textures.get(0));
        worldSprite = new Sprite((textures.get(1)));
        customStartSprite = new Sprite(textures.get(2));
        highScoreSprite = new Sprite(textures.get(3));
        settingsSprite = new Sprite(textures.get(4));

        setSpriteSizes();
        setSpritePositions();
    }
    @Override
    public void update() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        quickStartSprite.draw(batch);
        worldSprite.draw(batch);
        customStartSprite.draw(batch);
        highScoreSprite.draw(batch);
        settingsSprite.draw(batch);
        batch.end();
    }

    private void setSpriteSizes(){
        float sideLength = Constants.getStartMenuButtonDimension();
        quickStartSprite.setSize(sideLength, sideLength);
        worldSprite.setSize(sideLength, sideLength);
        customStartSprite.setSize(sideLength, sideLength);
        highScoreSprite.setSize(sideLength, sideLength);

        float settingsLength = Constants.getSettingsButtonDimension();
        settingsSprite.setSize(settingsLength, settingsLength);
    }

    private void setSpritePositions(){
        Vector2 quickStartPos = Constants.getQuickStartButtonPosition();
        quickStartSprite.setPosition(quickStartPos.x, quickStartPos.y);

        Vector2 worldPos = Constants.getWorldButtonPosition();
        worldSprite.setPosition(worldPos.x, worldPos.y);

        Vector2 customStartPos = Constants.getCustomStartButtonPosition();
        customStartSprite.setPosition(customStartPos.x, customStartPos.y);

        Vector2 highScorePos = Constants.getHighScoreButtonPosition();
        highScoreSprite.setPosition(highScorePos.x, highScorePos.y);

        Vector2 settingsPos = Constants.getSettingsButtonPosition();
        settingsSprite.setPosition(settingsPos.x, settingsPos.y);
    }

}
