package tank_revolution.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * Created by antonhagermalm on 2017-03-30.
 * A character is a holder of a tank.
 * Characters is kept between games while tanks aren't
 * A character is controlled of either a human or a ai
 */
public abstract class Character {
    private Tank tank;
    private final String name;
    private final boolean isNPC;

    Character(String name, boolean isNPC){
        this.name = name;
        this.isNPC = isNPC;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank(){
        return tank;
    }

    public String getName() {
        return name;
    }

    public abstract void play();

    public boolean isNPC(){
        return isNPC;
    }


}
