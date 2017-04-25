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
import com.badlogic.gdx.scenes.scene2d.Stage;
import tank_revolution.controller.MoveButton;
import tank_revolution.model.Character;
import tank_revolution.model.GameSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * GameView is the class that present the game to the user.
 * Its responsibility is to render the graphics of the game.
 */
public class GameView {

    /** Tells if the debug renderer should do its' work
     * True = debug: ON
     * False = debug: OFF */
    boolean deBugMode = true;

    /** The graphical batch that draws on the screen */
    private Batch batch;


    /**
     * An instance of the current game
     */
    private GameSession session;

    /**
     * List of the characters in the current game
     */
    private List<Character> characterList;

    /**
     * A constant that convert meters to pixels
     */
    final float metersToPixels;

    /**
     * The graphical representation of the flying projectile
     */
    private Sprite projectile;

    /**
     * An orthogonal camera
     */
    private OrthographicCamera camera;

    /**
     * A debug renderer to debug the box2d bodies
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * A matrix to help the debug process
     */
    private Matrix4 debugMatrix;

    /**
     * A rendering tool that can draw polygon shapes
     */
    private ShapeRenderer shapeRenderer;

    /**
     * This tells if a user is aiming
     */
    private boolean arrowIsActive = false;

    /**
     * This is the value where the user is touching
     */
    private Vector3 aimingArrowBottom;

    /**
     * Value of where the user started to drag on the screen
     */
    private Vector3 getAimingArrowTop;

    /** A representation of the animation of an explosion */
    private Animation<TextureRegion> explosionAnimation;

    private List<ExplosionAnimation> explosionAnimations;

    private  TurnIndicatorAnimation turnIndicatorAnimation;

    private HashMap<Character, GraphicalTank> characterTankHashMap;


    /**
     * The standard constructor that initialize everything to make the graphics work.
     *
     * @param session The current game session.
     */
    public GameView(GameSession session) {
        metersToPixels = Gdx.graphics.getWidth() / 50f; //Calculates the ratio between the pixels of the display to meters in the world.
        this.session = session;
        batch = new SpriteBatch();
        characterList = session.getCharacterList();
        setUpTankHashMap();
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        createDebugger();
        explosionAnimations = new ArrayList<ExplosionAnimation>();
        turnIndicatorAnimation = new TurnIndicatorAnimation(metersToPixels);
    }

    /**
     * This is the method that is called every time the physical world is updated.
     * It draws out all the graphical representation of the objects in the world.
     */
    public void update() {
        camera.update();

        Gdx.gl.glClearColor(0.980392f, 0.980392f, 0.823529f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        setCamera();
        camera.position.set(new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0));

        if(arrowIsActive){
            drawVector();
        }
        batch.begin();
        if (session.isProjectileFlying()) {
            drawProjectile();
        }
        if (session.getExplosions().size() > 0) {
            for (int i = 0; i < session.getExplosions().size(); i++) {
                explosionAnimations.add(new ExplosionAnimation(session.getExplosions().remove(0), metersToPixels));
            }
        }
        if (explosionAnimations.size() > 0) {
            for (int i = 0; i < explosionAnimations.size(); i++) {
                if (!explosionAnimations.get(i).isAnimationFinished())
                    explosionAnimations.get(i).draw(batch);
                else
                    explosionAnimations.remove(i).dispose();
            }
        }
        Vector2 pos = new Vector2(session.getCurrentCharacter().getTank().getStartX(), session.getCurrentCharacter().getTank().getStartY());
        turnIndicatorAnimation.draw(batch, pos);

        drawTanks();
        batch.end();


        if(deBugMode){
            drawDebugDetails();
        }
    }

    /**
     * This method is called if the user is aiming and is about to shoot.
     */
    private void drawVector() {
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
     * @param endX   The x-coordinate of where the user is touching at the moment.
     * @param endY   The y-coordinate of where the user is touching at the moment.
     */
    public void createArrow(float startX, float startY, float endX, float endY) {
        aimingArrowBottom = new Vector3(startX, startY, 0);
        getAimingArrowTop = new Vector3(endX, endY, 0);
        camera.unproject(aimingArrowBottom);
        camera.unproject(getAimingArrowTop);
        arrowIsActive = true;
    }

    /**
     * This method is called when a user end a touch on the screen.
     */
    public void removeVector() {
        arrowIsActive = false;
    }

    private void createDebugger(){
        if(deBugMode) {
            debugMatrix = new Matrix4(camera.combined);
            debugRenderer = new Box2DDebugRenderer();
            shapeRenderer = new ShapeRenderer();
        }
    }

    private void drawDebugDetails(){
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(metersToPixels, metersToPixels, 0);
        debugRenderer.render(session.getEnvironment().getWorld(), debugMatrix);
    }

    public void placeButtons(MoveButton leftButton, MoveButton rightButton, Stage stage){
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.draw();
    }

    /** Sets the camera */
    private void setCamera() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(metersToPixels, metersToPixels, 0);
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
    }

    /** Draws the projectile */
    private void drawProjectile() {
        Vector2 projectilePos = new Vector2(session.getProjectileX(), session.getProjectileY());
        projectile.setPosition(projectilePos.x * metersToPixels - projectile.getWidth() / 2, projectilePos.y * metersToPixels - projectile.getHeight() / 2);
        projectile.draw(batch);
    }
    private void setUpTankHashMap(){
        characterTankHashMap = new HashMap<Character, GraphicalTank>();
        for(Character c : characterList){
            GraphicalTank graphicalTank = new GraphicalTank(session.getEnvironment().getTank(c.getTank()), c.getId(), c.getTank().getAngle(), metersToPixels);
            characterTankHashMap.put(c, graphicalTank);
        }
    }

    /** Draws the tank*/
    private void drawTanks() {
        characterList = session.getCharacterList();
        for(Character c : characterList){
            GraphicalTank graphicalTank = characterTankHashMap.get(c);
            graphicalTank.draw(batch);
        }
        checkCharacterList();
    }

    private void checkCharacterList(){
        if(characterTankHashMap.size() > characterList.size()){
            HashMap<Character, GraphicalTank> tempMap = new HashMap<Character, GraphicalTank>();
            for(Character c : characterList){
                tempMap.put(c, characterTankHashMap.get(c));
            }
            characterTankHashMap = tempMap;
        }
    }

    /**
     * This disposes the graphical items.
     */
    public void dispose() {
        //for (TextureAtlas textureAtlas : textureAtlases) {
            //textureAtlas.dispose();
       // }
        if(deBugMode){
            debugRenderer.dispose();
        }
        turnIndicatorAnimation.dispose();
        batch.dispose();
    }

}
