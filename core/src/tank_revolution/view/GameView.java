package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
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

    /** The graphical representation of the flying projectile */
    private Sprite projectile;

    /** An orthoginal camera */
    private OrthographicCamera camera;

    /** A debug renderer to debug the box2d bodies */
    private Box2DDebugRenderer debugRenderer;

    /** A matrix to help the debug process */
    private Matrix4 debugMatrix;

    private ShapeRenderer shapeRenderer;

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
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
        metersToPixels = Gdx.graphics.getWidth()/50f; //Calculates the ratio between the pixels of the display to meters in the world.
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugMatrix = new Matrix4(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

    }

    /**
     * This is the method that is called every time the physical world is updated.
     * It draws out all the graphical representation of the objects in the world.
     */
    public void update(){
        camera.update();

        Gdx.gl.glClearColor(0.980392f, 0.980392f, 0.823529f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(metersToPixels, metersToPixels, 0);
        camera.position.set(new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0));
        batch.begin();

        if(session.isProjectileFlying()){
            Vector2 projectilePos = session.getProjectilePosision();
            projectile.setPosition(projectilePos.x * metersToPixels - projectile.getWidth()/2, projectilePos.y * metersToPixels - projectile.getHeight()/2);
            projectile.draw(batch);
        }
        TextureAtlas.AtlasRegion atlasRegion;
        for(int i = 0; i < characterList.size(); i++) {
            atlasRegion = textureAtlases.get(i).getRegions().first();
            Vector2 pos = characterList.get(i).getTank().getBody().getPosition();
            batch.draw(atlasRegion, (pos.x * metersToPixels) - atlasRegion.getRegionWidth()/2,
                                    (pos.y * metersToPixels) - atlasRegion.getRegionHeight()/4);
        }

        batch.end();
        debugRenderer.render(characterList.get(0).getTank().getBody().getWorld(), debugMatrix);
    }

    public void drawVector(float startX, float startY, float endX, float endY){
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.line(startX, startY, 0, endX, endY, 0);
        int lineWidth = 10; // pixels
        //Gdx.gl20.glLineWidth(lineWidth / camera.zoom);
        shapeRenderer.end();
    }

    public void removeVector(){

    }

    /**
     * This disposes the graphical items.
     */
    public void dispose(){
        for(TextureAtlas textureAtlas: textureAtlases){
            textureAtlas.dispose();
        }
        debugRenderer.dispose();
        batch.dispose();
    }
}
