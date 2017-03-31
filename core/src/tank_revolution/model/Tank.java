package tank_revolution.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank{

    private float health;
    private Body body;


    Tank(Body body){
        this.body = body;
        health = 100;
    }

    public void shoot(float deltaX, float deltaY){
        //new Vector2(de)
    }

    public Body getBody() {
        return body;
    }

    public float getHealth(){
        return health;
    }
}
