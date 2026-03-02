package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.PracticeSession;
import model.Solve;
import model.User;
import model.UserBase;

// I modelled this class from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// (As per the project requirements, this is allowed as long as a citation is given)

public class JsonReader {
    private String source;

    // REQUIRES: The source is a relative path leading to the data folder in this
    // directory, where the data will be stored
    // EFFECTS: Constructs a new reader to read the data at the given location
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Returns a userbase whose state is the same as the last time the data
    // was saved upon closing the program, and throws an IOException if an error
    // occurs while trying to read the file
    public UserBase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserBase(jsonObject);
    }

    // EFFECTS: Reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses workroom from JSON object and returns it
    private UserBase parseUserBase(JSONObject jsonObject) {
        UserBase userbase = new UserBase();
        addUsers(userbase, jsonObject);
        return userbase;
    }

    // MODIFIES: userbase
    // EFFECTS: Parses users from JSON object, and adds them to the userbase
    private void addUsers(UserBase userbase, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(userbase, nextUser);
        }
    }

    // MODIFIES: userbase
    // EFFECTS: Parses user from JSON object and adds it to userbase
    private void addUser(UserBase userbase, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String username = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        User user = new User(name, username, password);
        addSessions(user, jsonObject);
        userbase.addUser(user);
    }

    // MODIFIES: user
    // EFFECTS: Parses practice sessions
    private void addSessions(User user, JSONObject jsonObject) {
        JSONArray sessions = jsonObject.getJSONArray("sessions");
        for (Object json : sessions) {
            JSONObject nextSession = (JSONObject) json;
            PracticeSession session = addSession(nextSession);
            user.addPracticeSession(session);
        }
    }

    // EFFECTS: Parses one practice session
    private PracticeSession addSession(JSONObject jsonObject) {
        String sessionName = jsonObject.getString("sessionName");
        PracticeSession nextSession = new PracticeSession(sessionName);
        JSONArray solves = jsonObject.getJSONArray("solves");
        for (Object json : solves) {
            JSONObject nextSolve = (JSONObject) json;
            double time = nextSolve.getDouble("time");
            String scramble = nextSolve.getString("scramble");
            nextSession.addSolve(new Solve(time, scramble));
        }

        return nextSession;
    }
}