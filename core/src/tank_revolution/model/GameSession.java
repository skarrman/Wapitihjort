package tank_revolution.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameSession {

    float tankWidth = 3f;
    float tankHeight = 1f;
    float mapWidth = 300f;

    //Gives the tank a mass of 300kg
    float tankDensity = 100;

    Vector2 g = new Vector2(0f, -10f);
    World world;

    /**
     * Creates a new gameSession from a list of characters. (max four)
     * Note that this is the first iteration and is only made for two characters
     * @param characterList
     */

    GameSession(List<Character> characterList) {
        world = new World(g, true);

        for (int i = 0; i < characterList.size(); i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;

            if (i == 0)
                bodyDef.position.set(15f, 5f);

            else if (i == 1)
                bodyDef.position.set(mapWidth - 15f, 5f);

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

            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            fixtureDef.shape = shape;
            fixtureDef.density = tankDensity;

            body.createFixture(fixtureDef);
            shape.dispose();

            Tank tank = new Tank(body);
            characterList.get(i).setTank(tank);
        }


    }


}
