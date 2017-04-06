package tank_revolution_tests.model;


import org.junit.Test;
import tank_revolution.model.GameSession;

import static org.junit.Assert.*;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

public class Testing {

    @Test public void ArrayListTesting() {
        ArrayListTest arrayListTest = new ArrayListTest();
        long time = System.nanoTime();
        arrayListTest.add();
        arrayListTest.remove();
        long timeDif = System.nanoTime() - time;

        System.out.println("ArrayList = " + timeDif);
    }

    @Test public void LinkedListTesting() {
        LinkedListTest linkedListTest = new LinkedListTest();
        long time = System.nanoTime();
        linkedListTest.add();
        linkedListTest.remove();
        long timeDif = System.nanoTime() - time;

        System.out.println("LinkedList = " + timeDif);
    }

    @Test public void StackTesting() {
        StackTest StackTest = new StackTest();
        long time = System.nanoTime();
        StackTest.add();
        StackTest.remove();
        long timeDif = System.nanoTime() - time;

        System.out.println("Stack = " + timeDif);
    }

}