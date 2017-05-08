package tankRevolution.framework;

import com.badlogic.gdx.physics.box2d.*;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tankRevolution.model.shootablePackage.Shootable;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public class EnvironmentCollisions{ /*implements ContactListener{

    Environment environment;

    EnvironmentCollisions(Environment environment){
        this.environment = environment;
    }

    /**
     * Called when two fixtures begin to touch.
     *
     * @param contact
     */
  /*  @Override
    public void beginContact(Contact contact) {

    }

    /**
     * Called when two fixtures cease to touch.
     *
     * @param contact
     */
   /* @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        UserData dataA = (UserData) a.getUserData();
        UserData dataB = (UserData) b.getUserData();

//NOTE: BALL = a object that cant clip or be clipped
        if (dataA.getType() == UserData.BOMB && (dataB.getType() == UserData.GROUND || dataB.getType() == UserData.BALL)) {
            Shootable projectile = getProjectile(a);
            //terrainHandler.explode(a, projectile.getBlastRadius());
            environment.terrainHandler.explode(a, 3);
            projectileHit(projectile);
        } else if (dataB.getType() == UserData.BOMB && (dataA.getType() == UserData.GROUND || dataA.getType() == UserData.BALL)) {
            Shootable projectile = getProjectile(b);
            //terrainHandler.explode(b, projectile.getBlastRadius());
            terrainHandler.clippingGround(b, 3);
            projectileHit(projectile);
        }


    }

    private Shootable getProjectile(Body body) {
        for (Shootable s : projectiles.keySet()) {
            if (projectiles.get(s).equals(body)) {
                return s;
            }
        }
        System.out.println("getProjectile nulls");
        return null;
    }*/
}

