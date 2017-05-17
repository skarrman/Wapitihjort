package tankRevolution.framework;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.quailshillstudio.polygonClippingUtils.CollisionGeometry;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.utils.*;
import tankRevolution.framework.terrain.ITerrainHandler;
import tankRevolution.model.TankRevolution;
import tankRevolution.model.Character;
import tankRevolution.model.NPC;
import tankRevolution.model.Explosion;
import tankRevolution.model.shootablePackage.Shootable;
import tankRevolution.model.Tank;
import tankRevolution.framework.terrain.TerrainHandler;
import tankRevolution.utils.Vector;


/**
 * Model class holding everything that has to do with the framework "LibGDX"
 * Wrapper for the logical model and handles all input to it.
 */
public class Environment {

    /**
     * The logical model free from the framework.
     */
    private TankRevolution tankRevolution;

    /**
     * The world in which all objects will live.
     */
    private World world;

    /**
     * The active tanks in the world and their body.
     */
    private Map<Tank, Body> tanks;

    /**
     * The active projectiles in the world and their body.
     */
    private Map<Shootable, Body> projectiles;

    /**
     * List of bodies pending to be destroyed.
     */
    private List<Body> removeStack;

    /**
     * List of current explosions.
     */
    private List<Explosion> explosions;

    /**
     * TODO
     */
    private ITerrainHandler terrainHandler;
    
    /**
     * A boolean indicating if the terrain is changed.
     */
    private boolean isTerrainChanged = true;

    /**
     * The current time.
     */
    private double time;

    /**
     * Creates a new Environment for Bodies to live in.
     * @param tankRevolution The logical part of the model that not interacts with the framework.
     * @param mapName The name of the map being used in the game.
     */
    public Environment(TankRevolution tankRevolution, String mapName) {
        this.tankRevolution = tankRevolution;
        this.time = System.currentTimeMillis();
        this.tanks = new HashMap<Tank, Body>();
        this.projectiles = new HashMap<Shootable, Body>();
        this.removeStack = new ArrayList<Body>();
        this.explosions = new ArrayList<Explosion>();

        setupWorld(mapName);
        setupTanks();
        createContactListener();
    }

    /**
     * Sets up the world with gravity and terrain with two sides.
     * @param currentMap The name of the map being used in the game.
     */
    private void setupWorld(String currentMap) {
        Vector2 g = new Vector2(0f, Constants.getGravity());
        world = new World(g, true);

        terrainHandler = new TerrainHandler(world, currentMap);
        setupSides();
    }

    /**
     * Sets up both sides.
     */
    private void setupSides() {
        setupSide(0);
        setupSide(Constants.getMapWidth() + 0);
    }

    /**
     * Set up one side.
     * @param x the x coordinate of the side being created.
     */
    private void setupSide(float x) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        EdgeShape wall = new EdgeShape();
        wall.set(x, 0, x, Constants.getMapWidth() * 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = wall;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(new UserData(UserData.BALL));
        wall.dispose();
    }


    /**
     * Adds a projectile to the world.
     * @param projectile The instance of the projectile being shoot.
     * @param shooter The tank shooting the projectile.
     * @param deltaX The horizontal force (left to right) of the projectile.
     * @param deltaY The vertical force (down to up) of the projectile.
     */
    private void addProjectile(Shootable projectile, Tank shooter, float deltaX, float deltaY) {
        //Get the projectiles initial position (above the tank).
        float x = tanks.get(shooter).getPosition().x;
        float y = tanks.get(shooter).getPosition().y + Constants.getShootOffsetTank();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectile.getMissileRadius(), projectile.getMissileRadius());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = projectile.getMissileDensity();

        body.createFixture(fixtureDef);
        shape.dispose();

        Vector2 force = new Vector2(deltaX, deltaY);
        body.setLinearVelocity(force);
        body.setUserData(new UserData(1));

