package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Scrambler {

    private static Random r = new Random();
    private static String[] letters = { "U", "D", "R", "L", "F", "B" };
    private static List<String> xAxis = Arrays.asList("R", "L");
    private static List<String> yAxis = Arrays.asList("U", "D");
    private static List<String> zAxis = Arrays.asList("F", "B");

    // EFFECTS: Constructs a scrambler, allowing the user to call generateScramble
    public Scrambler() {

    }

    // EFFECTS: Produces a realsitic scramble for a 3x3 Rubik's cube
    public String generateScramble() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        int[] uniqueMoveCount = uniqueMoveCount();
        int numDoubleMoves = uniqueMoveCount[0];
        int numPrimeMoves = uniqueMoveCount[1];
        String[] moves = generateRandomMoves();
        String[] movesWithoutDuplicates = removeDuplicates(moves);
        String[] movesWithoutRedundancy = removeRedundancy(movesWithoutDuplicates);

        return applyTransformations(movesWithoutRedundancy, indexes, numDoubleMoves, numPrimeMoves);
    }

    // EFFECTS: Produces an array of size 2, where the first number is the number of
    // double moves, and the second is the number of prime (counterclockwise) moves,
    // randomly determined.
    private int[] uniqueMoveCount() {
        Random r = new Random();
        int numUnique = r.nextInt(5) + 12;
        int deviation = r.nextInt(7) - 3;
        int numDoubleMoves = numUnique / 2;
        int numPrimeMoves;
        if (!(numUnique % 2 == 0)) {
            numPrimeMoves = numUnique / 2 + 1;
        } else {
            numPrimeMoves = numUnique / 2;
        }

        numDoubleMoves += deviation;
        numPrimeMoves -= deviation;
        int[] uniqueMoveCount = { numDoubleMoves, numPrimeMoves };
        return uniqueMoveCount;
    }

    // EFFECTS: Randomly generates a sequence of 20 moves, stored in an array
    private static String[] generateRandomMoves() {
        String[] moves = new String[20];
        for (int i = 0; i < 20; i++) {
            moves[i] = letters[r.nextInt(6)];
        }

        return moves;
    }

    // REQUIRES: moves.length == 20
    // MODIFIES: this
    // EFFECTS: Returns a list of moves with the duplicates replaced with new moves
    private static String[] removeDuplicates(String[] moves) {
        for (int i = 1; i < 20; i++) {
            if (moves[i].equals(moves[i - 1])) {
                boolean generatingNewMove = true;
                while (generatingNewMove) {
                    if (!(moves[i].equals(moves[i - 1]))) {
                        generatingNewMove = false;
                    } else {
                        moves[i] = letters[r.nextInt(6)];
                    }
                }
            }
        }

        return moves;
    }

    // REQUIRES: moves.length == 20;
    // EFFECTS: Modifies the given move sequence to not contain rotation about the
    // same axis 3 times in a row, which is redunant (e. g. removes sequences such
    // as RLR, or UDU)
    private static String[] removeRedundancy(String[] moves) {
        for (int i = 2; i < 20; i++) {
            boolean x = xAxis.contains(moves[i - 2]) && xAxis.contains(moves[i - 1]) && xAxis.contains(moves[i]);
            boolean y = yAxis.contains(moves[i - 2]) && yAxis.contains(moves[i - 1]) && yAxis.contains(moves[i]);
            boolean z = zAxis.contains(moves[i - 2]) && zAxis.contains(moves[i - 1]) && zAxis.contains(moves[i]);

            ArrayList<String> remainingMoves = new ArrayList<>();
            if (x) {
                String[] noXAxis = { "U", "D", "F", "B" };
                determineRemainingMoves(moves, noXAxis, i, remainingMoves);
            } else if (y) {
                String[] noYAxis = { "R", "L", "F", "B" };
                determineRemainingMoves(moves, noYAxis, i, remainingMoves);
            } else if (z) {
                String[] noZAxis = { "U", "D", "R", "L" };
                determineRemainingMoves(moves, noZAxis, i, remainingMoves);
            }
        }

        return moves;
    }

    // REQUIRES: moves.length == 20, noAxis.length == 4, 0 <= i <= 19, this method
    // was called from the removeRedundancy method
    // MODIFIES: moves, remainingMoves
    // EFFECTS: Returns an ArrayList containing the valid next moves in the scramble
    private static void determineRemainingMoves(String[] moves, String[] noAxis, int i,
            ArrayList<String> remainingMoves) {
        if (i == 19) {
            moves[i] = noAxis[r.nextInt(4)];
        } else {
            Collections.addAll(remainingMoves, noAxis);
            remainingMoves.remove(moves[i + 1]);
            moves[i] = remainingMoves.get(r.nextInt(3));
        }
    }

    // REQUIRES: moves.size == 20;
    // MODIFIES: this
    // EFFECTS: Applies transformations according to numDoubleMoves and
    // numPrimeMoves to randomly make some moves become double or prime moves, then
    // returns the final scramble
    private static String applyTransformations(String[] moves, ArrayList<Integer> indexes, int doubles, int primes) {
        String scramble = "";
        for (int i = 0; i < 20; i++) {
            if (indexes.get(i) < doubles) {
                moves[i] += "2";
            } else if (indexes.get(i) < doubles + primes) {
                moves[i] += "'";
            }
        }

        for (String move : moves) {
            scramble += move + " ";
        }

        return scramble.substring(0, scramble.length() - 1);
    }
}
