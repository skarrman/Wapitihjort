package tankRevolution.model;

import tankRevolution.utils.Id;
import tankRevolution.utils.Point;
import tankRevolution.utils.Vector;

import java.awt.geom.Point2D;
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
        Vector vector = new Vector(-200, 300);
        return vector;
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
