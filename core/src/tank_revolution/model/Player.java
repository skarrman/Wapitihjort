package tank_revolution.model;

/**
 * {@inheritDoc}
 */
public class Player extends Character{

    Player(String name) {
        super(name, false);
    }

    Player(Player player){
        super(player);
    }

    public void setNewTurn(){
//TODO logic for what happens when player has new turn.
    }
}
