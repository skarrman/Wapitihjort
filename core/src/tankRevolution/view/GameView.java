package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.framework.Environment;
import tankRevolution.model.Character;
import tankRevolution.model.Explosion;
import tankRevolution.model.shootablePackage.Shootable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * GameView is the class that present the game to the user.
 * Its responsibility is to render the graphics of the game.
 */
public class GameView implements Viewable {

    /**
     * Tells if the debug renderer should do its' work
     * True = debug: ON
     * False = debug: OFF
     */
    private boolean deBugMode = false;

    /**
     * The graphical batch that draws on the screen
     */
    private Batch batch;

    /**
     * The environment
     */
    private Environment environment;

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
     * Tells if a move button is pressed
     */
    private boolean isPressed;

    /**
     * This is the value where the user is touching
     */
    private Vector2 aimingArrowBottom;

    /**
     * Value of where the user started to drag on the screen
     */
    private Vector2 aimingArrowTop;

    /**
     * The list of all the on going explosion animations.
     */
    private List<ExplosionAnimation> explosionAnimations;

    /** The instance that handles the arrow that show whose turn it is */
    private TurnIndicatorAnimation turnIndicatorAnimation;

    /** A map that connect a character with a graphical tank */
    private HashMap<Character, GraphicalTank> characterTankHashMap;

    /** A map that connect a shootable with a graphical projectile */
    private HashMap<Shootable, GraphicalProjectile> projectileHashMap;

    /** The instance of the class that handles the drawing of labels on the screen */
    private LabelDrawer labelDrawer;

    /** Handles the graphical elements that is shown when the game is over */
    private GameOverView gameOverView;

    /** The sound of a shot that is fired */
    private Sound shotSound;

    /** The sound of an explosion */
    private Sound explosionSound;

    /** The graphical representation of the weapon switch */
    private  WeaponSwitch weaponSwitch;

    /** The background of the map */
    private Sprite background;

    /** The graphical representation of the terrain */
    private GraphicalTerrain terrain;

    /**
     * The standard constructor that initialize everything to make the graphics work.
     *
     * @param environment The current environment.
     */
    public GameView(Environment environment) {
        isPressed = false;
        this.environment = environment;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        setUpTankHashMap();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        createDebugger();
        explosionAnimations = new ArrayList<ExplosionAnimation>();
        turnIndicatorAnimation = new TurnIndicatorAnimation(Constants.pixelsPerMeter());
        turnIndicatorAnimation = new TurnIndicatorAnimation(Constants.pixelsPerMeter());
        labelDrawer = new LabelDrawer();
        gameOverView = new GameOverView();
        shotSound = AssetsManager.getInstance().getSoundEffects().get(0);
        explosionSound = AssetsManager.getInstance().getSoundEffects().get(1);
        projectileHashMap = new HashMap<Shootable, GraphicalProjectile>();
        weaponSwitch = new WeaponSwitch();
        background = new Sprite(new Texture(Gdx.files.internal("background.png")));
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        terrain = new GraphicalTerrain();
    }

    /**
     * This is the method that is called every time the physical world is updated.
     * It draws out all the graphical representation of the objects in the world.
     */
    public void update() {
        camera.update();

        setCamera();
        batch.begin();

        setBackground();
        drawTerrain();
        drawTanks();



        if (environment.isProjectileFlying()) {
            setUpProjectileHashMap();
            drawProjectiles();
        }
        List<Explosion> explosions = environment.getExplosions();
        if (explosions.size() > 0) {
            for (Explosion e : explosions) {
                explosionAnimations.add(new ExplosionAnimation(e, Constants.pixelsPerMeter()));
                explosionSound.play();
            }
            explosions.clear();
        }
        if (explosionAnimations.size() > 0) {
            for (int i = 0; i < explosionAnimations.size(); i++) {
                if (!explosionAnimations.get(i).isAnimationFinished()) {
                    explosionAnimations.get(i).draw(batch);
                }
                else {
                    explosionAnimations.remove(i);
                }
            }
        }

        if(environment.gameOver()){
            gameOverView.draw(batch);
        }else {
            Body currentPlayer = environment.getTankBody(environment.getCurrentTank());
            turnIndicatorAnimation.draw(batch, currentPlayer.getPosition());
            labelDrawer.draw(environment.getCharacterList(), batch);
            weaponSwitch.draw(batch, environment.getCurrentWeapon());
            GraphicalUIButtons.draw((int)environment.getCurrentTank().getFuel()/10, batch, isPressed);
            environment.update();
        }


        batch.end();

        if (arrowIsActive) {

            drawVectorArrow();
        }

        if (deBugMode) {
            drawDebugDetails();
        }

    }

    /**
     * This method is called if the user is aiming and is about to shoot. Draws one line from where the user
     * touched the screen to the point where it releases. Draws two lines from the top of the line to make it an arrow.
     */
    private void drawVectorArrow() {
        float angle = getAngle(aimingArrowBottom.x, aimingArrowBottom.y, aimingArrowTop.x, aimingArrowTop.y);
        float angle1 = angle + 45;
        float angle2 = angle - 45;
        Vector2 arrow1 = getArrowLine(aimingArrowBottom, angle1);
        Vector2 arrow2 = getArrowLine(aimingArrowBottom, angle2);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.line(arrow1.x, arrow1.y, aimingArrowBottom.x, aimingArrowBottom.y);
        shapeRenderer.line(arrow2.x, arrow2.y, aimingArrowBottom.x, aimingArrowBottom.y);
        shapeRenderer.line(aimingArrowBottom.x, aimingArrowBottom.y, aimingArrowTop.x, aimingArrowTop.y);
        shapeRenderer.end();
    }

