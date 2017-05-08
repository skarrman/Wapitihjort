package tankRevolution.utils;

/**
 * Created by jakobwall on 2017-05-08.
 */
public class Vector {
    private float deltaX;
    private float deltaY;

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
