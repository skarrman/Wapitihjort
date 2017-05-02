package tank_revolution.model;

import tank_revolution.Utils.TankObserver;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;
import tank_revolution.model.ShootablePackage.SmallMissile;
import java.awt.*;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class Tank {

    private int health;
    private float fuel;
    private final float width = 3f;
    private final float height = 2f;
    private float deltaX;
    private float deltaY;
    private Integer angle = 0;
    private boolean alive = true;
    private TankObserver observer;

    //Gives the tank a mass of 600kg
    private final float density = 100;

    //smallMissile = 0
    private int currentProjectile;


    public Tank(int health, int fuel, TankObserver observer) {
        currentProjectile = 0;
        this.health = health;
        this.fuel = fuel;
        this.observer = observer;
    }

    public Tank(TankObserver observer){
        this(100, 100, observer);
    }

    public Shootable shoot(float deltaX, float deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        angle = (int)Math.toDegrees(Math.tan(deltaY/deltaX));
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
            kill();
        }
    }

    private void kill(){
        observer.actOnDeath(this);
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

    public void drive(int direction) {  }

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

    public float getDeltaX(){
        return deltaX;
    }

    public float getDeltaY(){
        return deltaY;
    }

    public Integer getAngle(){
        return angle;
    }

    public boolean hasFuel() {
        return (fuel > 0);

    }

}
