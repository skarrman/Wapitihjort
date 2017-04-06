package tank_revolution.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tank_revolution.Utils.Observable;
import tank_revolution.Utils.Observer;
import tank_revolution.framework.Environment;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameSession implements Observable {

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
        gameSessionSetup();
        createContactListener();
    }

    private void gameSessionSetup() {

        setIsActive();

        for (int i = 0; i < characterList.size(); i++) {

            if (i == 0) {
                Tank tank = new Tank(world, 5f, 7f);
                characterList.get(0).setTank(tank);
            }

            else if (i == 1) {
                Tank tank = new Tank(world, mapWidth - 5f, 7f);
                characterList.get(1).setTank(tank);
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
        if (isActive) {
            flyingProjectile = characterList.get(characterTurn).getTank().shoot(deltaX / 20, deltaY / 20);
            isActive = false;
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

    private void endTurn() {
        if (characterTurn == characterList.size()-1){
            characterTurn = 0;
        }
        else{
            characterTurn = characterTurn + 1;
        }
        setIsActive();
    }

    public int getCharacterTurn() {
        return characterTurn;
    }

    public boolean isProjectileFlying() {
        return flyingProjectile != null;
    }

    public Vector2 getProjectilePosision() {
        return flyingProjectile.getBody().getPosition();
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void update() {
        if (projectileHasHit)
            destroyProjectile();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void destroyProjectile() {
        if (!world.isLocked()) {
            world.destroyBody(flyingProjectile.getBody());
            flyingProjectile = null;
            projectileHasHit = false;
        }
    }

    public float getMapWidth() {
        return mapWidth;
    }

    public void setProjectileHasHit(boolean projectileHasHit) {
        this.projectileHasHit = projectileHasHit;
    }

    public void setIsActive(){
        if(!characterList.get(characterTurn).isNPC()){
            isActive = true;
        }else{
            isActive = false;
        }
    }

    public void setIsActive(boolean b){
        isActive = b;
    }


    private int calculateDamage(int damage, float distance, float blastRadius){
        return (int) ((blastRadius - distance) / blastRadius * damage);
    }

    private void projectileImpacted(){
        for(Character character : characterList){
            Tank tank = character.getTank();
            float distance = tank.distanceTo(flyingProjectile.getBody().getPosition());
            if(distance < flyingProjectile.getBlastRadius()){
                tank.reduceHealth(calculateDamage(flyingProjectile.getDamage(), distance, flyingProjectile.getBlastRadius()));
            }
            System.out.println(tank.getHealth());
        }
    }

    private void createContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(isProjectileFlying()) {
                    if (contact.getFixtureA().getBody().equals(flyingProjectile.getBody())
                            || contact.getFixtureB().getBody().equals(flyingProjectile.getBody())) {
                        projectileHasHit = true;
                        explosions.add(new Explosion(flyingProjectile.getBody().getPosition().x,
                                flyingProjectile.getBody().getPosition().y,
                                flyingProjectile.getBlastRadius()));
                        projectileImpacted();
                    }
                }
                endTurn();

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    //Observer handling

    List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Vector2 position, int value) {
        for(Observer o : observers){
            o.actOnChange(position, value);
        }
    }
}
