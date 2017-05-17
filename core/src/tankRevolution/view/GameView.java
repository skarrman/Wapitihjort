package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
    private boolean deBugMode = true;

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
    private Vector3 aimingArrowBottom;

    /**
     * Value of where the user started to drag on the screen
     */
    private Vector3 getAimingArrowTop;

    private List<ExplosionAnimation> explosionAnimations;

    private TurnIndicatorAnimation turnIndicatorAnimation;

    private HashMap<Character, GraphicalTank> characterTankHashMap;

    private HashMap<Shootable, GraphicalProjectile> projectileHashMap;

    private LabelDrawer labelDrawer;

    private GameOverView gameOverView;

    private Sound shotSound;

    private Sound explostionSound;

    private  WeaponSwitch weaponSwitch;

    private Sprite background;

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
        explostionSound = AssetsManager.getInstance().getSoundEffects().get(1);
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
       /* if (arrowIsActive) {
            drawVector();
        }*/

        batch.begin();

        setBackground();
        drawTerrain();
        drawTanks();


        GraphicalUIButtons.draw((int)environment.getCurrentTank().getFuel()/10, batch, isPressed);

        if (environment.isProjectileFlying()) {
            setUpProjectileHashMap();
            drawProjectiles();
        }
        List<Explosion> explosions = environment.getExplosions();
        if (explosions.size() > 0) {
            for (Explosion e : explosions) {
                explosionAnimations.add(new ExplosionAnimation(e, Constants.pixelsPerMeter()));
                explostionSound.play();
            }
            explosions.clear();
        }
        if (explosionAnimations.size() > 0) {
            for (int i = 0; i < explosionAnimations.size(); i++) {
                if (!explosionAnimations.get(i).isAnimationFinished())
                    explosionAnimations.get(i).draw(batch);
                else
                    explosionAnimations.remove(i);
            }
        }
        Body currentPlayer = environment.getTankBody(environment.getCurrentTank());
        turnIndicatorAnimation.draw(batch, currentPlayer.getPosition());

        if(environment.gameOver()){
            gameOverView.draw(batch);
        }else {
            labelDrawer.draw(environment.getCharacterList(), batch);
            environment.update();
        }

        weaponSwitch.draw(batch, environment.getCurrentWeapon());

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
        float angle = getAngle(aimingArrowBottom.x, aimingArrowBottom.y, getAimingArrowTop.x, getAimingArrowTop.y);
        float angle1 = angle + 45;
        float angle2 = angle - 45;
        Vector3 arrow1 = getArrowLine(aimingArrowBottom, angle1);
        Vector3 arrow2 = getArrowLine(aimingArrowBottom, angle2);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.line(arrow1.x, arrow1.y, aimingArrowBottom.x, aimingArrowBottom.y);
        shapeRenderer.line(arrow2.x, arrow2.y, aimingArrowBottom.x, aimingArrowBottom.y);
        shapeRenderer.line(aimingArrowBottom.x, aimingArrowBottom.y, getAimingArrowTop.x, getAimingArrowTop.y);
        shapeRenderer.end();
    }

    private float getAngle(float x1, float y1, float x2, float y2){
        float angle = (float) -Math.toDegrees(Math.atan2(x2 - x1, y2 - y1)) + 90;
        if (angle < 0){
            angle += 360;
        }
        return angle;
    }

    private Vector3 getArrowLine(Vector3 center, float angle){
        float x = (float)(Math.cos(Math.toRadians(angle)) * 50) + center.x;
        float y = (float)(Math.sin(Math.toRadians(angle)) * 50) + center.y;
        return new Vector3(x, y, 0);

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

    private void createDebugger() {
        debugMatrix = new Matrix4(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
    }

    private void drawDebugDetails() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.pixelsPerMeter(), Constants.pixelsPerMeter(), 0);
        debugRenderer.render(environment.getWorld(), debugMatrix);
    }

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

    private void drawTerrain(){
        List<float[]> vertices = environment.getVertices();
        terrain.draw(vertices, environment.isTerrainChanged(), batch);
        /*for(float[] v : vertices){
            shapeRenderer.setProjectionMatrix(debugMatrix);
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.polygon(v);
            shapeRenderer.end();
        }*/
    }


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

    private void setUpTankHashMap() {
        characterTankHashMap = new HashMap<Character, GraphicalTank>();
        for (Character c : environment.getCharacterList()) {
            GraphicalTank graphicalTank = new GraphicalTank(environment.getTankBody(c.getTank()), c.getId(),
                    c.getTank().getAngle(),Constants.pixelsPerMeter(), c.getTank().getWidth(), c.getTank().getHeight());
            characterTankHashMap.put(c, graphicalTank);
        }
    }

    /**
     * Draws the tank
     */
    private void drawTanks() {
        for (Character c : environment.getCharacterList()) {
            GraphicalTank graphicalTank = characterTankHashMap.get(c);
            graphicalTank.draw(batch);
        }
        checkCharacterList();
    }

    private void checkCharacterList() {
        if (characterTankHashMap.size() > environment.getCharacterList().size()) {
            HashMap<Character, GraphicalTank> tempMap = new HashMap<Character, GraphicalTank>();
            for (Character c : environment.getCharacterList()) {
                tempMap.put(c, characterTankHashMap.get(c));
            }
            characterTankHashMap = tempMap;
        }
    }

    private void checkShootableList() {
        if (projectileHashMap.size() > environment.getFlyingProjectiles().size()) {
            HashMap<Shootable, GraphicalProjectile> tempMap = new HashMap<Shootable, GraphicalProjectile>();
            for (Shootable s : environment.getFlyingProjectiles()) {
                tempMap.put(s, projectileHashMap.get(s));
            }
            projectileHashMap = tempMap;
        }
    }

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
        batch.dispose();
    }

}
