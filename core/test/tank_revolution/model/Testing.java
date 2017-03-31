package tank_revolution.model;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by antonhagermalm on 2017-03-30.
 *
 */

public class Testing {
    @Test
    public void testnr1(){
        NPC npc = new NPC();
        assertTrue(npc.print1() == 1);
    }

}