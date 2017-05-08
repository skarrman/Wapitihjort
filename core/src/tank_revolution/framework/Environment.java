package tank_revolution.framework;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tank_revolution.Utils.Constants;
import tank_revolution.Utils.Id;
import tank_revolution.framework.terrain.ITerrainHandler;
import tank_revolution.model.Character;
import tank_revolution.model.Explosion;
import tank_revolution.model.GameSession;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.Tank;
import tank_revolution.framework.terrain.TerrainHandler;

/**
 * Created by jakobwall on 2017-04-06.
 */
public class Environment {

    private GameSession gameSession;

    /**
     * The map width of the playing field
     */
    private float mapWidth;

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
     * The terrain of the world.
     */
    private Body terrain;

    /**
     * The left wall of the world.
     */
    private Body leftSide;

    /**
     * The right wall of the world
     */
    private Body rightSide;

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
     *
     * @param mapWidth is the width of the gameboard.
     */
    public Environment(float mapWidth, GameSession gameSession) {
        this.mapWidth = mapWidth;
        this.gameSession = gameSession;
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
        Vector2 g = new Vector2(0f, -10f);
        world = new World(g, true);
        terrainHandler = new TerrainHandler(world);
        //setTerrain(3f);
        TerrainHandler terrainHandler = new TerrainHandler(world);
        setupSides();
    }

    /**
     * Sets up sides.
     */
    private void setupSides() {
        leftSide = setupSide(-1f);
        rightSide = setupSide(mapWidth + 1);
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
        wall.set(n, 0, n, mapWidth * 2);
        fixtureDef3.shape = wall;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef3);
        wall.dispose();
        return body;
    }

    /**
     * @param projectile the projectile from the model.
     * @param shooter    the tank shooting the projectile.
     */
    public void addProjectile(Shootable projectile, Tank shooter) {
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
        Vector2 force = new Vector2(shooter.getDeltaX() * 50, shooter.getDeltaY() * 50);
        body.applyForceToCenter(force, true);
        body.setUserData(new UserData(1));

        projectiles.put(projectile, body);
    }

    /**
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
        ground.set(-2, y, mapWidth + 2, y);
        fixtureDef3.shape = ground;

        terrain = world.createBody(bodyDef);
        terrain.createFixture(fixtureDef3);
        ground.dispose();
    }

    private void InitializeTank() {
        for (Character c : gameSession.getCharacterList()) {
            addTank(c.getTank(), c.getId());
        }
    }

    public float getMapWidth() {
        return mapWidth;
    }

    public World getWorld() {
        return world;
    }

    public Body getTankBody(Tank tank) {
        return tanks.get(tank);
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public void dispose() {
        world.dispose();
    }

    public void update() {
        stackUpdate();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        terrainHandler.update();
        if(NPCWillShoot()){
            //TODO logic for calculating the shoot.
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


    private void projectileHit(Shootable projectile) {
        List<Tank> deadTanks = new ArrayList<Tank>();
        for (Tank t : tanks.keySet()) {
            gameSession.damage(projectile, t, distanceTo(t, projectile));
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
        //TODO check if any tank died and act on it
    }

    /**
     * Removes the body of the projectile fram the environment.
     *
     * @param projectile the projectile that will be destroyed.
     */
    public void destroyProjectile(Shootable projectile) {
        removeStack.add(projectiles.get(projectile));
        projectiles.remove(projectile);
        gameSession.destroyProjectile(projectile);
        if (projectiles.isEmpty()) {
            gameSession.doNextMove();
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
        gameSession.destroyTank(tank);
    }

    public float distanceTo(Tank tank, Shootable projectile) {
        float deltaX = Math.abs(getTankX(tank) - getProjectileX(projectile));
        float deltaY = Math.abs(getTankY(tank) - getProjectileY(projectile));
        return (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public void moveTank(int direction) {
        getTankBody(gameSession.getCurrentTank()).setLinearVelocity(direction * 50, 0);
    }

    public void stopTank() {
        getTankBody(gameSession.getCurrentTank()).setLinearVelocity(0, 0);
    }

    public void shoot(int screenX, int screenY, float touchX, float touchY) {
        Tank shooter = gameSession.getCurrentTank();
        List<Shootable> projectiles = gameSession.shoot(touchX - screenX, screenY - touchY);
        for (Shootable s : projectiles) {
            addProjectile(s, shooter);
        }
    }

    private boolean NPCWillShoot(){
        return gameSession.NPCWillShoot();
    }

    public float getTankX(Tank tank) {
        return tanks.get(tank).getPosition().x;
    }

    public float getTankY(Tank tank) {
        return tanks.get(tank).getPosition().y;
    }

    public float getProjectileX(Shootable projectile) {
        return projectiles.get(projectile).getPosition().x;
    }

    public float getProjectileY(Shootable projectile) {
        return projectiles.get(projectile).getPosition().y;
    }

    public List<Character> getCharacterList(){
        return gameSession.getCharacterList();
    }

    public List<Shootable> getFlyingProjectiles(){
        return gameSession.getFlyingProjectiles();
    }

    public boolean isProjectileFlying(){
        return gameSession.isProjectileFlying();
    }

    public Tank getCurrentTank(){
        return gameSession.getCurrentTank();
    }

    public boolean isInputAllowed(){
        return gameSession.isInputAllowed();
    }

    private void createContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                /*if (projectiles.containsValue(contact.getFixtureA().getBody())){
                    for(Shootable s: projectiles.keySet()){
                        if(projectiles.get(s).equals(contact.getFixtureA().getBody())){
                            //projectileHit(s);
                        }
                    }
                }else if(projectiles.containsValue(contact.getFixtureB().getBody())){
                    for(Shootable s: projectiles.keySet()){
                        if(projectiles.get(s).equals(contact.getFixtureB().getBody())){
                            //projectileHit(s);
                        }
                    }

                }*/
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if((((UserData)(contact.getFixtureA().getBody().getUserData())).getType() == UserData.GROUND &&
                        ((UserData)(contact.getFixtureB().getBody().getUserData())).getType() == UserData.BOMB)||
                        ((UserData)(contact.getFixtureA().getBody().getUserData())).getType() == UserData.BOMB &&
                                ((UserData)(contact.getFixtureB().getBody().getUserData())).getType() == UserData.GROUND) {
                    Shootable projectile;
                    UserData dataA = (UserData) a.getUserData();
                    UserData dataB = (UserData) b.getUserData();

                    if (projectiles.containsValue(a)) {
                        for (Shootable s : projectiles.keySet()) {
                            if (projectiles.get(s).equals(a)) {
                                projectile = s;
                                //projectileHit(s);
                            }
                        }
                    } else if (projectiles.containsValue(b)) {
                        for (Shootable s : projectiles.keySet()) {
                            if (projectiles.get(s).equals(b)) {
                                projectile = s;
                                //projectileHit(s);
                            }
                        }

                    }
                    System.out.println("PostSolve");

                    //add userData to projectile
                    if (dataA instanceof UserData && dataA.getType() == UserData.GROUND && dataB instanceof UserData && dataB.getType() == UserData.BOMB) {
                        terrainHandler.clippingGround(a, b, dataA);

                    } else if (dataB instanceof UserData && dataB.getType() == UserData.GROUND && dataA instanceof UserData && dataA.getType() == UserData.BOMB) {
                        terrainHandler.clippingGround(b, a, dataB);
                    }
                }
    //projectileHit(projectile);

                //clipped = false;
            }
        });
    }
}
