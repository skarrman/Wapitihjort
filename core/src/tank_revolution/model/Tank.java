package tank_revolution.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.SmallMissile;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank{

    private float health;
    private Body body;
    private int fuel;

    //smallMissile = 0
    private int currentProjectile;


    Tank(Body body){
        this.body = body;
        //Will be changed
        currentProjectile = 0;
        health = 100;
        fuel = 100;
    }

    public Body shoot(float deltaX, float deltaY){
        return ProjectileFactory.shootSmallMissile(deltaX, deltaY, body.getPosition().x, body.getPosition().y);
    }

    public Body getBody() {
        return body;
    }

    public int getCurrentProjectile() {
        return currentProjectile;
    }

    public float getHealth(){
        return health;
    }

    public void drive(){
        //Move body,
        //Lower fuel level.
    }
}
