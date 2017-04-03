package tank_revolution.model.ShootablePackage;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * The abstract class for projectiles, includes the fields blastRadius..
 */
public abstract class Projectile implements Shootable{

    protected static float missileRadius;
    protected static float missileDensity;
    protected static World world;

    Projectile (float deltaX, float deltaY, float tankX, float tankY){
        projectileSetup(deltaX, deltaY, tankX, tankY);

    }

    protected void projectileSetup(float deltaX, float deltaY, float tankX, float tankY){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //Fix this, since box2D is working with the center coordinates, the missile will be fired from the center of the tank
        bodyDef.position.set(tankX, tankY);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setRadius(missileRadius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = missileDensity;

        body.createFixture(fixtureDef);
        shape.dispose();

        //Translation will be needed, this vector will suck
        Vector2 force = new Vector2(deltaX, deltaY);
        body.applyForceToCenter(force, true);

    }


    protected float blastRadius;

    /**
     * @return blastRadius of the projectile
     */
    protected float getBlastRadius(){
        return blastRadius;
    }
}
