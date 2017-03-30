package tank_revolution.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public abstract class Character {
    private Tank tank;
    private final String name;
    private Sprite sprite;

    Character(String name){
        this.name = name;
    }

    public Tank getTank(){
        return tank;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
