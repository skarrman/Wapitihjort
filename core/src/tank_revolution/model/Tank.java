package tank_revolution.model;

import com.badlogic.gdx.physics.box2d.Body;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.SmallMissilePackage.*;
import tank_revolution.model.ShootablePackage.SmallMissilePackage.SmallMissile;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank{

    private float health;
    private Body body;

    //This will be further developed later
    private Projectile currentProjectile;


    Tank(Body body){
        this.body = body;
        //Will be changed
        currentProjectile = new SmallMissile();
        health = 100;
    }

    public void shoot(float deltaX, float deltaY){


        //new Vector2(de)
    }

    public Body getBody() {
        return body;
    }

    public Projectile getCurrentProjectile() {
        return currentProjectile;
    }

    public float getHealth(){
        return health;
    }
}
