package tankRevolution.framework;

import com.badlogic.gdx.physics.box2d.*;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tankRevolution.framework.terrain.ITerrainHandler;
import tankRevolution.model.shootablePackage.Shootable;

/**
 * Created by antonhagermalm on 2017-05-04.
 * Handles collisions in the world and tells the terrainHandler if an explosion has happened.
 */
class EnvironmentCollisions implements ContactListener {

    private final IEnvironment environment;
    private final ITerrainHandler terrainHandler;

    EnvironmentCollisions(IEnvironment environment, ITerrainHandler terrainHandler) {
        this.environment = environment;
        this.terrainHandler = terrainHandler;
    }

    /**
     * Called when two fixtures begin to touch.
     *
     * @param contact the contact
     */
    @Override
    public void beginContact(Contact contact) {

    }

    /**
     * Called when two fixtures cease to touch.
     *
     * @param contact the cotact
     */
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * called after the contact
     * @param contact the contact
     * @param impulse the impulse of the impact
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        UserData dataA = (UserData) a.getUserData();
        UserData dataB = (UserData) b.getUserData();

//NOTE: BALL = a object that cant clip or be clipped
        if (dataA.getType() == UserData.BOMB && (dataB.getType() == UserData.GROUND || dataB.getType() == UserData.BALL)) {
            explode(a);
        } else if (dataB.getType() == UserData.BOMB && (dataA.getType() == UserData.GROUND || dataA.getType() == UserData.BALL)) {
            explode(b);
        } else if (dataA.getType() == UserData.BALL && dataB.getType() == UserData.GROUND){
            environment.setTankFalling(a, false);
        } else if (dataA.getType() == UserData.GROUND && dataB.getType() == UserData.BALL){
            environment.setTankFalling(b, false);
        }


    }

    /**
     * if the contact was between a projectile and a tank/ground the terrain is told to preform a polygon clipping
     * @param body the body of the projectile
     */
    private void explode(Body body){
        Shootable projectile = environment.getProjectile(body);
        if(projectile != null) {
            terrainHandler.explode(body, projectile.getBlastRadius());
            environment.projectileHit(projectile);
        }
    }
}

