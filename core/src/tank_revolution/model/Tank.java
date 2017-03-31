package tank_revolution.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank{

    private Body body;

    Tank(Body body){
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}
