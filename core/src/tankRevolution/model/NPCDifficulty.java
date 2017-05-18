package tankRevolution.model;

/**
 * Created by jakobwall on 2017-05-11.
 * the logic implementation of NPCDifficulty
 */
public enum NPCDifficulty {
    EASY(0.4f, 0.8f, 0.95f),
    MEDIUM(0.3f, 0.6f, 0.90f),
    HARD(0.05f, 0.3f, 0.85f),
    SUPERHARD(0f, 0f, 0.1f);

    /**
     * The limit for getting a bad shoot.
     */
    private final float badLimit;

    /**
     * The limit for getting a good shoot.
     */
    private final float goodLimit;

    /**
     * The limit for getting a perfect shoot.
     */
    private final float perfectLimit;

    /**
     *  Make the different Enum-Values hold different values.
     * @param badLimit the limit for a bad shoot, 0-1.
     * @param goodLimit the limit for a good shoot, 0-1.
     * @param perfectLimit the limit for a perfect shoot, 0-1.
     */
    NPCDifficulty(float badLimit, float goodLimit, float perfectLimit){
        this.badLimit = badLimit;
        this.goodLimit = goodLimit;
        this.perfectLimit = perfectLimit;
    }

    /**
     * Get the limit for a bad shoot.
     * @return the limit in percent from 0 to 1.
     */
    public float getBadLimit() {
        return badLimit;
    }

    /**
     * Get the limit for a good shoot.
     * @return the limit in percent from 0 to 1.
     */
    public float getGoodLimit() {
        return goodLimit;
    }

    /**
     * Get the limit for a perfect shoot.
     * @return the limit in percent from 0 to 1.
     */
    public float getPerfectLimit() {
        return perfectLimit;
    }
}
