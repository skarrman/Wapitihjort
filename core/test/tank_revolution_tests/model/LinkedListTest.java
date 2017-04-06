package tank_revolution_tests.model;

import tank_revolution.model.Explosion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class LinkedListTest {
    List<Explosion> list;

    LinkedListTest() {
        list = new LinkedList<Explosion>();
    }

    public void add() {
        for (int i = 0; i < 1000; i++) {
            list.add(new Explosion(10, 10, 10));
        }
    }

    public void remove() {
        for (int i = 0; i < 1000; i++) {
            list.remove(0);
        }
    }

}