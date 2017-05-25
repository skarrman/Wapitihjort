package tankRevolution.utils;

/**
 * Created by jakobwall on 2017-05-08.
 * Non-mutable version of the Point class.
 */
public class Point {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
