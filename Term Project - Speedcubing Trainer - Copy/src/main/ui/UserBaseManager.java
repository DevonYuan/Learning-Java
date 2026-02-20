package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.PracticeSession;
import model.Scrambler;
import model.Solve;
import model.User;
import model.UserBase;

public class UserBaseManager {

    private UserBase userbase;
    private Scanner scanner;
    Scrambler scrambler;

    // EFFECTS: Constructs a UserBaseManager with a dedicated userbase, scrambler,
    // and scanner (For the user to interact with the userbase)
    public UserBaseManager() {
        userbase = new UserBase();
        scanner = new Scanner(System.in);
        scrambler = new Scrambler();
    }

    // MODIFIES: this
    // EFFECTS: Runs the program. This would be the "Home" of the app
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("1. Create a new account");
            System.out.println("2. Log into an existing account" + "\nType \'Terminate\' to stop the program\n");
            System.out.print("Enter your command: ");
            String command = scanner.nextLine();
            if (command.toLowerCase().equals("terminate")) {
                running = false;
            } else {
                if (command.equals("1")) {
                    System.out.println("\nCreating an account ...");
                    accountRegistration();
                } else if (command.equals("2")) {
                    System.out.println("\nLogging you in ...");
                    User currentUser = logIn();
                    runUser(currentUser);
                } else {
                    System.out.println("Please enter a valid command.\n");
                }
            }

