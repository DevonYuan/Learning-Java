package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import model.PracticeSession;
import model.Solve;
import model.User;
import model.UserBase;

import java.io.IOException;

public class TestJSONReader {
    // These tests were modelled based off of:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // TODO: Go to office hours and ask about why I can't download the annotation to
    // exclude from the checkstyle report in the test and UI classes

    @Test
    void testReaderNonExistentFile() {
        JSONReader reader = new JSONReader("data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUserBase() {
        JSONReader reader = new JSONReader("data/TestReaderEmptyUserBase.json");
        try {
            UserBase oldState = reader.read();
            assertEquals(0, oldState.getNumUsers());
        } catch (IOException e) {
            System.out.println("Coudln't read from file");
        }
    }

    @Test
    void testReaderGeneralUserBase() {
        // TODO: Write the data in the corresponding JSON file
        User u1 = new User("Person A", "Username A", "Password A");
        User u2 = new User("Person B", "Username B", "Password B");
        User u3 = new User("Person C", "Username C", "Password C");
        PracticeSession practiceSession1 = new PracticeSession("TPS");
        PracticeSession practiceSession2 = new PracticeSession("Standard");
        practiceSession2.addSolve(new Solve(9.8, "R U R' U'"));
        u2.addPracticeSession(practiceSession1);
        u3.addPracticeSession(practiceSession2);
        UserBase referenceUserBase = new UserBase();
        referenceUserBase.addUser(u1);
        referenceUserBase.addUser(u2);
        referenceUserBase.addUser(u3);
        JSONReader reader = new JSONReader("./data/TestReaderGeneralUserBase.json");
        UserBase userbase;

        try {
            userbase = reader.read();
            assertEquals(3, userbase.getNumUsers());

            assertEquals("Person A", userbase.getUsers().get(0).getName());
            assertEquals("Username A", userbase.getUsers().get(0).getUserName());
            assertEquals("Password A", userbase.getUsers().get(0).getPassWord());
            assertEquals(0, userbase.getUsers().get(0).getNumSolves());
            assertEquals(0, userbase.getUsers().get(0).getPracticeSessions().size());

            assertEquals("Person B", userbase.getUsers().get(1).getName());
            assertEquals("Username B", userbase.getUsers().get(1).getUserName());
            assertEquals("Password B", userbase.getUsers().get(1).getPassWord());
            assertEquals(0, userbase.getUsers().get(1).getNumSolves());
            assertEquals(1, userbase.getUsers().get(1).getPracticeSessions().size());

            double time = userbase.getUsers().get(2).getPracticeSessions().get(0).getSolves().get(0).getTime();
            String s = userbase.getUsers().get(2).getPracticeSessions().get(0).getSolves().get(0).getScramble();
            assertEquals("Person C", userbase.getUsers().get(2).getName());
            assertEquals("Username C", userbase.getUsers().get(2).getUserName());
            assertEquals("Password C", userbase.getUsers().get(2).getPassWord());
            assertEquals(1, userbase.getUsers().get(2).getNumSolves());
            assertEquals(1, userbase.getUsers().get(2).getPracticeSessions().size());
            assertEquals(time, 9.8, 0.001);
            assertEquals(s, "R U R' U'");
        } catch (IOException e) {
            fail("This shouldn't happen ...");
        }
    }
}
