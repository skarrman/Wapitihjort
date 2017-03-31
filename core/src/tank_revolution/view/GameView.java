package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.model.Character;
import tank_revolution.model.GameModel;

import javax.swing.text.Position;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by JakobErlandsson on 2017-03-30.
 */
public class GameView {
    private Batch batch;
    private TextureAtlas textureAtlas;
    private GameModel model;
    private List<Character> characterList;


    public GameView(GameModel model){
        this.model = model;
        batch = new SpriteBatch();
        characterList = model.getCharacterList();
        textureAtlas = new TextureAtlas(Gdx.files.internal("GreenTank.txt"));

    }

    public void update(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        Vector2 pos = characterList.get(0).getTank().getBody().getPosition();
        batch.draw(textureAtlas.getRegions().first(),pos.x,pos.y);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }
}
