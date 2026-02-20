package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserBase {

    private ArrayList<User> users;

    // EFFECTS: Constructs a new userbase with an empty list of users inside
    public UserBase() {
        users = new ArrayList<>();
    }

    // EFFECTS: Returns a user with the given username, returns null if not found
    public User getUserByUsername(String userName) {
        for (User u : users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }

        return null;
    }

    // EFFECTS: Returns a user's username, and number of solves
    public String[] viewUserProfile(User user) {
        String[] profileDetails = new String[2];
        profileDetails[0] = user.getUserName();
        profileDetails[1] = String.valueOf(user.getNumSolves());

        return profileDetails;
    }

    // REQUIRES: The userbase needs to have a minimum of 3 numbers
    // EFFECTS: Produces a 5 number summary on the # of solves for each user
    // (Minimum, Q1, Median, Q3, Max), followed by standard deviation
    public double[] numSolvesStats() {
        double[] stats = new double[6];
        ArrayList<Integer> numSolvesInOrder = new ArrayList<>();
        users.forEach((u) -> {
            numSolvesInOrder.add(u.getNumSolves());
        });
        int size = numSolvesInOrder.size();

        Collections.sort(numSolvesInOrder);
        stats[0] = numSolvesInOrder.get(0);
        stats[2] = findMedian((List<Integer>) numSolvesInOrder);
        if (numSolvesInOrder.size() % 2 == 0) {
            stats[1] = findMedian(numSolvesInOrder.subList(0, size / 2));
            stats[3] = findMedian(numSolvesInOrder.subList(size / 2, size));
        } else {
            stats[1] = findMedian(numSolvesInOrder.subList(0, Math.floorDiv(size, 2)));
            stats[3] = findMedian(numSolvesInOrder.subList(Math.ceilDiv(size, 2), size));
        }

        stats[4] = numSolvesInOrder.get(numSolvesInOrder.size() - 1);
        stats[5] = findSD(numSolvesInOrder);
        return stats;
    }

    // REQUIRES: findMedian is already sorted
    // EFFECTS: Finds the median of the list
    private static double findMedian(List<Integer> findMedian) {
        int size = findMedian.size();
        if (findMedian.size() % 2 == 0) {
            return (double) ((findMedian.get(size / 2) + findMedian.get((size / 2) - 1))) / 2;
        } else {
            return findMedian.get(size / 2);
        }
    }

    // REQUIRES: findSD.length > 0
    // EFFECTS: Finds the standard deviation of the data set
    private static double findSD(ArrayList<Integer> findSD) {
        int sum = 0;
        for (int i : findSD) {
            sum += i;
        }

        double mean = (double) sum / findSD.size();
        double squaredDistances = 0;

        for (int i : findSD) {
            squaredDistances += Math.pow((mean - i), 2);
        }

        return Math.sqrt(squaredDistances / (findSD.size()));
    }

    // REQUIRES: Username is unique to the userbase
    // MODIFIES: this
    // EFFECTS: Adds the user to the list of users in the userbase, does nothing if
    // the username is not unique
    public void addUser(User user) {
        if (!(users.contains(user))) {
            users.add(user);
        }
    }

    // EFFECTS: Returns true if there is a user with the given username in the
    // databse, returns false otherwise
    public boolean containsUsername(String username) {
        ArrayList<String> usernames = new ArrayList<>();
        users.forEach((u) -> {
            usernames.add(u.getUserName());
        });

        return usernames.contains(username);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public int getNumUsers() {
        return users.size();
    }
}
