package tank_revolution.model.ShootablePackage;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * A projectile shot by the tank and will damage the ground and other tanks
 */
public abstract class Projectile implements Shootable {

    /** The radius of the Projectile (should cbe renamed to projectileRadius) */
    protected float missileRadius = 0.27f;
    /** The density of the Projectile (should cbe renamed to projectileDensity) */
    protected float missileDensity = 100;
    /** The radius of the destruction caused by the projectile */
    protected int blastRadius;
    /** The world the projectile lives in */
    protected World world;
    /** The body of the projectile */
    protected Body body;

    /**
     * The constructor for the projectile. The body of the projectile is created from the shooting vector given by the character (deltaX and deltaY) and a starting point
     * tankX and tankY. All the logic is in the projectileSetup method.
     * @param deltaX The speed in x-axis given by the character
     * @param deltaY The speed in y-axis given by the character
     * @param tankX The starting point in the x-axis of the projectile
     * @param tankY The starting point in the y-axis of the projectile
     * @param world The world the current gameSession lives in
     */
    protected Projectile(float deltaX, float deltaY, float tankX, float tankY, World world) {
        this.body = projectileSetup(deltaX, deltaY, tankX, tankY, world);

    }

    /**
     * The logic of the setup, see constructor
     * @param deltaX The speed in x-axis given by the character
     * @param deltaY The speed in y-axis given by the character
     * @param tankX The starting point in the x-axis of the projectile
     * @param tankY The starting point in the y-axis of the projectile
     * @param world The world the current gameSession lives in
     * @return The body of the projectile
     */
    private Body projectileSetup(float deltaX, float deltaY, float tankX, float tankY, World world) {
        this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //Fix this, since box2D is working with the center coordinates, the missile will be fired from the center of the tank
        bodyDef.position.set(tankX, tankY+3);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(missileRadius, missileRadius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = missileDensity;

        body.createFixture(fixtureDef);
        shape.dispose();

        //Translation will be needed, this vector will suck
        Vector2 force = new Vector2(deltaX*1000, deltaY*1000);
        body.applyForceToCenter(force,true);
        return body;

    }

    /**
     * @return blastRadius of the projectile
     */
    public int getBlastRadius() {
        return blastRadius;
    }

    /**
     *
     * @return the blastRadius of the projectile
     */
    public Body getBody (){
        return body;
    }
}
