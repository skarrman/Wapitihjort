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
public class NPC extends Character {

    private NPCDifficulty difficulty;
    private Random rand;

    NPC(Id id, NPCDifficulty difficulty) {
        super(id, true);
        this.difficulty = difficulty;
        rand = new Random();
    }

    NPC(NPC npc) {
        super(npc);
        this.difficulty = npc.difficulty;
        rand = new Random();
    }

    public Vector getShootVector(List<Tank> tanks, List<Point> positions) {
        int ownTankPlace = getOwnTankPlace(tanks);
        Point ownPoint = positions.get(ownTankPlace);
        int opponentPlace = getRandomPlace(ownTankPlace, tanks.size());
        Point opponentPoint = positions.get(opponentPlace);
        Vector vector;
        float random = (float) Math.random();
        if (random < this.difficulty.getBadLimit()) {
            vector = getBadVector(ownPoint, opponentPoint);
        } else if (random < this.difficulty.getGoodLimit()) {
            vector = getNormalVector(ownPoint, opponentPoint);
        } else if (random < this.difficulty.getPerfectLimit()) {
            vector = getGoodVector(ownPoint, opponentPoint);
        } else if (random <= 1) {
            vector = getPerfectVector(ownPoint, opponentPoint);
        } else {
            System.out.println("No Difficulty set for NPC.");
            vector = calculateIdealVector(ownPoint, opponentPoint);
        }
        return vector;
    }

    private int getOwnTankPlace(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            if (this.getTank() == tanks.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private int getRandomPlace(int exceptionalPlace, int highest) {
        int number = -1;
        while (number == -1 || number == exceptionalPlace) {
            number = rand.nextInt(highest);
        }
        return number;
    }

    public Vector calculateIdealVector(Point own, Point opponent) {
        float deltaX = opponent.getX() - own.getX();
        float deltaY = opponent.getY() - (own.getY() + Constants.getShootOffsetTank());
        float minYVelocity = 0;
        if (deltaY > 0) {
            minYVelocity = Math.abs(Constants.getGravity() * (float) (Math.sqrt(Math.abs(deltaY * 2 / Constants.getGravity()))));
        }
        float extraForceY = rand.nextInt(30) + 10;
        float VelocityY = minYVelocity + extraForceY;
        float timeInAir = (float) ((VelocityY / -Constants.getGravity()) + (Math.sqrt((VelocityY / Constants.getGravity()) * (VelocityY / Constants.getGravity())
                - (2 * deltaY) / (-Constants.getGravity()))));
        return new Vector(deltaX / timeInAir, VelocityY);
    }

    private Vector getBadVector(Point own, Point opponent) {
        float diff = 20 + rand.nextInt(30);
        return getVectorWithDiff(own, opponent, diff);
    }

    private Vector getNormalVector(Point own, Point opponent) {
        float diff = 10 + rand.nextInt(10);
        return getVectorWithDiff(own, opponent, diff);
    }

    private Vector getGoodVector(Point own, Point opponent) {
        float diff = 5 + rand.nextInt(2);
        return getVectorWithDiff(own, opponent, diff);
    }

    private Vector getPerfectVector(Point own, Point opponent) {
        float diff = 0;
        return getVectorWithDiff(own, opponent, diff);
    }

    private Vector getVectorWithDiff(Point own, Point opponent, float diff) {
        if (rand.nextBoolean())
            opponent = new Point(opponent.getX() + diff, opponent.getY());
        else
            opponent = new Point(opponent.getX() - diff, opponent.getY());

        return calculateIdealVector(own, opponent);
    }

    public void setNewTurn() {
        getTank().resetFuel();
    }

}