            System.out.println("========================================");
        }

        System.out.println("\nThank you for using this program!");
    }

    // MODIFIES: this
    // EFFECTS: Creates an account and adds it to the userbase
    public void accountRegistration() {
        System.out.print("What is your name? ");
        String name = scanner.nextLine();

        System.out.print("Please pick a username: ");
        String userName = scanner.nextLine();

        if (userbase.containsUsername(userName)) {
            boolean takingNewUserName = true;
            while (takingNewUserName) {
                System.out.print("\nSorry! That username was taken. Please pick another one: ");
                userName = scanner.nextLine();
                if (!(userbase.containsUsername(userName))) {
                    takingNewUserName = false;
                }
            }
        }

        System.out.print("\nDone! Please set your password: ");
        String password = scanner.nextLine();
        User newUser = new User(name, userName, password);
        userbase.addUser(newUser);
        System.out.println("Congratulations! You have created your account.\n");
    }

    // EFFECTS: Logs into the user account and returns the user
    public User logIn() {
        String username = getUserName();

        User currentUser = userbase.getUserByUsername(username);
        System.out.print("\nPlease enter your password: ");
        String passwordAttempt = scanner.nextLine();
        if (!(currentUser.verifyPassword(passwordAttempt))) {
            boolean gettingPassword = true;
            while (gettingPassword) {
                System.out.print("The password was incorrect. Please enter your password: ");
                passwordAttempt = scanner.nextLine();
                if (currentUser.verifyPassword(passwordAttempt)) {
                    gettingPassword = false;
                }
            }
        }

        System.out.println("\nDone! you are now logged in.\n");
        System.out.println("====================");
        return currentUser;
    }

    // EFFECTS: Gets the user's username and does not stop until a user has been
    // found in the userbase
    public String getUserName() {
        System.out.print("What is your username? ");
        String username = scanner.nextLine();
        if (!(userbase.containsUsername(username))) {
            boolean gettingUserName = true;
            while (gettingUserName) {
                System.out.print("\nThat user does not exist. Please enter your username: ");
                username = scanner.nextLine();
                if (userbase.containsUsername(username)) {
                    gettingUserName = false;
                }
            }
        }

        return username;
    }

    // REQUIRES: User exists in the userbase
    // MODIFIES: this, currentUser
    // EFFECTS: Acceses the user's features in a loop to say logged in until
    // specified otherwise
    public void runUser(User currentUser) {
        boolean running = true;
        while (running) {
            System.out.println("1. Start a new practice session");
            System.out.println("2. Receive a custom practice schedule");
            System.out.println("Type \'log out\' to log out of this user");
            System.out.print("\nEnter your command: ");
            String command = scanner.nextLine();
            if (command.toLowerCase().equals("log out")) {
                System.out.println("\nLogging you out!");
                running = false;
            } else if (command.equals("1")) {
                initializePracticeSession(currentUser);
            } else if (command.equals("2")) {
                getCustomPracticeSchedule(currentUser);
            } else {
                System.out.println("Please enter a valid command.\n");
                System.out.println("====================");
            }
        }

        System.out.println("Done! You are now logged out.");
    }

    // REQUIRES: User exists in the database
    // MODIFIES: this, currentUser
    // EFFECTS: Creates a practice session and adds it to the user. If the practice
    // session already exists there is the option to continue off from last time, or
    // overwrite the old one
    public void initializePracticeSession(User currentUser) {
        System.out.println("Beginning practice session.");
        System.out.print("What will you be working on this time? ");
        String sessionName = scanner.nextLine();
        PracticeSession newSession = new PracticeSession(sessionName);

        newSession = manageOldPracticeSession(newSession, currentUser, sessionName);

        currentUser.addPracticeSession(newSession);
        practice(newSession);
    }

    // REQUIRES: Called from initializePracticeSession
    // MODIFIES: this, currentUser, newSession
    // EFFECTS: Presents the user with the option to overwrite the old practice
    // session or create a new one when the program detects that the user already
    // has a previously practice session with the given name and wants to start a
    // new one. Modifies the PracticeSession and returns the new version
    public PracticeSession manageOldPracticeSession(PracticeSession newSession, User currentUser, String sessionName) {
        if (currentUser.containsPracticeSessionName(sessionName)) {
            System.out.println("\nYou already have a practice session dedicated to that skill.");
            System.out.println("1. Overwrite the previous session");
            System.out.println("2. Resume the practice session");
            String command = scanner.nextLine();
            boolean gettingDecision = true;
            while (gettingDecision) {
                if (command.equals("1")) {
                    System.out.println("Very well. You will start with a new practice session");
                    currentUser.deleteSession(sessionName);
                    newSession = new PracticeSession(sessionName);
                    gettingDecision = false;
                } else if (command.equals("2")) {
                    System.out.println("Very well. you will resume the previous practice session.");
                    newSession = getPracticeSessionByUserName(currentUser, sessionName);
                    gettingDecision = false;
                } else {
                    System.out.println("Please enter a valid command");
                }
            }
        }

        return newSession;
    }

    // EFFECTS: Produces a custom practice schedule for the yser based on when they
    // are free to practice and what skills they identified as their areas of
    // improvement
    public void getCustomPracticeSchedule(User currentUser) {
        System.out.println();
        int[] practiceTimes = getPracticeTimes();
        ArrayList<String> skillsToWorkOn = getListOfSkills();
        ArrayList<String> schedule = currentUser.customPracticeSchedule(practiceTimes, skillsToWorkOn);
        System.out.println();
        schedule.forEach((s) -> {
            System.out.println(s);
        });
        System.out.println();
    }

    // EFFECTS: Records the practice times specified by the user
    public int[] getPracticeTimes() {
        String[] daysOfTheWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        int[] practiceTimes = new int[7];
        for (int i = 0; i < 7; i++) {
            System.out.print("How many minutes per day are you able to practice on " + daysOfTheWeek[i] + "? ");
            String dailyTime = scanner.nextLine();
            int time = 0;
            if (isStringInt(dailyTime)) {
                time = Integer.parseInt(dailyTime);
            } else {
                boolean gettingTime = true;
                while (gettingTime) {
                    System.out.print("\nPlease enter a whole number: ");
                    dailyTime = scanner.nextLine();
                    if (isStringInt(dailyTime)) {
                        time = Integer.parseInt(dailyTime);
                        gettingTime = false;
                    }
                }
            }

            practiceTimes[i] = time;
        }

        return practiceTimes;
    }

    // EFFECTS: Records the skills specified by the user
    public ArrayList<String> getListOfSkills() {
        ArrayList<String> listOfSkills = new ArrayList<>();
        System.out.print("\nHow many skills would you like to work on? ");
        String numSkillsString = scanner.nextLine();
        int numSkills = 0;
        if (isStringInt(numSkillsString)) {
            numSkills = Integer.parseInt(numSkillsString);
        } else {
            boolean gettingNumSkills = true;
            while (gettingNumSkills) {
                System.out.print("Please enter a whole number: ");
                numSkillsString = scanner.nextLine();
                if (isStringInt(numSkillsString)) {
                    numSkills = Integer.parseInt(numSkillsString);
                    gettingNumSkills = false;
                }
            }
        }

        for (int i = 0; i < numSkills; i++) {
            System.out.print("Skill " + Integer.valueOf(i + 1) + ": ");
            String currentSkill = scanner.nextLine();
            listOfSkills.add(currentSkill);
        }

        return listOfSkills;
    }

    // EFFECTS: Returns true if the given string can be parsed as an integer, false
    // otherwise
    public boolean isStringInt(String toParse) {
        try {
            Integer.parseInt(toParse);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // MODIFIES: this, currentSession
    // EFFECTS: Acesses the PracticeSession's features using a loop
    public void practice(PracticeSession currentSession) {
        boolean gettingDecision = true;
        while (gettingDecision) {
            System.out.println("\n1. Start adding times");
            System.out.println("2. Diagnose your splits");
            System.out.println("3. See all your current times");
            System.out.println("Type \'stop\' to end the practice session");

            System.out.print("\nPlease enter a command: ");
            String command = scanner.nextLine();
            if (command.equals("stop")) {
                gettingDecision = false;
            } else if (command.equals("1")) {
                addTimes(currentSession);
            } else if (command.equals("2")) {
                getSolveDiagnosis(currentSession);
            } else if (command.equals("3")) {
                listAllSolves(currentSession);
            } else {
                System.out.print("\nPlease enter a valid command: ");
                command = scanner.nextLine();
            }
        }

        System.out.println("Done practice!");
    }

    public void listAllSolves(PracticeSession currentSession) {
        ArrayList<Solve> solves = currentSession.getSolves();
        for (int i = 0; i < solves.size(); i++) {
            System.out.println((i + 1) + ". " + solves.get(i).getScramble() + ", " + solves.get(i).getTime());
        }
    }

    // EFFECTS: Prints the user's areas of improvement based on their time splits
    // for each step of solving the cube, asuming they use the standard CFOP method.
    public void getSolveDiagnosis(PracticeSession currentPracticeSession) {
        System.out.println();
        double[] splits = getTimeSplits();
        ArrayList<String> areasOfImprovement = currentPracticeSession.diagnoseSplits(splits);
        if (areasOfImprovement.isEmpty()) {
            System.out.println("\nYour current splits are quite optimal. ");
            System.out.println("To keep improving, pick one skill, and focus on it until you're better.");
            System.out.println("Then, move on to the next!");
        } else {
            System.out.println("You should work on: ");
            areasOfImprovement.forEach((s) -> {
                System.out.println("- " + s);
            });
        }
    }

    // EFFECTS: Uses the scanner to get the time splits frome ach user
    public double[] getTimeSplits() {
        double[] splits = new double[4];
        String[] steps = { "Cross", "F2L", "OLL", "PLL" };
        System.out.println("Let's take a look at your time splits!");

        for (int i = 0; i < 4; i++) {
            System.out.print(steps[i] + ": ");
            String timeForCurrentStep = scanner.nextLine();
            double time = 0;
            if (isStringDouble(timeForCurrentStep)) {
                time = Double.parseDouble(timeForCurrentStep);
            } else {
                boolean gettingTime = true;
                while (gettingTime) {
                    System.out.print("Please enter a valid number: ");
                    timeForCurrentStep = scanner.nextLine();
                    if (isStringDouble(timeForCurrentStep)) {
                        gettingTime = false;
                    }
                }
            }

            splits[i] = time;
        }

        return splits;
    }

    // EFFECTS: Produces true if the the string can be parsed as a double, otherwise
    // produces false
    public boolean isStringDouble(String toParseDouble) {
        try {
            Double.parseDouble(toParseDouble);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // REQUIRES: The userbase contains the given PracticeSession
    // MODIFIES: this, currentSession
    public void addTimes(PracticeSession currentSession) {
        System.out.println("Type \'stop\' at any point to exit the practice session");
        boolean addingTimes = true;
        while (addingTimes) {
            String scramble = scrambler.generateScramble();
            System.out.println("\nScramble: " + scramble);
            System.out.print("Please enter your time: ");
            String input = scanner.nextLine();
            double time;
            try {
                time = Double.parseDouble(input);
                currentSession.addSolve(new Solve(time, scramble));
                displayAverages(currentSession);
            } catch (NumberFormatException failedParseDouble) {
                if (input.equals("stop")) {
                    addingTimes = false;
                } else {
                    System.out.println("Please enter a valid command!");
                }
            }
        }

        System.out.println("Exiting the session ...");
    }

    // EFFECTS: Prints out the most recent averages in a given practice session
    private static void displayAverages(PracticeSession currentSession) {
        if (currentSession.getNumSolves() >= 5) {
            System.out.println("ao5: " + String.format("%.3f", currentSession.averageOf5()));
        }

        if (currentSession.getNumSolves() >= 12) {
            System.out.println("ao12: " + String.format("%.3f", currentSession.averageOf12()));
        }

        if (currentSession.getNumSolves() >= 100) {
            System.out.println("ao100: " + String.format("%.3f", currentSession.averageOf100()));
        }
    }

    // REUIRES: The user has a practice session with the given name
    // EFFECTS: Returns the practice session with the given name, otherwise returns
    // null
    public PracticeSession getPracticeSessionByUserName(User user, String practiceSessionName) {
        ArrayList<String> sessionNames = new ArrayList<>();
        user.getPracticeSessions().forEach((p) -> {
            sessionNames.add(p.getSessionName());
        });

        if (sessionNames.contains(practiceSessionName)) {
            return user.getPracticeSessions().get(sessionNames.indexOf(practiceSessionName));
        }

        return null;
    }
}
