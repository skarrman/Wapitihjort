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
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by simonkarrman on 2017-05-04.
 */
public class StartMenuView implements Viewable {
    private Batch batch;
    private Sprite quickStartSprite;
    private Sprite worldSprite;
    private Sprite customStartSprite;
    private Sprite highScoreSprite;
    private Sprite settingsSprite;
    private Sprite background;



    public StartMenuView(){
        batch = new SpriteBatch();

        List<Texture> textures = AssetsManager.getInstance().getStartMenuTextures();
        quickStartSprite = new Sprite(textures.get(0));
        worldSprite = new Sprite((textures.get(1)));
        customStartSprite = new Sprite(textures.get(2));
        highScoreSprite = new Sprite(textures.get(3));
        settingsSprite = new Sprite(textures.get(4));
        background = new Sprite(new Texture(Gdx.files.internal("StartMenuBackground.png")));
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        setSpriteSizes();
        setSpritePositions();
    }
    @Override
    public void update() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        setBackground();
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

    private void setBackground() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        background.draw(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
