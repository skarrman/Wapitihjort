package tankRevolution.model;

import tankRevolution.services.Constants;
import tankRevolution.services.Id;
import tankRevolution.utils.Point;
import tankRevolution.utils.Vector;

import java.util.List;
import java.util.Random;

/**
 * {@inheritDoc}
 */
public class NPC extends Character {

    /**
     * The difficulty of the NPC.
     */
    private final NPCDifficulty difficulty;

    /**
     * The randomizer being used of the NPC.
     */
    private final Random rand;

    /**
     * Creates a new NPC.
     * @param id The id of the character.
     * @param difficulty The difficulty of the NPC.
     */
    NPC(Id id, NPCDifficulty difficulty) {
        super(id, true);
        this.difficulty = difficulty;
        rand = new Random();
    }

    /**
     * Creates a new NPC.
     * @param npc the NPC that is being copied.
     */
    NPC(NPC npc) {
        super(npc);
        this.difficulty = npc.difficulty;
        rand = new Random();
    }

    /**
     * Gives a shoot-vector depending on enemies position and the difficulty of the NPC.
     * @param tanks a list of current tanks.
     * @param positions a list of the tanks' positions.
     * @return the vector to shoot at an opponent's tank.
     */
    public Vector getShootVector(List<Tank> tanks, List<Point> positions) {
        int ownTankPlace = getOwnTankPlace(tanks);
        Point ownPoint = positions.get(ownTankPlace);
        int opponentPlace = getRandomNumber(ownTankPlace, tanks.size());
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

    /**
     * Gives the place of the NPC's tank in a list of tanks.
     * @param tanks the list of tanks.
     * @return the position of the tank, -1 if there was no match.
     */
    private int getOwnTankPlace(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            if (this.getTank() == tanks.get(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calculates a random number from 0 to a limit
     * @param exceptionalNumber a number that can't be chosen.
     * @param highest the high limit of the random place.
     * @return a random number.
     */
    private int getRandomNumber(int exceptionalNumber, int highest) {
        int number = -1;
        while (number == -1 || number == exceptionalNumber) {
            number = rand.nextInt(highest);
        }
        return number;
    }

    /**
     * Calculates the perfect shoot-vector given two points.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @return a vector that shoots from own to opponent.
     */
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

    /**
     * Calculates a bad vector.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @return a vector that shoots from own to opponent.
     */
    private Vector getBadVector(Point own, Point opponent) {
        float diff = 20 + rand.nextInt(30);
        return getVectorWithDiff(own, opponent, diff);
    }

    /**
     * Calculates a normal/okay vector.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @return a vector that shoots from own to opponent.
     */
    private Vector getNormalVector(Point own, Point opponent) {
        float diff = 10 + rand.nextInt(10);
        return getVectorWithDiff(own, opponent, diff);
    }

    /**
     * Calculates a good vector.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @return a vector that shoots from own to opponent.
     */
    private Vector getGoodVector(Point own, Point opponent) {
        float diff = 5 + rand.nextInt(2);
        return getVectorWithDiff(own, opponent, diff);
    }

    /**
     * Calculates a perfect vector.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @return a vector that shoots from own to opponent.
     */
    private Vector getPerfectVector(Point own, Point opponent) {
        float diff = 0;
        return getVectorWithDiff(own, opponent, diff);
    }

    /**
     * Calculates a vector with a diff.
     * @param own the point from where to shoot.
     * @param opponent the point to hit.
     * @param diff the diff of the shoot.
     * @return a vector that differs from the perfect vector with the given diff.
     */
    private Vector getVectorWithDiff(Point own, Point opponent, float diff) {
        if (rand.nextBoolean())
            opponent = new Point(opponent.getX() + diff, opponent.getY());
        else
            opponent = new Point(opponent.getX() - diff, opponent.getY());

        return calculateIdealVector(own, opponent);
    }

    /**
     * Reset the character for a new turn.
     */
    public void setNewTurn() {
        getTank().resetFuel();
    }

}
