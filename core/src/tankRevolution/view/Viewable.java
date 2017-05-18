package tankRevolution.view;

/**
 * Created by simonkarrman on 2017-05-04.
 * General interface for all View-classes.
 */
public interface Viewable {
    /**
     * Called 60 times/second to update the view about what should be drawn.
     */
    void update();

    /**
     * Disposes all disposable items. Since C++ has no garbage collection.
     */
    void dispose();
}
