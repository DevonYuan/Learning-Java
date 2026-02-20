package model;

public class Solve {
    private double time;
    private String scramble;

    // EFFECTS: Constructs a solve with a given solve time and the scramble that the
    // user solved
    public Solve(double time, String scramble) {
        this.time = time;
        this.scramble = scramble;
    }

    public double getTime() {
        return time;
    }

    public String getScramble() {
        return scramble;
    }
}
