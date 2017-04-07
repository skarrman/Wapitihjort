package tank_revolution.model;

import com.badlogic.gdx.math.Vector2;
import tank_revolution.Utils.Observable;
import tank_revolution.Utils.Observer;
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
public class GameSession implements ContactObserver, NextMoveObserver {

    //All these values are in meter and affects all tanks
    private final float mapWidth = 50f;

    private List<Character> characterList;

    private Shootable flyingProjectile;

    private List<Explosion> explosions;

    //The index of the current character in characterList
    private int characterTurn = 0;

    private boolean projectileHasHit = false;

    private boolean isActive;

    private Environment environment;

    /**
     * Creates a new gameSession from a list of characters and gives the characters tanks and addes the tanks to the world.
     * (max four characters)
     * Note that this is the first iteration and is only made for two characters
     *
     * @param characterList
     */

    public GameSession(List<Character> characterList) {
        this.characterList = characterList;
        environment = new Environment(mapWidth);
        environment.addContactObserver(this);
        environment.addNextMoveObserver(this);
        explosions = new ArrayList<Explosion>();
        gameSessionSetup();
    }

    private void gameSessionSetup() {

        setIsActive();

        for (int i = 0; i < characterList.size(); i++) {

            if (i == 0) {
                Tank tank = new Tank( 5f, 7f);
                characterList.get(0).setTank(tank);
                environment.addTank(tank);
            }

            else if (i == 1) {
                Tank tank = new Tank( mapWidth - 5f, 7f);
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
            flyingProjectile = characterList.get(characterTurn).getTank().shoot();
            environment.addProjectile(flyingProjectile, deltaX, deltaY, characterList.get(characterTurn).getTank());
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
    public boolean moveTank(int direction) {
        Tank tank = characterList.get(characterTurn).getTank();
        if (tank.hasFuel()) {
            tank.setFuel(tank.getFuel() - 1);
            if (tankCanMove()) {
                tank.drive(direction);
                return true;
            }

        }
        return false;
    }

    /**
     * @return true if the terrain allows the tank to move
     */
    public boolean tankCanMove() {
        return true;
    }

    private void setNextCharacter() {
        if (characterTurn == characterList.size()-1){
            characterTurn = 0;
        }
        else{
            characterTurn = characterTurn + 1;
        }
    }

    public int getCharacterTurn() {
        return characterTurn;
    }

    public boolean isProjectileFlying() {
        return flyingProjectile != null;
    }

    public float getProjectileX() {
        return environment.getProjectileX(flyingProjectile);
    }

    public float getProjectileY() {
        return environment.getProjectileY(flyingProjectile);
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void update() {
        if (projectileHasHit)
            destroyProjectile();

        environment.update();
    }

    private void destroyProjectile() {
        if (!environment.isLocked()) {
            environment.destroyProjectile(flyingProjectile);
            projectileHasHit = false;
        }
    }

    public float getMapWidth() {
        return mapWidth;
    }

    public Environment getEnvironment(){
        return environment;
    }

    public void setProjectileHasHit(boolean projectileHasHit) {
        this.projectileHasHit = projectileHasHit;
    }

    public void setIsActive(){
        if(!getCurrentCharacter().isNPC()){
            isActive = true;
        }else{
            isActive = false;
        }
    }

    public void setIsActive(boolean b){
        isActive = b;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    private int calculateDamage(int damage, float distance, float blastRadius){
        return (int) ((blastRadius - distance) / blastRadius * damage);
    }

    private void projectileImpacted(){
        for(Character character : characterList){
            Tank tank = character.getTank();
            float distance = environment.distanceTo(tank, flyingProjectile);
            if(distance < flyingProjectile.getBlastRadius()){
                tank.reduceHealth(calculateDamage(flyingProjectile.getDamage(), distance, flyingProjectile.getBlastRadius()));
            }
            System.out.println(tank.getHealth());
        }
    }

    public void actOnContact(float x, float y){
        projectileHasHit = true;
        explosions.add(new Explosion(x,
                y,
                flyingProjectile.getBlastRadius()));
        //TODO find the blastradius from somewhere else.
        projectileImpacted();
        //TODO end turn should not be here for when we have multiple projectiles.
    }
}
