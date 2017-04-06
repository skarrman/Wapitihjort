package tank_revolution.framework;

import java.util.Map;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.Tank;

/**
 * Created by jakobwall on 2017-04-06.
 */
public class Environment {

    private float mapWidth;
    private World world;
    private Map<Tank, Body> tanks;
    private Map<Projectile, Body> projectiles;
    private Body terrain;
    private Body leftSide;
    private Body rightSide;


    public Environment(float mapWidth){
        this.mapWidth = mapWidth;
        setupWorld();
    }

    private void setupWorld(){
        //The gravity force is connected to the world.
        Vector2 g = new Vector2(0f, -10f);
        world = new World(g, true);

        ProjectileFactory.setWorld(world); //TODO remove this.

        setupSides();
    }

    private void setupSides(){
        leftSide = setupSide(-1f):
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

    public void addProjektile(){

    }

    public void addProjectile(){

    }

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

}
