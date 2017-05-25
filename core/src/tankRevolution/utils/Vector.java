package tankRevolution.utils;

/**
 * Created by jakobwall on 2017-05-08.
 * Non-mutable version of the Vector class.
 */
public class Vector {
    private final float deltaX;
    private final float deltaY;

    public Vector(float deltaX, float deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }
}
