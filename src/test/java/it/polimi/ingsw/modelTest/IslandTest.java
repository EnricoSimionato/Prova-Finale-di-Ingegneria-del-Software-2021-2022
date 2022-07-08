package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.IslandAlreadyForbiddenException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IslandTest {

    private Island island = new Island(1);
    private Bag bag = new Bag();

    @Test
    public void testSetProhibition() throws IslandAlreadyForbiddenException {
        island.setProhibition();
        assertTrue(island.isForbidden());
    }

    @Test
    public void testIsForbidden() throws IslandAlreadyForbiddenException {
        island.setProhibition();
        assertTrue(island.isForbidden());
    }

    @Test
    public void testResetProhibition() {
        island.resetProhibition();
        assertFalse(island.isForbidden());
    }

    @Test
    public void testGetIndex() {
        assertEquals(1, (int) island.getIndex().get(0));
    }

    @Test
    public void testSetTowers() {
        List<Tower> t = new ArrayList<>() ;
        t.add(new Tower(TowerColor.BLACK));
        island.setTowers(t);
        assertEquals(t.get(0), island.getTowers().get(0));
    }

    @Test
    public void testIsland(){
        Island i = new Island();
        assertNotNull(i);
    }

    @Test
    public void testGetTowers() {
        List<Tower> t = new ArrayList<>() ;
        t.add(new Tower(TowerColor.BLACK));
        island.setTowers(t);
        assertEquals(t.get(0), island.getTowers().get(0));

    }

    @Test
    public void testRemoveTowers() {
        List<Tower> t = new ArrayList<>() ;
        t.add(new Tower(TowerColor.BLACK));
        island.setTowers(t);
        assertEquals(t.get(0), island.removeTowers().get(0));
        assertEquals(0, island.getTowers().size());
    }

    @Test
    public void testAddStudents() throws EmptyBagException, InvalidIndexException {
        for(int i = 0; i < 3; i++){
            island.addStudents(bag.drawStudents(1).get(0), island.getIndex().get(0));
            assertNotNull(island.getStudents());
        }
        assertEquals(3, island.getStudents().size());
    }

    @Test
    public void testGetStudents() throws EmptyBagException, InvalidIndexException {
        for(int i = 0; i < 3; i++){
            Student s = bag.drawStudents(1).get(0);
            island.addStudents(s, island.getIndex().get(0));
            assertNotNull(island.getStudents());
            assertEquals(s, island.getStudents().get(i));
        }
        assertEquals(3, island.getStudents().size());
    }

    @Test
    public void testGetAggregation() {
        assertEquals(1, island.getAggregation());
    }
}