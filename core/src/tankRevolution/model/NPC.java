package tankRevolution.model;

import tankRevolution.utils.Id;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;

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

    public Vector<Float> getShootVector(List<Tank> tanks, List<Point2D.Float> positions){
        Vector<Float> vector = new Vector<Float>();
        vector.set(1, 100f);
        vector.set(2, 300f);
        return vector;
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
