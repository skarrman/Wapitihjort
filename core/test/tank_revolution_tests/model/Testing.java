package tank_revolution_tests.model;


import org.junit.Test;
import tank_revolution.model.GameSession;

import static org.junit.Assert.*;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

public class Testing {

    int iterations = 10;
    int times = 1000000;

    @Test public void ArrayListTesting() {
        ArrayListTest arrayListTest = new ArrayListTest(iterations);
        long time = System.nanoTime();
        for(int i = 0; i < times; i ++) {
            arrayListTest.add();
            arrayListTest.remove();
        }
        long timeDif = System.nanoTime() - time;

        System.out.println("ArrayList = " + timeDif);
    }

    @Test public void LinkedListTesting() {
        LinkedListTest linkedListTest = new LinkedListTest(iterations);
        long time = System.nanoTime();
        for(int i = 0; i < times; i ++) {
            linkedListTest.add();
            linkedListTest.remove();
        }
        long timeDif = System.nanoTime() - time;

        System.out.println("LinkedList = " + timeDif);
    }

    @Test public void StackTesting() {
        StackTest StackTest = new StackTest(iterations);
        long time = System.nanoTime();
        for(int i = 0; i < times; i ++) {
            StackTest.add();
            StackTest.remove();
        }
        long timeDif = System.nanoTime() - time;

        System.out.println("Stack = " + timeDif);
    }

}