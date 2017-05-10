package tankRevolution.model;

import tankRevolution.utils.Constants;
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
        Random rand = new Random();
        float deltaX = opponent.getX() - own.getX();
        float deltaY =  opponent.getY() - own.getY() + 3;

        float minYVelocity = 0;
        if(deltaY > 0){
            minYVelocity = Constants.getGravity() * (float) (Math.sqrt(Math.abs(deltaY *2 /Constants.getGravity())));
        }
        float extraForceY = rand.nextInt(30) + 10;

        float VelocityY = minYVelocity + extraForceY;

        float timeToStop = -(VelocityY/Constants.getGravity());
        float heightWhenStop = own.getY() + (timeToStop * Constants.getGravity() /2);
        System.out.println((heightWhenStop - opponent.getY())*2/Constants.getGravity());
        float timeToHit = (float) (Math.sqrt(Math.abs(((heightWhenStop - opponent.getY())*2)/Constants.getGravity())));
        float totalTime = timeToHit + timeToStop;

        return new Vector(deltaX/totalTime, VelocityY);
    }

    public void setNewTurn(){
//TODO logic for what happens when the player has new turn.
    }

}
