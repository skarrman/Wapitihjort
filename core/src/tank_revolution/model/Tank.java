package tank_revolution.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.ShootablePackage.SmallMissile;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank {

    private float health;
    private Body body;
    private float fuel;
    private final float width = 3f;
    private final float height = 2f;

    //Gives the tank a mass of 600kg
    private final float density = 100;

    //smallMissile = 0
    private int currentProjectile;


    Tank(World world, float startX, float startY) {
        initTank(world, startX, startY);
        currentProjectile = 0;
        health = 100;
        fuel = 100;
    }

    private void initTank(World world, float startX, float startY){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startX, startY);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef);
        shape.dispose();

    }

    public Shootable shoot(float deltaX, float deltaY) {
        return ProjectileFactory.shootSmallMissile(deltaX, deltaY, body.getPosition().x, body.getPosition().y);
    }

    public Body getBody() {
        return body;
    }

    public int getCurrentProjectile() {
        return currentProjectile;
    }

    public void setHealth(float n) {
        health = n;
    }

    public float getHealth() {
        return health;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float n) {
        fuel = n;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void drive(int direction) {

        body.setLinearVelocity(direction, 0);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDensity() {
        return density;
    }

    public boolean hasFuel() {
        return (fuel > 0);

    }

}
