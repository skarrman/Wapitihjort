package tank_revolution_tests.model;

import tank_revolution.model.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class StackTest {
    List<Explosion> list;
    StackTest(){
        list = new Stack<Explosion>();
    }

    public void add (){
        for(int i = 0; i < 1000; i ++){
            list.add(new Explosion(10,10,10));
        }
    }

    public void remove (){
        for(int i = 0; i < 1000; i ++){
            list.remove(0);
        }
    }

}
