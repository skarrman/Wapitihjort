package tankRevolution.model;

import tankRevolution.services.Id;

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
        getTank().resetFuel();
    }
}
