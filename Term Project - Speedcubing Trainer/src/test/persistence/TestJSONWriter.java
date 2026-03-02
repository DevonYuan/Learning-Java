package persistence;

import org.junit.jupiter.api.Test;

import model.PracticeSession;
import model.Solve;
import model.User;
import model.UserBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

public class TestJSONWriter {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\\0illegal:fileName.json");
            writer.open();
            fail("IO Exception was expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testWriterEmptyData() {
        try {
            UserBase userbase = new UserBase();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUserBase.json");
            writer.open();
            writer.write(userbase);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUserBase.json");
            userbase = reader.read();

            assertEquals(0, userbase.getNumUsers());
        } catch (IOException e) {
            fail("IO Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUserBase() {
        try {
            User u1 = new User("Person A", "Username A", "Password A");
            User u2 = new User("Person B", "Username B", "Password B");
            User u3 = new User("Person C", "Username C", "Password C");
            PracticeSession practiceSession1 = new PracticeSession("TPS");
            PracticeSession practiceSession2 = new PracticeSession("Standard");
            practiceSession2.addSolve(new Solve(9.8, "R U R' U'"));
            u2.addPracticeSession(practiceSession1);
            u3.addPracticeSession(practiceSession2);
            UserBase userbase = new UserBase(); 
            userbase.addUser(u1);
            userbase.addUser(u2);
            userbase.addUser(u3);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUserBase.json");
            writer.open(); 
            writer.write(userbase);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUserBase.json");
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

        }
    }
}
