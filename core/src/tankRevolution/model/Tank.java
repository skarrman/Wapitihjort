package tankRevolution.model;

import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.model.shootablePackage.ProjectileFactory;
import tankRevolution.model.shootablePackage.Shootable;

/**
 * Created by antonhagermalm on 2017-03-30.
 * the logic representation of tanks
 */
public class Tank {

    private Integer angle = 0;
    private boolean isFalling;
    private AmmunitionType ammunitionType;
    /**
     * Tank specs
     */
        //Gives the tank a mass of 600kg
    private final float density = 100;
    private final float width = 3f;
    private final float height = 2f;
    private final float maxFuel = 100f;
    private int health = 100;
    private final int enginePower = 100;
    private float fuel = 100;

    public Tank() {
        this.ammunitionType = AmmunitionType.SMALL_MISSILE;
        isFalling = true;
    }

    Shootable shoot(float deltaX, float deltaY) {
        angle = (int) Math.toDegrees(Math.tan(deltaY / deltaX));
        return ProjectileFactory.create(ammunitionType);
    }

    void reduceHealth(int damage) {
        health = health - damage;
    }

    public void setNextWeapon() {
        ammunitionType = AmmunitionType.getNext(ammunitionType);
    }

    public void setPreviousWeapon() {
        ammunitionType = AmmunitionType.getPrevious(ammunitionType);
    }

    public AmmunitionType getCurrentWeapon() {
        return ammunitionType;
    }

    public float getHealth() {
        return health;
    }

    public float getFuel() {
        return fuel;
    }

    public int getEnginePower(){
        return enginePower;
    }

    void reduceFuel() {
        fuel = fuel - 0.5f;
    }

    void resetFuel() {
        fuel = maxFuel;
    }

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

    public Integer getAngle() {
        return angle;
    }

    boolean hasFuel() {
        return (fuel > 0);

    }

    public boolean isTankFalling() {
        return isFalling;
    }

    public void setTankFalling(boolean b) {
        isFalling = b;
    }

}
