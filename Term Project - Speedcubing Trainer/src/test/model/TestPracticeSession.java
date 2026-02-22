package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

public class TestPracticeSession {
    PracticeSession testPracticeSession;

    @BeforeEach
    void setup() {
        testPracticeSession = new PracticeSession("Practice");
    }

    @Test
    void testDiagnoseSplitsOptimalSplits() {
        // Base case - the splits match the optimal ones exactly
        double[] splits1 = { 1.250, 5.000, 1.625, 2.125 };
        ArrayList<String> result1 = testPracticeSession.diagnoseSplits(splits1);
        assertTrue(result1.isEmpty());

        // A more realistic case, deviation is in a reasonable range
        double[] splits2 = { 5, 15, 5, 5 };
        ArrayList<String> result2 = testPracticeSession.diagnoseSplits(splits2);
        assertTrue(result2.isEmpty());
    }

    @Test
    void testDiagonoseSplitsOneAreaForImprovement() {
        double[] splits1 = { 14, 18, 6, 5 };
        ArrayList<String> result = testPracticeSession.diagnoseSplits(splits1);
        assertFalse(result.isEmpty());
        assertEquals("Cross", result.get(0));
    }

    @Test
    void testDiagnoseSplitsBadF2l() {
        double[] splits = { 1.0, 20.0, 1.0, 1.0 };
        ArrayList<String> result = testPracticeSession.diagnoseSplits(splits); 
        assertFalse(result.isEmpty()); 
        assertEquals("F2L", result.get(0)); 
    }

    @Test
    void testDiagnoseSplitsBadOLL() {
        double[] splits = {1.0, 1.0, 20.0, 1.0}; 
        ArrayList<String> result = testPracticeSession.diagnoseSplits(splits); 
        assertFalse(result.isEmpty()); 
        assertEquals("OLL", result.get(0)); 
    }

    @Test
    void testDiagnoseSplitsBadPLL() {
        double[] splits = {1.0, 1.0, 1.0, 20.0};
        ArrayList<String> result = testPracticeSession.diagnoseSplits(splits); 
        assertFalse(result.isEmpty()); 
        assertEquals("PLL", result.get(0)); 
    }

    @Test
    void testAvearageOf5() {
        testPracticeSession.addSolve(new Solve(9.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(10.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(10.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(11.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(10.0, "R U R' U'"));
        assertEquals(10.0, testPracticeSession.averageOf5(), 0.0001);

        testPracticeSession.addSolve(new Solve(8.0, "R U R' U'"));
        assertEquals(10.0, testPracticeSession.averageOf5(), 0.001);
    }

    @Test
    void testAverageOf12() {
        testPracticeSession.addSolve(new Solve(9.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(11.0, "R U R' U'"));
        for (int i = 0; i < 5; i++) {
            testPracticeSession.addSolve(new Solve(10, "R U R' U'"));
            testPracticeSession.addSolve(new Solve(11, "R U R' U'"));
        }

        assertEquals(10.5, testPracticeSession.averageOf12(), 0.001);
    }

    @Test
    void testAverageOf100() {
        testPracticeSession.addSolve(new Solve(6.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(7.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(8.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(10.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(11.0, "R U R' U'"));
        testPracticeSession.addSolve(new Solve(12.0, "R U R' U'"));
        for (int i = 0; i < 47; i++) {
            testPracticeSession.addSolve(new Solve(9.5, "R U R' U'"));
            testPracticeSession.addSolve(new Solve(9.8, "R U R' U'"));
        }

        assertEquals(9.65, testPracticeSession.averageOf100(), 0.001);
    }

    @Test
    void testGetSessionName() {
        assertEquals("Practice", testPracticeSession.getSessionName());
        assertFalse("3x3 Practice".equals(testPracticeSession.getSessionName()));
    }

    @Test
    void testGetSolves(){
        Solve solveA = new Solve(9.8, "R U R' U'");
        testPracticeSession.addSolve(solveA);
        assertEquals(1, testPracticeSession.getSolves().size());
        assertEquals(solveA, testPracticeSession.getSolves().get(0));

        Solve solveB = new Solve(9.8, "R U R' U'");
        testPracticeSession.addSolve(solveB);
        assertEquals(2, testPracticeSession.getSolves().size());
        assertEquals(solveA, testPracticeSession.getSolves().get(0));
        assertEquals(solveB, testPracticeSession.getSolves().get(1));
    }
}
