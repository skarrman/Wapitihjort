package tankRevolution.model;

import tankRevolution.utils.Id;

/**
 * {@inheritDoc}
 */
public class NPC extends Character{

    private int difficulty;

    NPC(Id id, int difficulty) {
        super(id, true);
        this.difficulty = difficulty;
    }

    NPC(NPC npc){
        super(npc);
        this.difficulty = npc.difficulty;
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
