package tank_revolution.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.ShootablePackage.SmallMissile;
import java.awt.*;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank {

    private float startX;
    private float startY;
    private int health;
    private float fuel;
    private final float width = 3f;
    private final float height = 2f;
    private boolean alive = true;

    //Gives the tank a mass of 600kg
    private final float density = 100;

    //smallMissile = 0
    private int currentProjectile;


    public Tank(float startX, float startY, int health, int fuel) {
        this.startX = startX;
        this.startY = startY;
        currentProjectile = 0;
        this.health = health;
        this.fuel = fuel;
    }

    public Tank(float startX, float startY){
        this(startX, startY, 100, 100);
    }

    public Shootable shoot() {
        return ProjectileFactory.shootSmallMissile();
    }

    public int getCurrentProjectile() {
        return currentProjectile;
    }

    public void setHealth(int n) {
        health = n;
    }

    public void reduceHealth(int damage) {
        health = health - damage;
        if (health < 0){
            alive = false;
        }
    }

    public float getHealth() {
        return health;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float n) {
        fuel = n;
    }

    public void drive(int direction) { }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDensity() {
        return density;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public boolean hasFuel() {
        return (fuel > 0);

    }

}
