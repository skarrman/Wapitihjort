package tank_revolution.model;

import com.badlogic.gdx.math.Vector2;
import tank_revolution.Utils.Observable;
import tank_revolution.Utils.Observer;
import tank_revolution.Utils.TankObserver;
import tank_revolution.framework.ContactObserver;
import tank_revolution.framework.Environment;
import tank_revolution.framework.NextMoveObserver;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameSession implements ContactObserver, NextMoveObserver, TankObserver{

    /** The width of the map in meters */
    private final float mapWidth = 50f;

    /** list of the playing character */
    private List<Character> characterList;

    /** The projectile, will hold the projectile in air */
    private Shootable flyingProjectile;

    /** List of current explosions */
    private List<Explosion> explosions;

    /** The index of the current playing character */
    private int characterTurn = 0;

    /** if the projectile has hit something and will be removed */
    private boolean projectileHasHit = false;

    /** true if the current character is a player, false if npc */
    private boolean isActive;
    /**

    /**
     * Creates a new gameSession from a list of characters and gives the characters tanks and addes the tanks to the world.
     * (max four characters)
     * Note that this is the first iteration and is only made for two characters
     *
     * @param characterList
     */

    public GameSession(List<Character> characterList) {
        this.characterList = characterList;
        explosions = new ArrayList<Explosion>();
        gameSessionSetup();
    }

    private void gameSessionSetup() {

        setIsActive();

        for (int i = 0; i < characterList.size(); i++) {

            if (i == 0) {
                Tank tank = new Tank( 5f, 7f, this);
                characterList.get(0).setTank(tank);
                environment.addTank(tank);
            }

            else if (i == 1) {
                Tank tank = new Tank( mapWidth - 5f, 7f, this);
                characterList.get(1).setTank(tank);
                environment.addTank(tank);
            }

            /*else if (i == 2) {
                if (characterList.size() == 3) {
                    bodyDef.position.set(mapWidth/2f, 5f);
                }
                else {

                }
            }
            else if (i == 4)
                    bodyDef.position.set(mapWidth/2f, 5f);
               */

        }
    }

    public void shoot(float deltaX, float deltaY) {
        if (isActive || getCurrentCharacter().isNPC()) {
            flyingProjectile = getCurrentTank().shoot(deltaX, deltaY);
            environment.addProjectile(flyingProjectile, getCurrentTank());
            isActive = false;
        }
    }

    public Character getCurrentCharacter(){
        return characterList.get(characterTurn);
    }

    public Tank getCurrentTank(){
        return getCurrentCharacter().getTank();
    }

    public void doNextMove(){
        setNextCharacter();
        getCurrentCharacter().setNewTurn();
        flyingProjectile = null;
        if(getCurrentCharacter().isNPC()){
            shoot(-470, 500);
        }else{
            setIsActive();
        }
    }

    /**
     * If the tank can move, gives the it a linear velocity depending on which way we want to move it.
     * <p>Returns a boolean to make it easier to test</p>
     *
     * @param direction -1 if left button is pressed, 1 if right button is pressed.
     */
    public void moveTank(int direction) {
        System.out.print(direction);
        Tank tank = characterList.get(characterTurn).getTank();
        if (tank.hasFuel()) {
            tank.setFuel(tank.getFuel() - 1);
            if (tankCanMove()) {
                environment.getTank(tank).setLinearVelocity(direction*50,0);
            }
        }
    }

    public void stopTank(){
        Tank tank = characterList.get(characterTurn).getTank();
        environment.getTank(tank).setLinearVelocity(0,0);
    }

    /**
     * @return true if the terrain allows the tank to move
     */
    public boolean tankCanMove() {
        return true;
    }

    /**
     * ends the turn of the current playing character and gives the turn too the next player
     */
    private void setNextCharacter() {
        characterTurn = (characterTurn+1)%characterList.size();
    }

    /**
     * @return the index of the current playing character
     */
    public int getCharacterTurn() {
        return characterTurn;
    }

    /**
     * @return true if the projectile is currently in the air
     */
    public boolean isProjectileFlying() {
        return flyingProjectile != null;
    }

    /**
     * @return the x-coordinate of the projectile
     */
    public float getProjectileX() {
        return environment.getProjectileX(flyingProjectile);
    }

    /**
     * @return the y-coordinate of the projectile
     */
    public float getProjectileY() {
        return environment.getProjectileY(flyingProjectile);
    }

    /**
     * @return the list of the characters
     */
    public List<Character> getCharacterList() {
        return characterList;
    }

    /**
     * updating the model and tells the environment to update
     */
    public void update() {
        if (projectileHasHit) {
            destroyProjectile();
        }
        environment.update();

    }

    /**
     * safely removes a projectile
     */
    private void destroyProjectile() {
        if (!environment.isLocked()) {
            environment.destroyProjectile(flyingProjectile);
            projectileHasHit = false;
        }
    }

    /**
     * @return the mapWidth
     */
    public float getMapWidth() {
        return mapWidth;
    }

    /**
     * @return the environment
     */
    public Environment getEnvironment(){
        return environment;
    }

    /**
     * sets that the current character is a player
     */
    public void setIsActive(){
        if(!getCurrentCharacter().isNPC()){
            isActive = true;
        }else{
            isActive = false;
        }
    }

    /**
     * @return the list of explosions
     */
    public List<Explosion> getExplosions() {
        return explosions;
    }

    /**
     * calculates the damage that will be inflicted on a tank
     * @param damage the starting damage of the projectile
     * @param distance the distance from the tanks center to the hitdown
     * @param blastRadius the projectiles blast radius
     * @return the damage inflicted to the tank
     */
    private int calculateDamage(int damage, float distance, float blastRadius){
        return (int) ((blastRadius - distance) / blastRadius * damage);
    }

    /**
     * called when a projectile hits something
     * @param x the x-coordinate of the impact
     * @param y the y-coordinate of the impact
     */
    private void projectileImpacted(float x, float y){
        projectileHasHit = true;

        //TODO find the blastradius from somewhere else.
        explosions.add(new Explosion(x, y, flyingProjectile.getBlastRadius()));

        for(int i = 0; i < characterList.size(); i++){
            Tank tank = characterList.get(i).getTank();
            float distance = environment.distanceTo(tan k, flyingProjectile);
            if(distance < flyingProjectile.getBlastRadius()){
                tank.reduceHealth(calculateDamage(flyingProjectile.getDamage(), distance, flyingProjectile.getBlastRadius()));
            }
            System.out.println(tank.getHealth());
        }
    }

    /**
     * this method is called when a object collides with another
     * @param x the x-coordinate of the collision
     * @param y the y-coordinate of the collision
     */
    public void actOnContact(float x, float y){
        projectileImpacted(x, y);
    }

    public void actOnDeath(Tank tank) {
        /* if bodies doesnt disappear, it's because this if statment is only ran once, to fix this put it in update
        maybe make a list of bodies to be removed and in update remove them and make the removeMethod in environment
        generic */

        for(int i = 0; i < characterList.size(); i++){
            if (characterList.get(i).getTank() == tank) {
                characterList.remove(i);
                setNextCharacter();
            }
        }
        explosions.add(new Explosion(environment.getTankX(tank), environment.getTankY(tank), 10));
        environment.destroyTank(tank);

        //TODO look in the character list for the character with this tank and remove it. Make sure to have defencive copying from model
    }
}