        //Put the projectile in the map of active projectiles.
        projectiles.put(projectile, body);
    }


    /**
     * Adds a tank to a specific position in the world based on the owning player.
     * @param tank The instance of the tank being initialized to the world.
     * @param id The player of the tank.
     */
    private void addTank(Tank tank, Id id) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getStartingPosition(id));

        Body body = world.createBody(bodyDef);
        body.setUserData(new UserData(2));
        PolygonShape shape = createCircleShape(tank.getWidth() / 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = tank.getDensity();
        body.createFixture(fixtureDef);
        shape.dispose();

        //Put the tank in the map of active tanks.
        tanks.put(tank, body);
    }

    /**
     * Returns a polygonshape of an approximated circle.
     * @param radius the radius of the circle.
     * @return A polygonshape of an approximated circle.
     */
    private PolygonShape createCircleShape(float radius) {
        float[] floatVertices = CollisionGeometry.approxCircle(0, 0, radius, 6);
        PolygonShape shape = new PolygonShape();
        shape.set(floatVertices);
        return shape;
    }

    /**
     * Setup the tanks of all characters.
     */
    private void setupTanks() {
        for (Character c : getCharacterList()) {
            addTank(c.getTank(), c.getId());
        }
    }

    /**
     * Returns the world of the game.
     * @return the world of the game.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the body connected to a tank.
     * @param tank the logical model of the tank.
     * @return The body och that tank.
     */
    public Body getTankBody(Tank tank) {
        return tanks.get(tank);
    }

    /**
     * Returns the body connected to a projectile.
     * @param s The logical model of the projectile.
     * @return The body och that tank.
     */
    public Body getProjectileBody(Shootable s) {
        return projectiles.get(s);
    }

    /**
     * Returns a list of current explosions.
     * @return a list of current explosions.
     */
    public List<Explosion> getExplosions() {
        return explosions;
    }

    /**
     * Checks if it's only one player left alive.
     * @return true if it's only one player left.
     */
    public boolean gameOver() {
        return tanks.size() < 2;
    }

    /**
     * Disposes the world.
     */
    public void dispose() {
        world.dispose();
    }

    /**
     * TODO refactoring
     */
    private void tankOutsideMapCheck() {
        for (int i = 0; i < getCharacterList().size(); i++) {
            Vector2 position = tanks.get(getCharacterList().get(i).getTank()).getPosition();
            if (position.y < -2) {
                destroyTank(getCharacterList().get(i).getTank());
                i--;
            }
        }
    }

    /**
     * TODO refactoring
     */
    private void projectileOutsideMapCheck() {
        for (int i = 0; i < getFlyingProjectiles().size(); i++) {
            Vector2 position = projectiles.get(getFlyingProjectiles().get(i)).getPosition();
            if (position.y < -2) {
                destroyProjectile(getFlyingProjectiles().get(i));
                i--;
            }
        }
    }

    /**
     * Is called for the world to update the bodies within it.
     */
    public void update() {
        tankOutsideMapCheck();
        projectileOutsideMapCheck();
        setAllTanksFalling();
        stackUpdate();
        time = System.currentTimeMillis() - time;
        world.step((Math.min((float) time / 1000, Gdx.graphics.getDeltaTime())), 6, 2);
        terrainHandler.update();
        if (NPCWillShoot()) {
            NPCDoShoot();
        }
        time = System.currentTimeMillis();
    }

    /**
     * TODO
     */
    public boolean isTerrainChanged() {
        boolean value = isTerrainChanged;
        isTerrainChanged = false;

        return value;
    }

    /**
     * Updates the remove-stack.
     */
    private void stackUpdate() {
        if (!world.isLocked() && !removeStack.isEmpty()) {
            world.destroyBody(removeStack.get(removeStack.size() - 1));
            removeStack.remove(removeStack.size() - 1);
        }
    }

    /**
     * Checks if the world is locked.
     * @return true if the world is locked.
     */
    public boolean isLocked() {
        return world.isLocked();
    }

    /**
     * TODO JAKOB ERLANDSSON?
     * @param body
     * @param isFalling
     */
    public void setTankFalling(Body body, boolean isFalling){
        for (Tank t: tanks.keySet()){
            if (tanks.get(t).equals(body)){
                t.setTankFalling(isFalling);
            }
        }
    }

    /**
     * TODO Jakob Erlandsson?
     */
    private void setAllTanksFalling(){
        for (int i  = 0; i < tanks.size(); i++){
            setTankFalling(tanks.get(getCharacterList().get(i).getTank()), true);
        }
    }


    /**
     * Damages the tanks hit and plays the explosion animation where the projectile hit.
     *
     * @param projectile the projectile that hit something.
     */

    //TODO refactor this projectile hit method, seems like it's doing more than one thing.
    void projectileHit(Shootable projectile) {
        List<Tank> deadTanks = new ArrayList<Tank>();
        for (Tank t : tanks.keySet()) {
            tankRevolution.damage(projectile, t, distanceTo(t, projectile));
            if (!t.isAlive()) {
                deadTanks.add(t);
            }
        }
        explosions.add(new Explosion(getProjectilePosition(projectile), projectile.getBlastRadius()));
        destroyProjectile(projectile);
        for (Tank t : deadTanks) {
            explosions.add(new Explosion(getTankPosition(t), 25));
            destroyTank(t);
        }
    }

    /**
     * Removes the body of the projectile from the world.
     * @param projectile the projectile that will be destroyed.
     */
    private void destroyProjectile(Shootable projectile) {
        removeStack.add(projectiles.get(projectile));
        projectiles.remove(projectile);
        tankRevolution.destroyProjectile(projectile);
        isTerrainChanged = true;

        if (projectiles.isEmpty()) {
            tankRevolution.doNextMove();
        }
    }

    /**
     * Removes the body of the tank from the world.
     * @param tank the tank that will be destroyed.
     */
    private void destroyTank(Tank tank) {
        removeStack.add(tanks.get(tank));
        tanks.remove(tank);
        tankRevolution.destroyTank(tank);
    }

    /**
     * @param tank the actual tank.
     * @param projectile the actual projectile.
     * @return The distance between the tank and the projectile.
     */
    private float distanceTo(Tank tank, Shootable projectile) {
        float deltaX = Math.abs(getTankX(tank) - getProjectileX(projectile));
        float deltaY = Math.abs(getTankY(tank) - getProjectileY(projectile));
        return (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    /**
     * Moves tank based on input from user.
     * @param direction recieves it from ButtonController, 1 if moving right, -1 if moving left
     */
    public void moveTank(int direction) {
        getTankBody(getCurrentTank()).setLinearVelocity(direction * 25, Constants.getGravity());
        tankRevolution.reduceFuel();
    }

    /**
     * Called when the move buttons are released.
     */
    public void stopTank() {
        for (int i = 0; i < tanks.size(); i++) {
            if (!getCharacterList().get(i).getTank().isTankFalling()) {
                tanks.get(getCharacterList().get(i).getTank()).setLinearVelocity(0, 0);
            }else{
                tanks.get(getCharacterList().get(i).getTank()).setLinearVelocity(0, Constants.getGravity());
            }
        }
    }

    /**
     * @return true if there is nothing stopping the tank from moving.
     */
    public boolean tankCanMove() {
        return tankRevolution.tankCanMove() && !getCurrentTank().isTankFalling();
    }

    /**
     * @param screenX x-coordinate of the point where the user released touch.
     * @param screenY y-coordinate of the point where the user released touch.
     * @param touchX  x-coordinate of the user's initial touch.
     * @param touchY  y-coordinate of the user's initial touch.
     */
    public void shoot(int screenX, int screenY, float touchX, float touchY) {
        shoot(Constants.metersPerPixel() * (touchX - screenX), Constants.metersPerPixel() * (screenY - touchY));
    }

    /**
     * Fires a projectile in a direction based on either the user's input or a calculation for the NPC.
     *
     * @param deltaX distance x-wise between user's initial input and where touch was let go.
     * @param deltaY distance y-wise between user's initial input and where touch was let go.
     */

    private void shoot(float deltaX, float deltaY) {
        Tank shooter = getCurrentTank();
        List<Shootable> projectiles = tankRevolution.shoot(deltaX, deltaY);
        for (Shootable s : projectiles) {
            addProjectile(s, shooter, deltaX, deltaY);
        }
    }

    /**
     * Calculates the perfect shot and puts a random fault based on difficulty.
     */

    private void NPCDoShoot() {
        List<Tank> tanksToNPC = new ArrayList<Tank>();
        tanksToNPC.addAll(tanks.keySet());
        List<Point> positionsToNPC = new ArrayList<Point>();
        for (Tank t : tanksToNPC) {
            float positionX = getTankX(t);
            float positionY = getTankY(t);
            positionsToNPC.add(new Point(positionX, positionY));
        }
        NPC NPC = (NPC) tankRevolution.getCurrentCharacter();
        Vector vector = NPC.getShootVector(tanksToNPC, positionsToNPC);
        shoot(vector.getDeltaX(), vector.getDeltaY());
    }

    public List<float[]> getVertices() {
        return terrainHandler.getVertices();
    }

    private boolean NPCWillShoot() {
        return tankRevolution.NPCWillShoot();
    }

    private float getTankX(Tank tank) {
        return tanks.get(tank).getPosition().x;
    }

    private float getTankY(Tank tank) {
        return tanks.get(tank).getPosition().y;
    }

    private Vector2 getTankPosition(Tank tank){
        return new Vector2(getTankX(tank), getTankY(tank));
    }

    public Shootable getProjectile(Body body) {
        for (Shootable s : projectiles.keySet()) {
            if (projectiles.get(s).equals(body)) {
                return s;
            }
        }
        return null;
    }

    private float getProjectileX(Shootable projectile) {
        return projectiles.get(projectile).getPosition().x;
    }

    private float getProjectileY(Shootable projectile) {
        return projectiles.get(projectile).getPosition().y;
    }

    private Vector2 getProjectilePosition(Shootable projectile){
        return new Vector2(getProjectileX(projectile), getProjectileY(projectile));
    }

    public List<Character> getCharacterList() {
        return tankRevolution.getCharacterList();
    }

    public List<Shootable> getFlyingProjectiles() {
        return tankRevolution.getFlyingProjectiles();
    }

    public boolean isProjectileFlying() {
        return tankRevolution.isProjectileFlying();
    }

    public Tank getCurrentTank() {
        return tankRevolution.getCurrentTank();
    }

    public boolean isInputAllowed() {
        return tankRevolution.isInputAllowed();
    }

    private void createContactListener() {
        world.setContactListener(new EnvironmentCollisions(this, terrainHandler));
    }

    public AmmunitionType getCurrentWeapon() {
        return getCurrentTank().getCurrentWeapon();
    }

    public void setNextWeapon() {
        getCurrentTank().setNextWeapon();
    }

    public void setPreviousWeapon() {
        getCurrentTank().setPreviousWeapon();
    }

    private Vector2 getStartingPosition(int i){
        return new Vector2(5 + (i - 1)* ((Constants.getMapWidth() - 10) / (getCharacterList().size()-1)),
                Constants.getTankStartPositionY());
    }

    private Vector2 getStartingPosition(Id id) {
        switch (id) {
            case PLAYER1:
                return getStartingPosition(1);

            case PLAYER2:
                return getStartingPosition(2);

            case PLAYER3:
                return getStartingPosition(3);

            case PLAYER4:
                return getStartingPosition(4);

            default:
                System.out.println("Invalid Id");
                return new Vector2(0, 0);
        }
    }

}