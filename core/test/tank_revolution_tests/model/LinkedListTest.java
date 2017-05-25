package tank_revolution_tests.model;

import tankRevolution.model.Explosion;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class LinkedListTest {
    List<Explosion> list;
    //Iterator<Explosion> iterator;
    int iterations;
    LinkedListTest(int iterations) {
        list = new LinkedList<>();
        this.iterations = iterations;
    }

    public void add() {
        for (int i = 0; i < iterations; i++) {
            list.add(new Explosion(10, 10, 10));
        }
    }

    public void remove() {
        for (int i = 0; i < iterations; i++) {
            list.remove(0);
        }
    }

}