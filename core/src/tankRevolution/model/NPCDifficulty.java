package tankRevolution.model;

/**
 * Created by jakobwall on 2017-05-11.
 */
public enum NPCDifficulty {
    EASY(0.4f, 0.8f, 0.95f),
    MEDIUM(0.3f, 0.6f, 0.90f),
    HARD(0.05f, 0.3f, 0.85f),
    SUPERHARD(0f, 0f, 0.1f);

    private final float badLimit;
    private final float goodLimit;
    private final float perfectLimit;

    NPCDifficulty(float badLimit, float goodLimit, float perfectLimit){
        this.badLimit = badLimit;
        this.goodLimit = goodLimit;
        this.perfectLimit = perfectLimit;
    }

    public float getBadLimit() {
        return badLimit;
    }

    public float getGoodLimit() {
        return goodLimit;
    }

    public float getPerfectLimit() {
        return perfectLimit;
    }
}
