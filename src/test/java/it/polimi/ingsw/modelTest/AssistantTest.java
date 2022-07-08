package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Assistant;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class AssistantTest {

    private Assistant a1 = new Assistant(1,5);
    private Assistant a2 = new Assistant(10, 1);
    @Test
    public void testGetCardValue() {
        assertTrue(a1.getCardValue() == 1);
        assertTrue(a2.getCardValue() == 10);
    }
    @Test
    public void testGetMotherNatureMoves() {
        assertTrue(a1.getMotherNatureMoves() == 5);
        assertTrue(a2.getMotherNatureMoves() == 1);
    }

    @Test
    public void testEquals(){
        Assistant a = new Assistant(1,5);
        assertTrue(a1.equals(a));
    }

    @Test
    public void testHashCode(){
        int i = Objects.hash(a1.getCardValue(), a1.getMotherNatureMoves());
        assertEquals(i, a1.hashCode());
    }
}