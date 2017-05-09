package tankRevolution.model;

import tankRevolution.utils.Id;
import tankRevolution.utils.Point;
import tankRevolution.utils.Vector;
import java.util.List;
import java.util.Random;

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
        int opponentPlace = getRandomPlace(ownTankPlace, tanks.size());
        Point opponentPoint = positions.get(opponentPlace);
        Vector vector = calculateVector(ownPoint, opponentPoint);
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

    private int getRandomPlace(int exceptionalPlace, int highest){
        Random rand = new Random();
        int number = -1;
        while(number == -1 || number == exceptionalPlace){
            number = rand.nextInt(highest);
        }
        return number;
    }

    private Vector calculateVector(Point own, Point opponent){
        return new Vector(-100, 100);
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
