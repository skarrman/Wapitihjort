package tank_revolution.framework;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.javafx.geom.Edge;
import tank_revolution.model.Explosion;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.Tank;

/**
 * Created by jakobwall on 2017-04-06.
 */
public class Environment {

    private float mapWidth;
    private World world;
    private Map<Tank, Body> tanks;
    private Map<Shootable, Body> projectiles;
    private Body terrain;
    private Body leftSide;
    private Body rightSide;

    private List<ContactObserver> contactObservers = new ArrayList<ContactObserver>();
    private List<NextMoveObserver> nextMoveObservers = new ArrayList<NextMoveObserver>();


    public Environment(float mapWidth){
        this.mapWidth = mapWidth;
        tanks = new HashMap<Tank, Body>();
        projectiles = new HashMap<Shootable, Body>();
        setupWorld();
        createContactListener();
    }

    private void setupWorld(){
        //The gravity force is connected to the world.
        Vector2 g = new Vector2(0f, -10f);
        world = new World(g, true);

        setTerrain(3f);
        setupSides();
    }

    private void setupSides(){
        leftSide = setupSide(-1f);
        rightSide = setupSide(mapWidth + 1);
    }

    private Body setupSide(float n){
        Body body;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef3 = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0,0);

        EdgeShape wall = new EdgeShape();
        wall.set(n,0,n,mapWidth*2);
        fixtureDef3.shape = wall;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef3);
        wall.dispose();
        return body;
    }

    public void addProjectile(Shootable projectile, float deltaX, float deltaY, Tank shooter){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        float x = tanks.get(shooter).getPosition().x;
        float y = tanks.get(shooter).getPosition().y;

        //TODO Fix this, since box2D is working with the center coordinates, the missile will be fired from the center of the tank
        bodyDef.position.set(x, y+3);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectile.getMissileRadius(), projectile.getMissileRadius());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = projectile.getMissileDensity();

        body.createFixture(fixtureDef);
        shape.dispose();

        //Translation will be needed, this vector will suck
        Vector2 force = new Vector2(deltaX*50, deltaY*50);
        body.applyForceToCenter(force,true);

        projectiles.put(projectile, body);
    }

    public void addTank(Tank tank) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(tank.getStartX(), tank.getStartY());

        body = world.createBody(bodyDef);
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
    public void setTerrain(float y){
        Body terrain;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0, 0);
        FixtureDef fixtureDef3 = new FixtureDef();

        EdgeShape ground = new EdgeShape();
        ground.set(-2, y, mapWidth+2, y);
        fixtureDef3.shape = ground;

        terrain = world.createBody(bodyDef);
        terrain.createFixture(fixtureDef3);
        ground.dispose();
    }

    public float getMapWidth(){
        return mapWidth;
    }

    public World getWorld(){
        return world;
    }

    public Body getTank(Tank tank){
        return tanks.get(tank);
    }

    public void dispose() {
        world.dispose();
    }

    public void update(){
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public boolean isLocked(){
        return world.isLocked();
    }

    public void destroyProjectile(Shootable projectile){
        world.destroyBody(projectiles.get(projectile));
        projectiles.remove(projectile);
        if(projectiles.isEmpty()){
            notifyNextMoveObservers();
        }
    }

    public float distanceTo(Tank tank, Shootable projectile){
        float deltaX = getTankX(tank)-getProjectileX(projectile);
        float deltaY = getTankY(tank)-getProjectileY(projectile);
        return (float) Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
    }

    public float getTankX(Tank tank){
        return tanks.get(tank).getPosition().x;
    }

    public float getTankY(Tank tank){
        return tanks.get(tank).getPosition().y;
    }

    public float getProjectileX(Shootable projectile){
        return projectiles.get(projectile).getPosition().x;
    }

    public float getProjectileY(Shootable projectile){
        return projectiles.get(projectile).getPosition().y;
    }

    public void addNextMoveObserver(NextMoveObserver observer){
        nextMoveObservers.add(observer);
    }

    private void notifyNextMoveObservers(){
        for(int i = 0; i < nextMoveObservers.size(); i++) {
            nextMoveObservers.get(i).doNextMove();
        }
    }

    public void addContactObserver(ContactObserver observer){
        contactObservers.add(observer);
    }

    private void createContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (projectiles.containsValue(contact.getFixtureA().getBody())){
                    notifyContactObservers(contact.getFixtureA().getBody().getPosition().x,
                            contact.getFixtureA().getBody().getPosition().y);
                }else if(projectiles.containsValue(contact.getFixtureB().getBody())){
                    notifyContactObservers(contact.getFixtureB().getBody().getPosition().x,
                            contact.getFixtureB().getBody().getPosition().y);
                }
            }
            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private void notifyContactObservers(float x, float y){
       for(int i = 0; i < contactObservers.size(); i++) {
           contactObservers.get(i).actOnContact(x, y);
       }
    }

}
