package tank_revolution.model;

import tank_revolution.Utils.Id;

/**
 * {@inheritDoc}
 */
public class Player extends Character{

    Player(Id id) {
        super(id, false);
    }

    Player(Player player){
        super(player);
    }

    public void setNewTurn(){
//TODO logic for what happens when player has new turn.
    }
}
