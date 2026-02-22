package model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

    User testUser;

    @BeforeEach
    void setup() {
        testUser = new User("Devon", "devon7021o_o", "1234");
    }

    @Test
    void testConstructor() {
        assertEquals("Devon", testUser.getName());
        assertEquals("devon7021o_o", testUser.getUserName());
        assertTrue(testUser.getPracticeSessions().isEmpty());
    }

    @Test
    void testAddPracticeSession() {
        PracticeSession testPracticeSession1 = new PracticeSession("Standard Practice");
        PracticeSession testPracticeSession2 = new PracticeSession("Slow Solves");

        testUser.addPracticeSession(testPracticeSession1);
        assertFalse(testUser.getPracticeSessions().isEmpty());
        assertEquals(testPracticeSession1, testUser.getPracticeSessions().get(0));

        testUser.addPracticeSession(testPracticeSession2);
        assertEquals(2, testUser.getPracticeSessions().size());
        assertEquals(testPracticeSession1, testUser.getPracticeSessions().get(0));
        assertEquals(testPracticeSession2, testUser.getPracticeSessions().get(1));
    }

    @Test
    void testCustomPracticeScheduleOneSkill() {
        int[] practiceTimes = { 45, 45, 45, 45, 45, 45, 45 };
        ArrayList<String> targetSkills = new ArrayList<>();
        targetSkills.add("TPS");
        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("Monday: 45 minutes of TPS practice");
        expectedResults.add("Tuesday: 45 minutes of TPS practice");
        expectedResults.add("Wednesday: 45 minutes of TPS practice");
        expectedResults.add("Thursday: 23 minutes of TPS practice, 22 minutes of standard practice");
        expectedResults.add("Friday: 45 minutes of standard practice");
        expectedResults.add("Saturday: 45 minutes of standard practice");
        expectedResults.add("Sunday: 45 minutes of standard practice");

        ArrayList<String> results = testUser.customPracticeSchedule(practiceTimes, targetSkills);
        assertFalse(results.isEmpty());
        assertEquals(7, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void testCustomPracticeScheduleTwoSkills() {
        int[] practiceTimes = { 45, 15, 45, 15, 45, 15, 45 };
        ArrayList<String> targetSkills = new ArrayList<>();
        targetSkills.add("TPS");
        targetSkills.add("look ahead");
        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("Monday: 45 minutes of TPS practice");
        expectedResults.add("Tuesday: 15 minutes of TPS practice");
        expectedResults.add("Wednesday: 15 minutes of TPS practice, 30 minutes of look ahead practice");
        expectedResults.add("Thursday: 15 minutes of look ahead practice");
        expectedResults.add("Friday: 30 minutes of look ahead practice, 15 minutes of standard practice");
        expectedResults.add("Saturday: 15 minutes of standard practice");
        expectedResults.add("Sunday: 45 minutes of standard practice");

        ArrayList<String> results = testUser.customPracticeSchedule(practiceTimes, targetSkills);
        assertFalse(results.isEmpty());
        assertEquals(7, results.size());
        assertEquals(expectedResults, results);
    }


    @Test
    void testSearchForResources() {
        String topic1 = "TPS"; 
        String turnSpeedLink = "https://youtube.com/watch?v=__q-5MwlOiU&feature=youtu.be";
        assertEquals(turnSpeedLink, testUser.searchForResources(topic1));

        String topic2 = "Fingertricks"; 
        String fingerTricksLink = "https://youtube.com/watch?v=KWe4SNIMtrg&feature=youtu.be";
        assertEquals(topic2, fingerTricksLink);
    }

    @Test
    void testSetName(){
        String newName = "devon";
        assertEquals("Devon", testUser.getName());
        
        testUser.setName(newName);
        assertEquals(newName, testUser.getName()); 
    }

    @Test
    void testSetUsername(){
        assertEquals("devon7021o_o", testUser.getUserName()); 

        String userName = "devon7022o_o"; 
        testUser.setUserName(userName);
        assertEquals("devon7022o_o", testUser.getUserName());
    }

    @Test
    void testVerifyPassword(){
        boolean passwordAttempt = testUser.verifyPassword("123");
        assertFalse(passwordAttempt);

        boolean nextPasswordAttempt = testUser.verifyPassword("1234");
        assertTrue(nextPasswordAttempt);
    }

    @Test
    void testContainsPracticeSessionName(){
        assertFalse(testUser.containsPracticeSessionName("3x3 Practice")); 

        PracticeSession p1 = new PracticeSession("3x3 Practice"); 
        p1.addSolve(new Solve(10.000, "R U R' U'"));
        testUser.addPracticeSession(p1);
        assertEquals(1, testUser.getNumSolves());
        assertTrue(testUser.containsPracticeSessionName("3x3 Practice"));
    }

    @Test
    void testDeletePracticeSession(){
        PracticeSession p1 = new PracticeSession("Standard 3x3 practice"); 
        p1.addSolve(new Solve(10.000, "R U R' U'"));
        testUser.addPracticeSession(p1);

        assertEquals(1, testUser.getNumSolves());
        
        testUser.deleteSession("Standard 3x3 practice");
        assertEquals(0, testUser.getNumSolves());
        assertTrue(testUser.getPracticeSessions().isEmpty());

        testUser.deleteSession("Standard 3x3 practice");
        assertEquals(0, testUser.getNumSolves());
        assertTrue(testUser.getPracticeSessions().isEmpty()); 
    }

    @Test
    void testGetPassword(){
        User u1 = new User("Name", "UserName", "Password");
        assertEquals("Password", u1.getPassWord());
    }
}
