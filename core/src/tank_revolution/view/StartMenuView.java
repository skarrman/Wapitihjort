package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.Utils.Constants;

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



    public StartMenuView(){
        batch = new SpriteBatch();

        quickStartSprite = new Sprite(new Texture(Gdx.files.internal("NewGameButtonIcon.png")));
        worldSprite = new Sprite(new Texture(Gdx.files.internal("Disabled.png")));
        customStartSprite = new Sprite(new Texture(Gdx.files.internal("Disabled.png")));
        highScoreSprite = new Sprite(new Texture(Gdx.files.internal("Disabled.png")));

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
        batch.end();
    }

    private void setSpriteSizes(){
        float sideLength = Constants.getStartMenuButtonDimension();
        quickStartSprite.setSize(sideLength, sideLength);
        worldSprite.setSize(sideLength, sideLength);
        customStartSprite.setSize(sideLength, sideLength);
        highScoreSprite.setSize(sideLength, sideLength);
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
    }

}
