package model;

import java.util.ArrayList;

public class User {

    private String name;
    private String userName;
    private String password;
    private ArrayList<PracticeSession> sessions;
    private static String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
            "Sunday" };

    // EFFECTS: Constructs a user with the given name, username, and password,
    // alongsie an empty list of practice sessions
    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        sessions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a new session to the user's collection of practice sessions
    public void addPracticeSession(PracticeSession practiceSession) {
        sessions.add(practiceSession);
    }

    // REQUIRES: times has a size of 7 as it represents the number of minutes a
    // person is free to practice per day, and skills is not empty
    // MODIFIES: this
    // EFFECTS: Returns a list containing the number of minutes they should practice
    // a specific skill each day, such as "45 minutes of standard practice", or "23
    // minutes of TPS practice, 22 minutes of standard practice". Adds 1 to the
    // number of times this function was called
    public ArrayList<String> customPracticeSchedule(int[] practiceTimes, ArrayList<String> skills) {
        skills.add("standard");
        ArrayList<String> exercises = new ArrayList<>();
        int totalTime = 0;
        for (Integer timePerDay : practiceTimes) {
            totalTime += timePerDay;
        }

        int baseTimePerSkill = totalTime / (skills.size());
        int remainingTime = totalTime % (skills.size());
        int[] timesPerSkill = new int[skills.size()];
        for (int i = 0; i < timesPerSkill.length; i++) {
            timesPerSkill[i] = baseTimePerSkill;
        }

        for (int i = 0; i < remainingTime; i++) {
            timesPerSkill[i] += 1;
        }

        schedulingLoop(exercises, timesPerSkill, practiceTimes, skills);
        return exercises;
    }

    // REQUIRES: Called from the customPracticeSchedule method
    // MODIFIES: exercises,
    // EFFECTS: Runs a loop to produce a schedule with the given information
    public void schedulingLoop(ArrayList<String> exercises, int[] times, int[] practice, ArrayList<String> skills) {
        int index = 0;
        for (int i = 0; i < practice.length; i++) {
            String todayExercises = daysOfWeek[i] + ": ";
            ArrayList<String> sections = new ArrayList<>();
            int todayTimeLeft = practice[i];
            while (todayTimeLeft > 0) {
                if (todayTimeLeft >= times[index]) {
                    sections.add(times[index] + " minutes of " + skills.get(index) + " practice, ");
                    todayTimeLeft -= times[index];
                    times[index] = 0;
                    index += 1;
                } else {
                    times[index] -= todayTimeLeft;
                    sections.add(todayTimeLeft + " minutes of " + skills.get(index) + " practice, ");
                    todayTimeLeft = 0;
                }
            }

            for (String section : sections) {
                todayExercises += section;
            }

            exercises.add(todayExercises.substring(0, todayExercises.length() - 2));
        }
    }

    // REQUIRES: topic is not an empty string
    // MODIFIES: this
    // EFFECTS: Returns a link to the most relevant video that provides guides on
    // the selected topic, adds 1 to the number of times this function was called
    public String searchForResources(String topic) {
        return null;
    }

    // EFFECTS: Returns true if the given string matches the password, and returns
    // false otherwise
    public boolean verifyPassword(String passwordAttempt) {
        return password.equals(passwordAttempt);
    }

    // EFFECTS: Produces true if the user has a practice session with the given name
    public boolean containsPracticeSessionName(String sessionName) {
        ArrayList<String> sessionNames = new ArrayList<>();
        sessions.forEach((s) -> {
            sessionNames.add(s.getSessionName());
        });

        return sessionNames.contains(sessionName);
    }

    // REQUIRES: sessionName refers to an existing practice session for the user
    // EFFECTS: Deletes the user's practice sesction with the given name, otherwise
    // does nothing if the name is not found
    public void deleteSession(String sessionName) {
        ArrayList<String> sessionNames = new ArrayList<>();
        sessions.forEach((s) -> {
            sessionNames.add(s.getSessionName());
        });

        if (sessionNames.contains(sessionName)) {
            sessions.remove(sessionNames.indexOf(sessionName));
        }
    }

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWord(){
        return password; 
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumSolves() {
        int numSolves = 0;
        for (PracticeSession session : sessions) {
            numSolves += session.getNumSolves();
        }

        return numSolves;
    }

    public ArrayList<PracticeSession> getPracticeSessions() {
        return sessions;
    }
}
