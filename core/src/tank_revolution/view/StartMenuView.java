package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.lwjgl.Sys;
import tank_revolution.Utils.Constants;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by simonkarrman on 2017-05-04.
 */
public class StartMenuView implements Viewable {
    Batch batch;
    Sprite newGameSprite;


    public StartMenuView(){
        batch = new SpriteBatch();
        newGameSprite = new Sprite(new Texture(Gdx.files.internal("NewGameButtonIcon.png")));
        float scale = Constants.getNewGameButtonDimention()/newGameSprite.getWidth();
        System.out.println("Scale: "+scale);

        if(scale < 1)
            scale = -scale;

        newGameSprite.setSize(Constants.getNewGameButtonDimention(), Constants.getNewGameButtonDimention());
        Vector2 pos = Constants.getNewGameButtonPosition();
        newGameSprite.setPosition(pos.x, pos.y);
    }
    @Override
    public void update() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        newGameSprite.draw(batch);
        batch.end();
    }

}
