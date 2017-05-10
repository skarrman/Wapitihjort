package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.utils.StringBuilder;
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
     * Graphical representation of the UI buttons
     */
    private Sprite leftMoveButtonSprite;
    private Sprite rightMoveButtonSprite;
    private Sprite pauseMenuButtonSprite;

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

    /**
     * A representation of the animation of an explosion
     */
    private Animation<TextureRegion> explosionAnimation;

    private List<ExplosionAnimation> explosionAnimations;

    private TurnIndicatorAnimation turnIndicatorAnimation;

    private HashMap<Character, GraphicalTank> characterTankHashMap;

    private HashMap<Shootable, GraphicalProjectile> projectileHashMap;

    private LabelDrawer labelDrawer;

    private GameOverView gameOverView;
    /**
     * The standard constructor that initialize everything to make the graphics work.
     *
     * @param environment The current environment.
     */
    public GameView(Environment environment) {
        this.environment = environment;
        batch = new SpriteBatch();
        setUpTankHashMap();
        List<Texture> textures = AssetsManager.getInstance().getUITextures();
        leftMoveButtonSprite = new Sprite(textures.get(0));
        rightMoveButtonSprite = new Sprite(textures.get(1));
        pauseMenuButtonSprite = new Sprite(textures.get(2));
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        createDebugger();
        explosionAnimations = new ArrayList<ExplosionAnimation>();
        turnIndicatorAnimation = new TurnIndicatorAnimation(Constants.pixelsPerMeter());
        turnIndicatorAnimation = new TurnIndicatorAnimation(Constants.pixelsPerMeter());
        labelDrawer = new LabelDrawer();
        gameOverView = new GameOverView();
    }

    /**
     * This is the method that is called every time the physical world is updated.
     * It draws out all the graphical representation of the objects in the world.
     */
    public void update() {
        environment.update();
        camera.update();
        setBackground();
        setCamera();
        if (arrowIsActive) {
            drawVector();
        }
        batch.begin();

        drawTanks();
        drawButtons();

        if (environment.isProjectileFlying()) {
            setUpProjectileHashMap();
            drawProjectiles();
        }
        List<Explosion> explosions = environment.getExplosions();
        if (explosions.size() > 0) {
            for (Explosion e : explosions) {
                explosionAnimations.add(new ExplosionAnimation(e, Constants.pixelsPerMeter()));
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
        }

        batch.end();

        if (deBugMode) {
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

    private void createDebugger() {
        if (deBugMode) {
            debugMatrix = new Matrix4(camera.combined);
            debugRenderer = new Box2DDebugRenderer();
            shapeRenderer = new ShapeRenderer();
        }
    }

    private void drawDebugDetails() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.pixelsPerMeter(), Constants.pixelsPerMeter(), 0);
        debugRenderer.render(environment.getWorld(), debugMatrix);
    }

    private void drawButtons() {
        leftMoveButtonSprite.setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        leftMoveButtonSprite.draw(batch);
        rightMoveButtonSprite.setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        rightMoveButtonSprite.draw(batch);
        pauseMenuButtonSprite.setBounds(Constants.getSettingsButtonPosition().x, Constants.getSettingsButtonPosition().y,
                Constants.getSettingsButtonDimension(), Constants.getSettingsButtonDimension());
        pauseMenuButtonSprite.draw(batch);
    }

    private void setBackground() {
        Gdx.gl.glClearColor(0.980392f, 0.980392f, 0.823529f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    /**
     * Sets the camera
     */
    private void setCamera() {
        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.pixelsPerMeter(), Constants.pixelsPerMeter(), 0);
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
    }


    private void setUpProjectileHashMap() {
        projectileHashMap = new HashMap<Shootable, GraphicalProjectile>();
        for (Shootable s : environment.getFlyingProjectiles()) {
            GraphicalProjectile graphicalProjectile = new GraphicalProjectile(environment.getProjectileBody(s), Constants.pixelsPerMeter());
            projectileHashMap.put(s, graphicalProjectile);
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
