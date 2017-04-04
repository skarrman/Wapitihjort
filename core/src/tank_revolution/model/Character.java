package tank_revolution.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>A character is a holder of a tank. </p>
 * <p>Characters is kept between games while tanks aren't </p>
 * <p>A character is controlled of either a human or a ai </p>
 */

public abstract class Character {
    private Tank tank;
    private final String name;
    private final boolean isNPC;

    /**
     * Standard constructor for characters
     * @param name The name of the character
     * @param isNPC If the character is a npc or a player
     */
    Character(String name, boolean isNPC){
        this.name = name;
        this.isNPC = isNPC;
    }

    /**
     * Sets the tank
     * @param tank
     */
    public void setTank(Tank tank) {
        this.tank = tank;
    }

    /**
     * gets the tank
     * @return
     */
    public Tank getTank(){
        return tank;
    }

    /**
     * gets the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * when a turn is ended, this method will be called for the character which turn who's turn it is.
     */
    public abstract void play();

    /**
     * returns whether or not the current character is a npc or not
     * @return
     */
    public boolean isNPC(){
        return isNPC;
    }


}
