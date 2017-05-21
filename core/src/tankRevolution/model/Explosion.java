package tankRevolution.model;

import com.badlogic.gdx.math.Vector2;

/**
 * The logic representation of a explosion
 */
public class Explosion {

    /** The x-cordinate of the explosion */
    public final float x;

    /** The y-coordinate of the explosion */
    public final float y;

    /** The blast radius of the explosion */
    public final int blastRadius;

    public Explosion(float x, float y, int blastRadius){
    this.x = x;
    this.y = y;
    this.blastRadius = blastRadius;
    }

    public Explosion(Vector2 v, int blastRadius){
        this(v.x, v.y, blastRadius);
    }
}
