package tankRevolution.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by simonkarrman on 2017-04-05.
 */
public interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Vector2 position, int value);

}
