package tank_revolution_tests.model;

import tank_revolution.model.Explosion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class ArrayListTest {
    List<Explosion> list;
    int iterations;
    ArrayListTest(int iterations){
        list = new ArrayList();
        this.iterations = iterations;
    }

    public void add (){
        for(int i = 0; i < iterations; i ++){
            list.add(new Explosion(10,10,10));
        }
    }

    public void remove() {
        for (int i = 0; i < iterations; i++) {
            list.remove(0);
        }
    }

}
