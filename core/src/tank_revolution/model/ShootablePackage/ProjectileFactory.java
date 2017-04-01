package tank_revolution.model.ShootablePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by antonhagermalm on 2017-03-31.
 */
public class ProjectileFactory {

    private static float missileRadius = 0.5f;
    private static float missileDensity = 100f;
    private static World world;

    static void shootSmallMissile(float deltaX, float deltaY, float tankX, float tankY){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //Fix this, since box2D is working with the center coordinates, these coordinates need to be changed
        bodyDef.position.set(tankX, tankY);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setRadius(missileRadius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = missileDensity;

        body.createFixture(fixtureDef);
        shape.dispose();

        Vector2 force = new Vector2(deltaX, deltaY);
        body.applyForceToCenter(force, true);


    }

    public static void setWorld(World inputWorld){
        world = inputWorld;
    }
}
