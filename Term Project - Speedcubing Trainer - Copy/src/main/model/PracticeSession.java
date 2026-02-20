package model;

import java.util.ArrayList;

public class PracticeSession {

    private ArrayList<Solve> solves;
    private String sessionName;

    // EFFECTS: Constructs a practice session with the given name (Topic / skill to
    // be worked on) and an empty list of solves
    public PracticeSession(String sessionName) {
        this.sessionName = sessionName;
        solves = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds the solve to this practice session's list of solves
    public void addSolve(Solve solve) {
        solves.add(solve);
    }

    // REQUIRES: splits has a size of 4 positive numbers, with the sum of elements
    // in the list being <= 60 seconds. Also, the total time (sum of elements) in
    // the list should be between 8 to 60 seconds inclusive
    // MODIFIES: this
    // EFFECTS: Returns a list of areas for improvement based on the user's time
    // split for each step, assuming they use the CFOP method. An empty list means
    // that the user's splits are optimal. Also adds 1 to the number of times this
    // function was called
    public ArrayList<String> diagnoseSplits(double[] splits) {
        ArrayList<String> areasForImprovement = new ArrayList<>();
        double totalTime = 0;
        for (Double split : splits) {
            totalTime += split;
        }

        double[] ratios = new double[4];
        for (int i = 0; i < 4; i++) {
            ratios[i] = splits[i] / totalTime;
        }

        if (ratios[0] >= 0.325) {
            areasForImprovement.add("Cross");
        } else if (ratios[1] >= 0.7) {
            areasForImprovement.add("F2L");
        } else if (ratios[2] >= 0.3625) {
            areasForImprovement.add("OLL");
        } else if (ratios[3] >= 0.4125) {
            areasForImprovement.add("PLL");
        }

        return areasForImprovement;
    }

    // REQUIRES: solves.size() >= 5
    // EFFECTS: Produces the most recent average of 5 solves (Mean of the middle 3,
    // dropping the slowest and the fastest times)
    public double averageOf5() {
        ArrayList<Double> mostRecentTimes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mostRecentTimes.add(solves.get(solves.size() - 1 - i).getTime());
        }
        mostRecentTimes.remove(slowest(mostRecentTimes));
        mostRecentTimes.remove(fastest(mostRecentTimes));
        return mean(mostRecentTimes);
    }

    // REQUIRES: solves.size() >= 12
    // EFFECTS: Produces the most recent average of 12 solves (Mean of the middle
    // 10, dropping the slowest and the fastest times)
    public double averageOf12() {
        ArrayList<Double> mostRecentTimes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            mostRecentTimes.add(solves.get(solves.size() - 1 - i).getTime());
        }

        mostRecentTimes.remove(slowest(mostRecentTimes));
        mostRecentTimes.remove(fastest(mostRecentTimes));
        return mean(mostRecentTimes);
    }

    // REQUIRES: solves.size() >= 100
    // EFFECTS: Produces the most recent average of 100 solves (Drop the 3 fastest
    // and 3 lowest, then take the mean of the middle 94 solves)
    public double averageOf100() {
        ArrayList<Double> mostRecentTimes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            mostRecentTimes.add(solves.get(solves.size() - 1 - i).getTime());
        }

        for (int i = 0; i < 3; i++) {
            mostRecentTimes.remove(slowest(mostRecentTimes));
            mostRecentTimes.remove(fastest(mostRecentTimes));
        }

        return mean(mostRecentTimes);
    }

    // REQUIRES: getSmallest.size() > 0
    // EFFECTS: Gets the smallest double value in the list
    private static double fastest(ArrayList<Double> getSmallest) {
        double smallest = getSmallest.get(0);
        for (Double d : getSmallest) {
            if (d < smallest) {
                smallest = d;
            }
        }

        return smallest;
    }

    // REQUIRES: getMean.size() > 0
    // EFFECTS: Returns the mean of the numbers in the list
    private static double mean(ArrayList<Double> getMean) {
        double sum = 0;
        for (Double d : getMean) {
            sum += d;
        }
        return sum / getMean.size();
    }

    // REQUIRES: getSmallest().size() > 0
    // EFFECTS: Gets largest double value in the list
    private static double slowest(ArrayList<Double> getLargest) {
        double largest = getLargest.get(0);
        for (Double d : getLargest) {
            if (d > largest) {
                largest = d;
            }
        }

        return largest;
    }

    // EFFECTS: Returns the number of solves completed by the user
    public int getNumSolves() {
        return solves.size();
    }

    public ArrayList<Solve> getSolves() {
        return solves;
    }

    public String getSessionName() {
        return sessionName;
    }
}
