package tank_revolution.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import tank_revolution.model.ShootablePackage.Projectile;
import tank_revolution.model.ShootablePackage.ProjectileFactory;
import tank_revolution.model.ShootablePackage.Shootable;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameSession {

    //All these values are in meter and affects all tanks
    private final float tankWidth = 3f;
    private final float tankHeight = 2f;
    private final float mapWidth = 50f;

    //Gives the tank a mass of 600kg
    private final float tankDensity = 100;

    //The gravity in the world
    private Vector2 g = new Vector2(0f, -10f);
    // The world
    private World world;
    private List<Character> characterList;

    private Body flyingProjectile;


    //The index of the current character in characterList
    private int characterTurn = 0;

    /**
     * Creates a new gameSession from a list of characters and gives the characters tanks and addes the tanks to the world.
     * (max four characters)
     * Note that this is the first iteration and is only made for two characters
     *
     * @param characterList
     */

    public GameSession(List<Character> characterList) {
        this.characterList = characterList;

        gameSessionSetup(characterList);


    }

    private void gameSessionSetup(List<Character> characterList){
        world = new World(g, true);
        ProjectileFactory.setWorld(world);

        for (int i = 0; i < characterList.size(); i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;

            if (i == 0)
                bodyDef.position.set(5f, 7f);

            else if (i == 1)
                bodyDef.position.set(mapWidth - 5f, 7f);

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

            Body body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(tankWidth / 2, tankHeight / 2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = tankDensity;

            body.createFixture(fixtureDef);
            shape.dispose();

            Tank tank = new Tank(body);
            characterList.get(i).setTank(tank);
        }
        Body terrain;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(0,0);
        FixtureDef fixtureDef3 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0,3,50,3);
        fixtureDef3.shape = edgeShape;


        terrain = world.createBody(bodyDef);
        terrain.createFixture(fixtureDef3);
        edgeShape.dispose();
    }

    public void shoot(float deltaX, float deltaY){
        flyingProjectile = characterList.get(characterTurn).getTank().shoot(deltaX/100, deltaY/100);
    }

    private void endTurn(){
        characterTurn = (characterTurn++)%characterList.size();
    }

    public int getCharacterTurn() {
        return characterTurn;
    }

    public boolean isProjectileFlying(){
        return flyingProjectile != null;
    }

    public Vector2 getProjectilePosision(){
        return flyingProjectile.getPosition();
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void update(){
        world.step(1/60f, 6, 2);
    }
}