    /**
     * Returns the angle of the arrow that the user draws on the screen when aiming.
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     * @return The angle of the line that is between the two points.
     */
    private float getAngle(float x1, float y1, float x2, float y2){
        float angle = (float) -Math.toDegrees(Math.atan2(x2 - x1, y2 - y1)) + 90;
        if (angle < 0){
            angle += 360;
        }
        return angle;
    }

    /**
     * The method that calculates the points to make the tip of the arrow.
     * @param center The end point of the arrow.
     * @param angle The angle of the line.
     * @return The point to where a line will be drawn to make an arrow tip.
     */
    private Vector2 getArrowLine(Vector2 center, float angle){
        float x = (float)(Math.cos(Math.toRadians(angle)) * 50) + center.x;
        float y = (float)(Math.sin(Math.toRadians(angle)) * 50) + center.y;
        return new Vector2(x, y);

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
        Vector3 unProjectVectorBottom = new Vector3(startX, startY, 0);
        Vector3 unProjectVectorTop = new Vector3(endX, endY, 0);
        camera.unproject(unProjectVectorBottom);
        camera.unproject(unProjectVectorTop);
        aimingArrowBottom = new Vector2(unProjectVectorBottom.x, unProjectVectorBottom.y);
        aimingArrowTop = new Vector2(unProjectVectorTop.x, unProjectVectorTop.y);
        arrowIsActive = true;
    }

    /**
     * This method is called when a user end a touch on the screen.
     */
    public void removeVector() {
        arrowIsActive = false;
    }

    /**
     * Initialize the debugger.
     */
    private void createDebugger() {
        debugMatrix = new Matrix4(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * Draws the debug details.
     */
    private void drawDebugDetails() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.pixelsPerMeter(), Constants.pixelsPerMeter(), 0);
        debugRenderer.render(environment.getWorld(), debugMatrix);
    }

    /**
     * Sets the background.
     */
    private void setBackground() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        background.draw(batch);
    }

    /**
     * Sets the camera
     */
    private void setCamera() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.pixelsPerMeter(), Constants.pixelsPerMeter(), 0);
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
    }

    /**
     * Draws the terrain.
     */
    private void drawTerrain(){
        List<float[]> vertices = environment.getVertices();
        terrain.draw(vertices, environment.isTerrainChanged(), batch);
    }


    /**
     * Pairing shootables with flying projectiles in a map.
     */
    private void setUpProjectileHashMap() {
        for (Shootable s : environment.getFlyingProjectiles()) {
            if (!projectileHashMap.containsKey(s)){
                GraphicalProjectile graphicalProjectile = new GraphicalProjectile(environment.getProjectileBody(s), Constants.pixelsPerMeter());
                projectileHashMap.put(s, graphicalProjectile);
                shotSound.play();
            }
        }
    }

    /**
     * Draws the projectile
     */
    private void drawProjectiles() {
        for (Shootable s : environment.getFlyingProjectiles()) {
            GraphicalProjectile graphicalProjectile = projectileHashMap.get(s);
                graphicalProjectile.draw(batch);
        }
        checkShootableList();

    }

    /**
     * Pairing characters with graphical tanks in a map.
     */
    private void setUpTankHashMap() {
        characterTankHashMap = new HashMap<Character, GraphicalTank>();
        for (Character c : environment.getCharacterList()) {
            GraphicalTank graphicalTank = new GraphicalTank(environment.getTankBody(c.getTank()), c.getId(),
                    c.getTank().getAngle(),Constants.pixelsPerMeter(), c.getTank().getWidth(), c.getTank().getHeight());
            characterTankHashMap.put(c, graphicalTank);
        }
    }

    /**
     * Draws all the tanks that is in in the current game and alive.
     */
    private void drawTanks() {
        for (Character c : environment.getCharacterList()) {
            GraphicalTank graphicalTank = characterTankHashMap.get(c);
            graphicalTank.draw(batch);
        }
        checkCharacterList();
    }

    /**
     * Checks the map with characters and tank and sees if someone has died.
     * In that case that tank/character is removed from the map.
     */
    private void checkCharacterList() {
        if (characterTankHashMap.size() > environment.getCharacterList().size()) {
            HashMap<Character, GraphicalTank> tempMap = new HashMap<Character, GraphicalTank>();
            for (Character c : environment.getCharacterList()) {
                tempMap.put(c, characterTankHashMap.get(c));
            }
            characterTankHashMap = tempMap;
        }
    }

    /**
     * Checks the map with shootables and projectiles and sees if any has exploded.
     * In that case that shootable is removed from the map.
     */
    private void checkShootableList() {
        if (projectileHashMap.size() > environment.getFlyingProjectiles().size()) {
            HashMap<Shootable, GraphicalProjectile> tempMap = new HashMap<Shootable, GraphicalProjectile>();
            for (Shootable s : environment.getFlyingProjectiles()) {
                tempMap.put(s, projectileHashMap.get(s));
            }
            projectileHashMap = tempMap;
        }
    }

    /**
     * Tells the button if it is pressed of not.
     * @param pressed The value if the buttons is pressed.
     */
    public void setPressed(boolean pressed){
        isPressed = pressed;
    }

    /**
     * This disposes the graphical items.
     */
    public void dispose() {
        if (deBugMode) {
            debugRenderer.dispose();
        }
        turnIndicatorAnimation.dispose();
        environment.dispose();
        batch.dispose();
    }

}
