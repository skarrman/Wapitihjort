package tankRevolution.framework;


import com.badlogic.gdx.physics.box2d.*;
import tankRevolution.model.*;
import tankRevolution.model.Character;
import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.model.shootablePackage.Shootable;
import java.util.List;


/**
 * Created by simonkarrman on 2017-05-25.
 */
public interface IEnvironment {

    void pauseGame();

    /**
     * Returns the world of the game.
     *
     * @return the world of the game.
     */
    World getWorld();

    /**
     * Returns the body connected to a tank.
     *
     * @param tank the logical model of the tank.
     * @return The body och that tank.
     */
    Body getTankBody(Tank tank);

    /**
     * Returns the body connected to a projectile.
     *
     * @param s The logical model of the projectile.
     * @return The body och that tank.
     */
    Body getProjectileBody(Shootable s);

    /**
     * Returns a list of current explosions.
     *
     * @return a list of current explosions.
     */
    List<Explosion> getExplosions();

    /**
     * Checks if it's only one player left alive.
     *
     * @return true if it's only one player left.
     */
    boolean gameOver();

    /**
     * Disposes the world.
     */
    void dispose();

    /**
     * Is called for the world to update the bodies within it.
     */
    void update();

    /**
     * TODO
     */
    boolean isTerrainChanged();


    /**
     * TODO JAKOB ERLANDSSON?
     * @param body
     * @param isFalling
     */
    void setTankFalling(Body body, boolean isFalling);


    /**
     * Damages the tanks hit and plays the explosion animation where the projectile hit.
     *
     * @param projectile the projectile that hit something.
     */

    //TODO refactor this projectile hit method, seems like it's doing more than one thing.
    void projectileHit(Shootable projectile);


    /**
     * Moves tank based on input from user. Multiplies the direction by the tank's engine power to make the tank move at a good pace.
     * @param direction 1 if moving right, -1 if moving left.
     */
    void moveTank(int direction);

    /**
     * Stops the current playing tank.
     */
    void stopTank();


    /**
     * TODO JAKOBERLANDSSON, IS THIS STILL NEEDED?
     * @return true if there is nothing stopping the tank from moving.
     */
    boolean tankCanMove();

    /**
     * Shoots a projectile.
     * @param screenX x-coordinate of the point where the user released touch.
     * @param screenY y-coordinate of the point where the user released touch.
     * @param touchX  x-coordinate of the user's initial touch.
     * @param touchY  y-coordinate of the user's initial touch.
     */
    void shoot(int screenX, int screenY, float touchX, float touchY);


    /**
     * TODO
     * @return the vertices that makes up the terrain
     */
    List<float[]> getVertices();


    /**
     * Get teh projectile from a body.
     * @param body the body of a projectile.
     * @return the projectile with that body.
     */
    Shootable getProjectile(Body body);


    /**
     * Get the character list of the current game.
     * @return the current character list.
     */
    List<Character> getCharacterList();

    /**
     * Get the list of currently flying projectiles.
     * @return A list of flying projectiles.
     */
    List<Shootable> getFlyingProjectiles();

    /**
     * Tells if a projectile is flying.
     * @return true if a projectile is flying.
     */
    boolean isProjectileFlying();

    /**
     * Get the current tank.
     * @return the current tank.
     */
    Tank getCurrentTank();

    /**
     * Tells if input from the user is allowed.
     * @return true is input is allowed.
     */
    boolean isInputAllowed();

    /**
     * Get the current weapon being used in the game.
     * @return the current weapon.
     */
    AmmunitionType getCurrentWeapon();


    /**
     * Set the weapon of the tank to the next in the list of weapons.
     */
    void setNextWeapon();


    /**
     * Set the weapon of the tank to the previous in the list of weapons.
     */
    void setPreviousWeapon();
}
