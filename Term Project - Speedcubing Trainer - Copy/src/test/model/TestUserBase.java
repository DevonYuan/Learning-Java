package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestUserBase {

    UserBase testUserBase;

    User u1;
    User u2;
    User u3;
    User u4;
    User u5;

    PracticeSession practiceForU1;
    PracticeSession practiceForU2;
    PracticeSession practiceForU3;
    PracticeSession practiceForU4;
    PracticeSession practiceForU5;

    @BeforeEach
    void setup() {
        testUserBase = new UserBase();

        u1 = new User("A", "1", "1234");
        u2 = new User("B", "2", "1234");
        u3 = new User("C", "3", "1234");
        u4 = new User("D", "4", "1234");
        u5 = new User("E", "5", "1234");

        practiceForU1 = new PracticeSession("Standard Practice");
        practiceForU2 = new PracticeSession("Standard Practice");
        practiceForU3 = new PracticeSession("Standard Practice");
        practiceForU4 = new PracticeSession("Standard Practice");
        practiceForU5 = new PracticeSession("Standard Practice");

        u1.addPracticeSession(practiceForU1);
        u2.addPracticeSession(practiceForU2);
        u3.addPracticeSession(practiceForU3);
        u4.addPracticeSession(practiceForU4);
        u5.addPracticeSession(practiceForU5);

    }

    @Test
    void testConstructor() {
        assertEquals(0, testUserBase.getNumUsers());
        assertTrue(testUserBase.getUsers().isEmpty());
    }

    @Test
    void testAddUser() {
        User u1 = new User("A", "A1", "1234");
        User u2 = new User("B", "B1", "1234");
        User u3 = new User("C", "C3", "1234");

        testUserBase.addUser(u1);
        assertFalse(testUserBase.getUsers().isEmpty());
        assertEquals(1, testUserBase.getUsers().size());
        assertEquals(u1, testUserBase.getUsers().get(0));

        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        assertEquals(3, testUserBase.getUsers().size());
        assertEquals(u1, testUserBase.getUsers().get(0));
        assertEquals(u2, testUserBase.getUsers().get(1));
        assertEquals(u3, testUserBase.getUsers().get(2));

        testUserBase.addUser(u1);
        assertEquals(3, testUserBase.getUsers().size());
    }

    @Test
    void testGetUserByUsername() {
        User u1 = new User("Devon", "devon7021o_o", "1234");
        testUserBase.addUser(u1);
        assertEquals(u1, testUserBase.getUserByUsername("devon7021o_o"));

        User u2 = new User("A", "A1", "1234");
        User u3 = new User("B", "B1", "1234");
        testUserBase.addUser(u2);
        testUserBase.addUser(u3);

        assertEquals(u2, testUserBase.getUserByUsername("A1"));
        assertEquals(u3, testUserBase.getUserByUsername("B1"));

        assertNull(testUserBase.getUserByUsername("Devon"));
    }

    @Test
    void testViewUserProfile() {
        User u1 = new User("Devon", "devon7021o_o", "1234");
        testUserBase.addUser(u1);
        assertEquals("devon7021o_o", testUserBase.viewUserProfile(u1)[0]);
        assertEquals("0", testUserBase.viewUserProfile(u1)[1]);

        PracticeSession standardPractice = new PracticeSession("Standard Practice");
        u1.addPracticeSession(standardPractice);
        assertEquals("0", testUserBase.viewUserProfile(u1)[1]);
        standardPractice.addSolve(new Solve(10.000, "R U R' U'"));
        assertEquals("1", testUserBase.viewUserProfile(u1)[1]);
    }

    @Test
    void testNumSolvesStats1SessionEachBaseCase() {
        // Base case where everyone has 0 solves
        // 5 number summary should be 0 for everything
        testUserBase.addUser(u1);
        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        testUserBase.addUser(u4);
        testUserBase.addUser(u5);

        double[] results = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        double[] stats = testUserBase.numSolvesStats();
        for (int i = 0; i < 6; i++){
            assertEquals(results[i], stats[i], 0.001);
        }
    }

    @Test
    void testNumSolvesStats1SessionEach() {
        testUserBase.addUser(u1);
        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        testUserBase.addUser(u4);
        testUserBase.addUser(u5);

        // 3, 1, 4, 3, 1 solves respectively for users 1 through 5
        practiceForU2.addSolve(new Solve(10.000, "R U R' U'"));
        practiceForU3.addSolve(new Solve(10.000, "R U R' U'"));
        practiceForU5.addSolve(new Solve(10.000, "R U R' U'"));

        for (int i = 0; i < 3; i++) {
            practiceForU1.addSolve(new Solve(10.000, "R U R' U'"));
            practiceForU3.addSolve(new Solve(10.000, "R U R' U'"));
            practiceForU4.addSolve(new Solve(10.000, "R U R' U'"));
        }

        double[] results = { 1.0, 1.0, 3.0, 3.5, 4, 1.2 };
        double[] stats = testUserBase.numSolvesStats();
        for (int i = 0; i < 6; i++){
            assertEquals(results[i], stats[i], 0.001);
        }
    }

    @Test
    void testNumSolveStatsEvenNumberOfSolves(){
        testUserBase.addUser(u1);
        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        testUserBase.addUser(u4);
        for (int i = 0; i < 6; i++){
            practiceForU1.addSolve(new Solve((double) i + 3, "R U R' U'"));
        }

        double[] result = {0, 0, 0, 3, 6, Math.sqrt(6.75)};
        double[] stats = testUserBase.numSolvesStats();
        for (int i = 0; i < 6; i++){
            assertEquals(result[i], stats[i], 0.001);
        }
    }

    @Test
    void testNumSolvesStatsMultipleSessions() {
        testUserBase.addUser(u1);
        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        testUserBase.addUser(u4);
        testUserBase.addUser(u5);

        PracticeSession nextPracticeForU2 = new PracticeSession("Slow Solves");
        PracticeSession nextPracticeForU4 = new PracticeSession("Slow Solves");

        u2.addPracticeSession(nextPracticeForU2);
        u4.addPracticeSession(nextPracticeForU4);

        // 3, 1, 4, 3, 1 solves respectively for users 1 through 5 on their first
        // practice sessions
        practiceForU2.addSolve(new Solve(10.000, "R U R' U'"));
        practiceForU3.addSolve(new Solve(10.000, "R U R' U'"));
        practiceForU5.addSolve(new Solve(10.000, "R U R' U'"));

        for (int i = 0; i < 3; i++) {
            practiceForU1.addSolve(new Solve(10.000, "R U R' U'"));
            practiceForU3.addSolve(new Solve(10.000, "R U R' U'"));
            practiceForU4.addSolve(new Solve(10.000, "R U R' U'"));
        }

        // 5 additional solves for u2 on second session
        // 2 additional solves for u4 on second esssion
        // So in total: 3, 6, 4, 5, 1 (Calculate 5 number summary)

        for (int i = 0; i < 5; i++) {
            practiceForU2.addSolve(new Solve(10.000, "R U R' U'"));
        }

        practiceForU4.addSolve(new Solve(10.000, "R U R' U'"));
        practiceForU4.addSolve(new Solve(10.000, "R U R' U'"));
        
        double[] results = { 1.0, 2.0, 4.0, 5.5, 6, Math.sqrt(2.96) };
        double[] stats = testUserBase.numSolvesStats(); 
        
        for (int i = 0; i < 6; i++){
            assertEquals(results[i], stats[i], 0.001);
        }
    }

    @Test
    void testUserBaseContainsUserName(){
        assertFalse(testUserBase.containsUsername(""));
        
        testUserBase.addUser(u1);
        assertTrue(testUserBase.containsUsername("1")); 

        testUserBase.addUser(u2);
        testUserBase.addUser(u3);
        assertTrue(testUserBase.containsUsername("2")); 
        assertTrue(testUserBase.containsUsername("3")); 
    }
}
