package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestScrambler {
    Scrambler s;

    @BeforeEach
    void setup() {
        s = new Scrambler();
    }

    @Test
    void testForNoDuplicates() {
        for (int i = 0; i < 1000; i++) {
            String scramble = s.generateScramble();
            String[] moves = scramble.split(" ");
            for (int j = 0; j < moves.length; j++) {
                moves[j] = moves[j].substring(0, 1);
            }

            String plainScramble = "";
            for (String move : moves) {
                plainScramble += move;
            }

            boolean duplicatedU = plainScramble.contains("UU");
            boolean duplicatedD = plainScramble.contains("DD");
            boolean duplicatedR = plainScramble.contains("RR");
            boolean duplicatedL = plainScramble.contains("LL");
            boolean duplicatedF = plainScramble.contains("FF");
            boolean duplicatedB = plainScramble.contains("BB");

            assertFalse(duplicatedU);
            assertFalse(duplicatedD);
            assertFalse(duplicatedR);
            assertFalse(duplicatedL);
            assertFalse(duplicatedF);
            assertFalse(duplicatedB);
        }
    }

    @Test
    void testForNoRedunancy() {
        for (int i = 0; i < 1000; i++) {
            String scramble = s.generateScramble();
            String[] moves = scramble.split(" ");
            for (int j = 0; j < moves.length; j++) {
                moves[j] = moves[j].substring(0, 1);
            }

            String plainScramble = "";
            for (String move : moves) {
                plainScramble += move;
            }

            boolean xAxisRedundant = plainScramble.contains("RLR") || plainScramble.contains("LRL");
            boolean yAxisRedundant = plainScramble.contains("UDU") || plainScramble.contains("DUD");
            boolean zAxisRedundant = plainScramble.contains("FBF") || plainScramble.contains("BFB");

            if (xAxisRedundant) {
                System.out.println("After: " + plainScramble);
            } else if (yAxisRedundant) {
                System.out.println("After: " + plainScramble);
            } else if (zAxisRedundant) {
                System.out.println("After: " + plainScramble);
            }

            assertFalse(xAxisRedundant);
            assertFalse(yAxisRedundant);
            assertFalse(zAxisRedundant);
        }
    }

    @Test
    void testApplyTransformations() {
        // This also tests the uniqueMoveCount helper, by checking if the
        // transformations to the moves were applied properly
        for (int i = 0; i < 1000; i++) {
            String scramble = s.generateScramble();
            String[] moves = scramble.split(" ");
            
            int numDoubleMoves = 0;
            int numPrimeMoves = 0; 
            for (int j = 0; j < moves.length; j++){
                if (moves[j].contains("2")){
                    numDoubleMoves += 1; 
                } else if (moves[j].contains("'")){
                    numPrimeMoves += 1; 
                }
            }

            assertTrue(numDoubleMoves + numPrimeMoves <= 17);
            assertTrue(numDoubleMoves + numPrimeMoves >= 12); 
            assertTrue(Math.abs(numDoubleMoves - numPrimeMoves) <= 7); 
        }
    }
}