package tankRevolution.model;

import tankRevolution.utils.Id;
import tankRevolution.utils.Point;
import tankRevolution.utils.Vector;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class NPC extends Character{

    private int difficulty;

    NPC(Id id) {
        super(id, true);;
    }

    NPC(NPC npc){
        super(npc);
        this.difficulty = npc.difficulty;
    }

    public Vector getShootVector(List<Tank> tanks, List<Point> positions){
        int ownTankPlace = getOwnTankPlace(tanks);
        Point ownPoint = positions.get(ownTankPlace);
        

        Vector vector = new Vector(-200, 300);
        return vector;
    }

    private int getOwnTankPlace(List<Tank> tanks){
        for (int i = 0; i < tanks.size(); i++){
            if(this.getTank() == tanks.get(i)){
                return i;
            }
        }
        return -1;
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
