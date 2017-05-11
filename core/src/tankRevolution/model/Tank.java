package tankRevolution.model;

import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.model.shootablePackage.ProjectileFactory;
import tankRevolution.model.shootablePackage.Shootable;

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
    private AmmunitionType ammunitionType;

    //Gives the tank a mass of 600kg
    private final float density = 100;



    public Tank(int health, int fuel) {
        this.health = health;
        this.fuel = fuel;
        this.ammunitionType = AmmunitionType.SMALL_MISSILE;
    }

    public Tank(){
        this(100, 100);
    }

    public Shootable shoot(float deltaX, float deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        angle = (int)Math.toDegrees(Math.tan(deltaY/deltaX));
        return ProjectileFactory.create(ammunitionType);
    }

    public void setHealth(int n) {
        health = n;
    }

    public void reduceHealth(int damage) {
        health = health - damage;
        alive = health <= 0;
    }

    public void setNextWeapon(){
        ammunitionType = AmmunitionType.getNext(ammunitionType);
    }
    public void setPreviousWeapon(){
        ammunitionType = AmmunitionType.getPrevious(ammunitionType);
    }

    public AmmunitionType getCurrentWeapon(){
        return ammunitionType;
    }

    public float getHealth() {
        return health;
    }

    public float getFuel() {
        return fuel;
    }

    public void reduceFuel(){
        fuel = fuel - 0.5f;
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
        return health >= 0;
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
