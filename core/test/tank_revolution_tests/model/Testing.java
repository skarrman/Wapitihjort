package tank_revolution_tests.model;


import org.junit.Test;
import tankRevolution.model.*;
import tankRevolution.model.Character;
import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.model.shootablePackage.ProjectileFactory;
import tankRevolution.utils.Id;
import tankRevolution.utils.Point;
import tankRevolution.utils.Vector;

import static org.junit.Assert.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

public class Testing {

    @Test public void tankDamageTest(){
        Character character = CharacterFactory.newPlayer(Id.PLAYER1);
        Tank tank = new Tank();
        character.setTank(tank);
        List<Character> characters = new ArrayList<Character>();
        characters.add(character);
        TankRevolution tankRevolution = new TankRevolution(characters);
        tankRevolution.damage(ProjectileFactory.create(AmmunitionType.MISSILE), tank, 2);
        assertEquals(40, (int)tank.getHealth());
    }

    @Test public void reduceFuelTest(){
        Character character = CharacterFactory.newPlayer(Id.PLAYER1);
        Tank tank = new Tank();
        character.setTank(tank);
        List<Character> characters = new ArrayList<Character>();
        characters.add(character);
        TankRevolution tankRevolution = new TankRevolution(characters);
        tankRevolution.reduceFuel();
        tankRevolution.reduceFuel();
        assertEquals(99, (int)tankRevolution.getCurrentTank().getFuel());
    }

    @Test public void NPCTest(){
        NPC NPC = CharacterFactory.newNPC(Id.PLAYER1, NPCDifficulty.MEDIUM);
        Point own = new Point(1, 1);
        Point opponent = new Point(21, 1);
        Vector vector = NPC.calculateIdealVector(own, opponent);
        assertTrue(vector.getDeltaX() > 0);
        assertTrue(vector.getDeltaY() > 0);
    }
}