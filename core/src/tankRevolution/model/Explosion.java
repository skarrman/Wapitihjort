package tankRevolution.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by antonhagermalm on 2017-04-06.
 * The logic representation of a explosion
 */
public class Explosion {

    public float x;
    public float y;
    public int blastRadius;

    public Explosion(float x, float y, int blastRadius){
    this.x = x;
    this.y = y;
    this.blastRadius = blastRadius;
    }

    public Explosion(Vector2 v, int blastRadius){
        this(v.x, v.y, blastRadius);
    }
}
