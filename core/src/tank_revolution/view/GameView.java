package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.model.Character;
import tank_revolution.model.GameModel;
import tank_revolution.model.GameSession;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by JakobErlandsson on 2017-03-30.
 */
public class GameView {
    private Batch batch;
    private List<TextureAtlas> textureAtlases;
    private GameSession session;
    private List<Character> characterList;


    public GameView(GameSession session){
        this.session = session;
        batch = new SpriteBatch();
        characterList = session.getCharacterList();
        textureAtlases = new ArrayList<TextureAtlas>();
        textureAtlases.add(new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
        textureAtlases.add(new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));

    }

    public void update(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(int i = 0; i < characterList.size(); i++) {
            Vector2 pos = characterList.get(i).getTank().getBody().getPosition();
            batch.draw(textureAtlases.get(i).getRegions().first(), pos.x, pos.y);
        }
        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }
}
