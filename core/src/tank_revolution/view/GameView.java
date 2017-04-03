package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tank_revolution.model.Character;
import tank_revolution.model.GameSession;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * GameView is the class that present the game to the user.
 * Its responsibility is to render the graphics of the game.
 */
public class GameView {
    private Batch batch;

    /** List of the tank sprite sheets. */
    private List<TextureAtlas> textureAtlases;

    /** An instance of the current game */
    private GameSession session;

    /** List of the characters in the current game */
    private List<Character> characterList;

    /** A constant that convert meters to pixels */
    private final float metersToPixels;

    /**
     * The standard constructor that initialize everything to make the graphics work.
     * @param session The current game session.
     */
    public GameView(GameSession session){
        this.session = session;
        batch = new SpriteBatch();
        characterList = session.getCharacterList();
        textureAtlases = new ArrayList<TextureAtlas>();
        textureAtlases.add(new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
        textureAtlases.add(new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));
        metersToPixels = Gdx.graphics.getWidth()/50; //

    }

    /**
     * This is the method that is called every time the physical world is updated.
     */
    public void update(){
        Gdx.gl.glClearColor(0.980392f, 0.980392f, 0.823529f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        TextureAtlas.AtlasRegion atlasRegion;
        for(int i = 0; i < characterList.size(); i++) {
            atlasRegion = textureAtlases.get(i).getRegions().first();
            Vector2 pos = characterList.get(i).getTank().getBody().getPosition();
            batch.draw(atlasRegion, (pos.x * metersToPixels) - atlasRegion.getRegionWidth()/2,
                                    (pos.y * metersToPixels) - atlasRegion.getRegionHeight()/2);
        }
        batch.end();
    }

    /**
     * This disposes the graphical items.
     */
    public void dispose(){
        batch.dispose();
    }
}
