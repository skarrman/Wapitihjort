package tank_revolution.model;

/**
 * {@inheritDoc}
 */
public class NPC extends Character{

    private int difficulty;

    NPC(String name, int difficulty) {
        super(name, true);
        this.difficulty = difficulty;
    }

    NPC(Character player){
        super(player);
    }

    public float getDeltaX(){
        //TODO make this calculation. (Based on enemies)
        return -500;
    }

    public float getDeltaY(){
        return 500;
        //TODO make this calculation. (Based on enemies)
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
