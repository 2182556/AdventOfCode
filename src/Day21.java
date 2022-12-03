//import java.nio.file.Paths;
import java.util.*;


public class Day21 {

    public static void splitUniverses(boolean player1, int die, int[] place, int[] score, Long[] winsCount, Long sameUniverses){
        int turn = 1;
        if (player1) turn = 0;

        place[turn] += die;
        if (place[turn]>10) place[turn]%=10;
        score[turn] += place[turn];

        if (score[turn] >= 21) {
            winsCount[turn]+=sameUniverses;
        } else {
            splitUniverses(!player1, 3, place.clone(), score.clone(), winsCount, sameUniverses*1L);
            splitUniverses(!player1, 4, place.clone(), score.clone(), winsCount, sameUniverses*3L);
            splitUniverses(!player1, 5, place.clone(), score.clone(), winsCount, sameUniverses*6L);
            splitUniverses(!player1, 6, place.clone(), score.clone(), winsCount, sameUniverses*7L);
            splitUniverses(!player1, 7, place.clone(), score.clone(), winsCount, sameUniverses*6L);
            splitUniverses(!player1, 8, place.clone(), score.clone(), winsCount, sameUniverses*3L);
            splitUniverses(!player1, 9, place.clone(), score.clone(), winsCount, sameUniverses*1L);
        }
    }

    public static void solutionPartTwo(int[] startingPositions){
        Long[] winsCount = new Long[]{0L,0L};
        int[] currentScore = new int[]{0,0};
        int[] currentPlace = new int[]{startingPositions[0],startingPositions[1]};
        splitUniverses(true, 3, currentPlace.clone(), currentScore.clone(), winsCount, 1L);
        splitUniverses(true, 4, currentPlace.clone(), currentScore.clone(), winsCount, 3L);
        splitUniverses(true, 5, currentPlace.clone(), currentScore.clone(), winsCount, 6L);
        splitUniverses(true, 6, currentPlace.clone(), currentScore.clone(), winsCount, 7L);
        splitUniverses(true, 7, currentPlace.clone(), currentScore.clone(), winsCount, 6L);
        splitUniverses(true, 8, currentPlace.clone(), currentScore.clone(), winsCount, 3L);
        splitUniverses(true, 9, currentPlace.clone(), currentScore.clone(), winsCount, 1L);
        Arrays.sort(winsCount,Collections.reverseOrder());
        System.out.println("Part two: "+ winsCount[0]);
    }

    public static void solutionPartOne(int[] positions){
        int[] pos = positions.clone();
        int[] dice = new int[]{1,2,3};
        int fullDice = 0;
        int[] score = new int[]{0,0};
        boolean numberOne = true;
        while (true){
            pos[0] = (pos[0] + dice[0] + dice[1] + dice[2] - 1)%10 + 1;
            score[0] += pos[0];
            if (score[0] >= 1000) break;

            boolean newDie = false;
            for (int i = 0 ; i<3; i++) {
                if (dice[i]<98) dice[i]+=3;
                else {
                    if (dice[i]==100) newDie = true;
                    dice[i] = (dice[i]+3)%100;
                }
            }

            pos[1] = (pos[1] + dice[0] + dice[1] + dice[2] - 1)%10 + 1;
            score[1] += pos[1];
            if (score[1] >= 1000) {
                numberOne = false;
                break;
            }

            for (int i = 0 ; i<3; i++) {
                if (dice[i]<98) dice[i]+=3;
                else {
                    if (dice[i]==100) newDie = true;
                    dice[i] = (dice[i]+3)%100;
                }
            }
            if (newDie) fullDice+=100;
        }

        if (numberOne) System.out.println("Part one: "+score[1]*(fullDice+dice[2]));
        else System.out.println("Part one: "+score[0]*(fullDice+dice[2]));
    }

    public static void main(String[] args){
        int[] positions = new int[]{4,3};
        solutionPartOne(positions);
        solutionPartTwo(positions);
    }

}
