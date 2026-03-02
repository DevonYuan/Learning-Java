package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSolve {
    
    Solve testSolve; 

    @BeforeEach
    void setup(){
        testSolve = new Solve(10.000, "R U R' U'"); 
    }

    @Test
    void testGetTime(){
        assertEquals(10.000, testSolve.getTime(), 0.001); 
    }

    @Test
    void testGetScramble(){
        assertEquals("R U R' U'", testSolve.getScramble()); 
    }
}
