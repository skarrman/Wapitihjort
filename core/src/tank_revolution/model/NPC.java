package tank_revolution.model;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class NPC extends Character{

    private int difficulty;

    NPC(String name, int difficulty) {
        super(name, true);
        this.difficulty = difficulty;
    }

    public void play(){
        //Count the perfect shoot. manipulate random.
        //Do shoot.
    }

}
