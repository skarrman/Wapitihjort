package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import tank_revolution.Utils.Observer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import tank_revolution.controller.MoveButton;
import tank_revolution.model.Character;
import tank_revolution.model.GameSession;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * GameView is the class that present the game to the user.
 * Its responsibility is to render the graphics of the game.
 */
public class GameView implements Observer {
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

    /** An orthogonal camera */
    private OrthographicCamera camera;

    /** A debug renderer to debug the box2d bodies */
    private Box2DDebugRenderer debugRenderer;

    /** A matrix to help the debug process */
    private Matrix4 debugMatrix;

    /** A rendering tool that can draw polygon shapes */
    private ShapeRenderer shapeRenderer;

    /** This tells if a user is aiming */
    private boolean arrowIsActive = false;

    /** This is the value where the user is touching */
    private Vector3 aimingArrowBottom;

    /** Value of where the user started to drag on the screen */
    private Vector3 getAimingArrowTop;

    Animation<TextureRegion> explosionAnimation;

    private boolean isAnimatingExplosion = false;

    private Vector2 explosionPosition;

    private int blastRadius;

    private float animationTime;
    /** Button allowing the user to move the tank to the right */
    private MoveButton rightButton;

    /** Button allowing the user to move the tank to the left */
    private MoveButton leftButton;

    private Stage stage;


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
        rightButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));
        leftButton = new MoveButton(new Texture(Gdx.files.internal("Projectile.png")));
        stage = new Stage();
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        Gdx.input.setInputProcessor(stage);
        metersToPixels = Gdx.graphics.getWidth()/50f; //Calculates the ratio between the pixels of the display to meters in the world.
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugMatrix = new Matrix4(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Explosion.txt"));
        explosionAnimation = new Animation<TextureRegion>(1/20f, textureAtlas.getRegions());
    }

    /**
     * This is the method that is called every time the physical world is updated.
     * It draws out all the graphical representation of the objects in the world.
     */
    public void update(){
        camera.update();
        Gdx.gl.glClearColor(0.980392f, 0.980392f, 0.823529f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        setCamera();
        placeButtons();
        if(arrowIsActive){
            drawVector();
        }
        batch.begin();
        if(session.isProjectileFlying()){
            drawProjectile();
        }else if(isAnimatingExplosion){
            animationTime += Gdx.graphics.getDeltaTime();
            if(!explosionAnimation.isAnimationFinished(animationTime)){
                animateExplosion();
            }else{
                isAnimatingExplosion = false;
            }
        }
        drawTanks();
        batch.end();
        debugRenderer.render(characterList.get(0).getTank().getBody().getWorld(), debugMatrix);
    }

    /**
     * This method is called if the user is aiming and is about to shoot.
     */
    private void drawVector(){
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.line(aimingArrowBottom.x, aimingArrowBottom.y, getAimingArrowTop.x, getAimingArrowTop.y);
        shapeRenderer.end();
    }

    /**
     * This method is called when a user makes a drag on the screen.
     *
     * @param startX The x-coordinate of the start point of the drag.
     * @param startY The y-coordinate of the start point of the drag.
     * @param endX The x-coordinate of where the user is touching at the moment.
     * @param endY The y-coordinate of where the user is touching at the moment.
     */
    public void createArrow(float startX, float startY, float endX, float endY){
        aimingArrowBottom = new Vector3(startX,startY, 0);
        getAimingArrowTop = new Vector3(endX, endY, 0);
        camera.unproject(aimingArrowBottom);
        camera.unproject(getAimingArrowTop);
        arrowIsActive = true;
    }

    /**
     * This method is called when a user end a touch on the screen.
     */
    public void removeVector(){
        arrowIsActive = false;
    }

    @Override
    public void actOnChange(Vector2 position, int value) {
        isAnimatingExplosion = true;
        animationTime = 0;
        explosionPosition = position;
        blastRadius = value;
    }

    private void placeButtons(){
        leftButton.setBounds(metersToPixels, 0, 2*metersToPixels, Gdx.graphics.getHeight());
        rightButton.setBounds(Gdx.graphics.getWidth()-(2*metersToPixels), 0, 2*metersToPixels, Gdx.graphics.getHeight());
        stage.draw();
    }

    private void setCamera(){
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(metersToPixels, metersToPixels, 0);
        camera.position.set(new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0));
    }

    private void drawProjectile(){
        Vector2 projectilePos = new Vector2(session.getProjectileX(), session.getProjectileY());
        projectile.setPosition(projectilePos.x * metersToPixels - projectile.getWidth()/2, projectilePos.y * metersToPixels - projectile.getHeight()/2);
        projectile.draw(batch);
    }

    private void drawTanks(){
        TextureAtlas.AtlasRegion atlasRegion;
        for(int i = 0; i < characterList.size(); i++) {
            atlasRegion = textureAtlases.get(i).getRegions().first();
            Vector2 pos = characterList.get(i).getTank().getBody().getPosition();
            batch.draw(atlasRegion, (pos.x * metersToPixels) - atlasRegion.getRegionWidth()/2,
                    (pos.y * metersToPixels) - atlasRegion.getRegionHeight()/4);
        }
    }

    private void animateExplosion(){
        TextureRegion animationFrame = new TextureRegion(explosionAnimation.getKeyFrame(animationTime, false));
        float x = (explosionPosition.x * metersToPixels) - (animationFrame.getRegionWidth()/2);
        float y = (explosionPosition.y * metersToPixels) - (animationFrame.getRegionHeight()/2);
        batch.draw(animationFrame, x, y);
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
