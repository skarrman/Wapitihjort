package tankRevolution.framework;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.quailshillstudio.polygonClippingUtils.UserData;
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
     * The tanks in the world and their body.
     */
    private Map<Tank, Body> tanks;

    /**
     * The Projectiles in the world and their Body.
     */
    private Map<Shootable, Body> projectiles;
    
    /**
     * List of bodies pending to be destroyed
     */
    private List<Body> removeStack;

    /**
     * List of current explosions
     */
    private List<Explosion> explosions;

    private ITerrainHandler terrainHandler;

    /**
     * Creates a new Environment for Bodys to live in.
     */
    public Environment( TankRevolution tankRevolution) {
        this.tankRevolution = tankRevolution;
        tanks = new HashMap<Tank, Body>();
        projectiles = new HashMap<Shootable, Body>();
        removeStack = new ArrayList<Body>();
        setupWorld();
        createContactListener();
        this.explosions = new ArrayList<Explosion>();
        InitializeTank();
    }

    /**
     * Sets up the world with gravity and terrain with two sides.
     */
    private void setupWorld() {
        //The gravity force is connected to the world.
        Vector2 g = new Vector2(0f, Constants.getGravity());
        world = new World(g, true);
        terrainHandler = new TerrainHandler(world);
        //setTerrain(3f);
        setupSides();
    }

    /**
     * Sets up sides.
     */
    private void setupSides() {
        Body leftSide = setupSide(0);
        Body rightSide = setupSide(Constants.getMapWidth() + 0);
    }

    /**
     * @param n the x coordinate of the side being created.
     * @return a body of the side.
     */
    private Body setupSide(float n) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef3 = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0, 0);

        EdgeShape wall = new EdgeShape();
        wall.set(n, 0, n, Constants.getMapWidth() * 2);
        fixtureDef3.shape = wall;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef3);
        body.setUserData(new UserData(UserData.BALL));
        wall.dispose();
        return body;
    }

    /**
     * @param projectile the projectile from the model.
     * @param shooter    the tank shooting the projectile.
     */
    public void addProjectile(Shootable projectile, Tank shooter, float deltaX, float deltaY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        float x = tanks.get(shooter).getPosition().x;
        float y = tanks.get(shooter).getPosition().y;

        //TODO Fix this, since box2D is working with the center coordinates, the missile will be fired from the center of the tank
        bodyDef.position.set(x, y + 3);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectile.getMissileRadius(), projectile.getMissileRadius());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = projectile.getMissileDensity();

        body.createFixture(fixtureDef);
        shape.dispose();

        //Translation will be needed, this vector will suck
        Vector2 force = new Vector2(deltaX, deltaY);
        body.setLinearVelocity(force/*, body.getPosition(), true*/);
        body.setUserData(new UserData(1));

        projectiles.put(projectile, body);
    }

    /**
     * Places the body of a tank at a specified location based on what player owns it.
     * @param tank the tank object from the model being created.
     */
    public void addTank(Tank tank, Id id) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (id == Id.PLAYER1) {
            bodyDef.position.set(5f, 20f);
        } else if (id == Id.PLAYER2) {
            bodyDef.position.set(Constants.getMapWidth() - 5f, 20f);
        }
        body = world.createBody(bodyDef);
        body.setUserData(new UserData(2));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(tank.getWidth() / 2, tank.getHeight() / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = tank.getDensity();
        body.createFixture(fixtureDef);
        shape.dispose();

        tanks.put(tank, body);
    }

    //TODO create different terrains.
    private void setTerrain(float y) {
        Body terrain;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0, 0);
        FixtureDef fixtureDef3 = new FixtureDef();

        EdgeShape ground = new EdgeShape();
        ground.set(-2, y, Constants.getMapWidth() + 2, y);
        fixtureDef3.shape = ground;

        terrain = world.createBody(bodyDef);
        terrain.createFixture(fixtureDef3);
        ground.dispose();
    }

    private void InitializeTank() {
        for (Character c : tankRevolution.getCharacterList()) {
            addTank(c.getTank(), c.getId());
        }
    }

    public World getWorld() {
        return world;
    }

    public Body getTankBody(Tank tank) {
        return tanks.get(tank);
    }

    public Body getProjectileBody(Shootable s){
        return projectiles.get(s);
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public void dispose() {
        world.dispose();
    }

    /**
     * Moves the world forward one step and updates everything accordingly. called 60 times/second.
     */
    public void update() {
        stackUpdate();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        terrainHandler.update();
        if (NPCWillShoot()) {
            NPCDoShoot();
        }
    }

    public void stackUpdate() {
        if (!world.isLocked() && !removeStack.isEmpty()) {
            world.destroyBody(removeStack.get(removeStack.size() - 1));
            removeStack.remove(removeStack.size() - 1);
        }
    }

    public boolean isLocked() {
        return world.isLocked();
    }


    /**
     * Damages the tanks hit and plays the explosion animation where the projectile hit.
     * @param projectile the projectile that hit something.
     */
    void projectileHit(Shootable projectile) {
        List<Tank> deadTanks = new ArrayList<Tank>();
        for (Tank t : tanks.keySet()) {
            tankRevolution.damage(projectile, t, distanceTo(t, projectile));
            if (!t.isAlive()) {
                deadTanks.add(t);
            }
            System.out.println(t.getHealth());
        }
        explosions.add(new Explosion(projectiles.get(projectile).getPosition().x, projectiles.get(projectile).getPosition().y,
                projectile.getBlastRadius()));
        destroyProjectile(projectile);

        for (Tank t : deadTanks) {
            explosions.add(new Explosion(tanks.get(t).getPosition().x, tanks.get(t).getPosition().y, 10));
            destroyTank(t);
        }
    }

    /**
     * Removes the body of the projectile fram the environment.
     *
     * @param projectile the projectile that will be destroyed.
     */
    public void destroyProjectile(Shootable projectile) {
        removeStack.add(projectiles.get(projectile));
        projectiles.remove(projectile);
        tankRevolution.destroyProjectile(projectile);
        if (projectiles.isEmpty()) {
            tankRevolution.doNextMove();
        }
    }

    /**
     * Removes the body of the tank from the environment.
     *
     * @param tank the tank that will be destroyed.
     */
    public void destroyTank(Tank tank) {
        removeStack.add(tanks.get(tank));
        tanks.remove(tank);
        tankRevolution.destroyTank(tank);
    }

    /**
     *
     * @param tank
     * @param projectile
     * @return  The distance between the tank and the projectile.
     */
    public float distanceTo(Tank tank, Shootable projectile) {
        float deltaX = Math.abs(getTankX(tank) - getProjectileX(projectile));
        float deltaY = Math.abs(getTankY(tank) - getProjectileY(projectile));
        return (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    /**
     * Moves tank based on input from user.
     * @param direction recieves it from ButtonController, 1 if moving right, -1 if moving left
     */
    public void moveTank(int direction) {
        getTankBody(tankRevolution.getCurrentTank()).setLinearVelocity(direction * 10, 0);
        tankRevolution.reduceFuel();
    }

    /**
     * @return true if there is nothing stopping the tank from moving.
     */
    public boolean tankCanMove(){
        return tankRevolution.tankCanMove();
    }

    /**
     * Called when the move buttons are released.
     */
    public void stopTank() {
        getTankBody(tankRevolution.getCurrentTank()).setLinearVelocity(0, -10);
    }

    /**
     * @param screenX x-coordinate of the point where the user released touch.
     * @param screenY y-coordinate of the point where the user released touch.
     * @param touchX  x-coordinate of the user's initial touch.
     * @param touchY  y-coordinate of the user's initial touch.
     */
    public void shoot(int screenX, int screenY, float touchX, float touchY) {
        shoot(Constants.metersPerPixel() * (touchX - screenX),Constants.metersPerPixel() * (screenY - touchY));
    }

    /**
     * Fires a projectile in a direction based on either the user's input or a calculation for the NPC.
     * @param deltaX distance x-wise between user's initial input and where touch was let go.
     * @param deltaY distance y-wise between user's initial input and where touch was let go.
     */
    public void shoot(float deltaX, float deltaY){
        Tank shooter = tankRevolution.getCurrentTank();
        List<Shootable> projectiles = tankRevolution.shoot(deltaX, deltaY);
        for (Shootable s : projectiles) {
            addProjectile(s, shooter,  deltaX, deltaY);
        }
    }

    /**
     * Calculates the perfect shot and puts a random fault based on difficulty.
     */
    public void NPCDoShoot(){
        List<Tank> tanksToNPC = new ArrayList<Tank>();
        tanksToNPC.addAll(tanks.keySet());
        List<Point> positionsToNPC = new ArrayList<Point>();
        for (Tank t: tanksToNPC){
            float positionX = getTankX(t);
            float positionY = getTankY(t);
            positionsToNPC.add(new Point(positionX, positionY));
        }
        NPC NPC = (NPC) tankRevolution.getCurrentCharacter();
        Vector vector = NPC.getShootVector(tanksToNPC, positionsToNPC);
        shoot(vector.getDeltaX(), vector.getDeltaY());
    }

    private boolean NPCWillShoot() {
        return tankRevolution.NPCWillShoot();
    }

    public float getTankX(Tank tank) {
        return tanks.get(tank).getPosition().x;
    }

    public float getTankY(Tank tank) {
        return tanks.get(tank).getPosition().y;
    }

    public Shootable getProjectile(Body body) {
        for (Shootable s : projectiles.keySet()) {
            if (projectiles.get(s).equals(body)) {
                return s;
            }
        }
        System.out.println("getProjectile nulls");
        return null;
    }

    public float getProjectileX(Shootable projectile) {
        return projectiles.get(projectile).getPosition().x;
    }

    public float getProjectileY(Shootable projectile) {
        return projectiles.get(projectile).getPosition().y;
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


}