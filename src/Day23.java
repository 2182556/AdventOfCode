import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day23 {

    static int[] dest = {2, 4, 6, 8};
    static char[] letters = {'A','B','C','D'};
    static int[] energy = {1,10,100,1000};

    public static boolean canMoveToDestination(char[][] diagram, int[][] ps, int pos) {
        int[] p = ps[pos];
        int wantedDest = dest[(int) Math.floor(pos/2)];
        if ((diagram[1][wantedDest] == '.') &&
                (diagram[2][wantedDest] == '.' || diagram[2][wantedDest] == letters[(int) pos/2])) {
            if (p[1] > wantedDest) {
                for (int i = p[1]-1; i > wantedDest; i--){
                    if (!(diagram[0][i] == '.' || diagram[0][i] == '-')) return false;
                }
            } else {
                for (int i = p[1] + 1; i < wantedDest; i++){
                    if (!(diagram[0][i] == '.' || diagram[0][i] == '-')) return false;
                }
            }
            if (p[0] == 2) {
                return diagram[1][p[1]] == '.';
            }
            return true;
        }
        return false;
    }

    public static boolean canMove(char[][] diagram, int[][] ps, int pos) {
        int[] p = ps[pos];
        if (p[1] == 0) return canMoveToDestination(diagram, ps, pos);
        if (diagram[p[0]-1][0] == '.' || diagram[p[0]+1][0] == '.') {
            if (p[1] == 2) {
                return diagram[p[0]][1] == '.';
            } else return true;
        }
        return false;
    }

    public static long moveToDestination(long shortestPath, long currentPath, char[][] cD, int[][] ps, int[] found, int pos, int[] dest){
        char[][] newD = new char[cD.length][cD[0].length];
        for (int i=0; i< cD.length; i++) {
            newD[i] = cD[i].clone();
        }
        int[] p = ps[pos];
        newD[dest[0]][dest[1]] = cD[p[0]][p[1]];
        newD[p[0]][p[1]] = '.';
        ps[pos] = dest;

        int move = Math.abs(p[1] - dest[1]) + p[0] + dest[0];
        move *= energy[(int) Math.floor(pos/2)];
        currentPath += move;
        if (shortestPath <= currentPath) return shortestPath;
        boolean allFound = true;
        for (int i : found)
            if (i == 0) {
                allFound = false;
                break;
            }
        if (!allFound) {
            for (int j = 0; j < ps.length; j++) {
                if (found[j] == 0){
                    shortestPath = findMove(shortestPath, currentPath, newD, ps.clone(), found.clone(), j);
                }
            }
        } else {
//            System.out.println("Shortest path found: " + shortestPath);
//            System.out.println("path length: " + currentPath);
            return currentPath;
        }
        return shortestPath;
    }

    public static long findMove(long shortestPath, long currentPath, char[][] diagram, int[][] ps, int[] found, int pos){
        char[][] cloned = new char[diagram.length][diagram[0].length];
        for (int i=0; i< diagram.length; i++) {
            cloned[i] = diagram[i].clone();
        }
        int[] p = ps[pos];
        int wantedDest = dest[(int) Math.floor(pos/2)];
        if (canMoveToDestination(cloned, ps.clone(), pos)) {
            int[] newFound = found.clone();
            newFound[pos] = 1;
            if (diagram[2][wantedDest]== '.') {
                shortestPath = moveToDestination(shortestPath, currentPath, cloned, ps.clone(), newFound, pos, new int[]{2, wantedDest});
            } else {
                shortestPath = moveToDestination(shortestPath, currentPath, cloned, ps.clone(), newFound, pos, new int[]{1, wantedDest});
            }
        }
        else if ( (p[0] == 1) || (p[0] == 2 && diagram[1][p[1]] == '.') ) {
            for (int i = p[1]-1; i >= 0; i--) {
                if (diagram[0][i] == '.'){
                    shortestPath = moveToDestination(shortestPath, currentPath, cloned, ps.clone(), found.clone(), pos, new int[]{0, i});
                } else if (diagram[0][i] != '-'){
                    //letter!
                    break;
                }
            }
            for (int i = (p[1]+1); i < diagram[0].length; i++) {
                if (diagram[0][i] == '.'){
                    shortestPath = moveToDestination(shortestPath, currentPath, cloned, ps.clone(), found.clone(), pos, new int[]{0, i});
                } else if (diagram[0][i] != '-'){
                    //letter!
                    break;
                }
            }
        }
        System.out.println("Shortest path: " + shortestPath);
        return shortestPath;
    }

    public static void solutionPartOne(){
//        char[][] diagram = new char[][]{   {'.', '.', '-', '.', '-', '.', '-', '.','-', '.', '.'},
//                {'-', '-', 'B', '-', 'C', '-', 'B', '-', 'D', '-', '-'},
//                {'-', '-', 'A', '-', 'D', '-', 'C', '-', 'A', '-', '-'} };
//        int[][] ps = { {2,2}, {2,8}, {1,2}, {1,6}, {1,4}, {2,6}, {2,4}, {1,8} };
        char[][] diagram = new char[][]{   {'.', '.', '-', '.', '-', '.', '-', '.','-', '.', '.'},
                {'-', '-', 'A', '-', 'C', '-', 'C', '-', 'D', '-', '-'},
                {'-', '-', 'B', '-', 'D', '-', 'A', '-', 'B', '-', '-'} };
        int[][] ps = { {1,2}, {2,6}, {2,2}, {2,8}, {1,4}, {1,6}, {2,4}, {1,8} };

        int[] found = { 0, 0, 0, 0, 0, 0, 0, 0};
        long shortestPath = Integer.MAX_VALUE;
        for (int i = 0; i < ps.length; i++){
            if (found[i] == 0){
                shortestPath = findMove(shortestPath, 0, diagram.clone(), ps.clone(), found.clone(), i);
            }

        }
        System.out.println("Part one: " + shortestPath);

    }

    public static boolean canMoveToHallwayPartTwo(char[][] diagram, int[][][] ps, int[] pos) {
        int[] p = ps[pos[0]][pos[1]];
        if (p[0] == 0) return false;
        for (int i=p[0]-1; i>=1; i--) {
            if (diagram[i][p[1]] != '.') return false;
        }
        return true;
    }

    public static boolean canMoveToDestinationPartTwo(char[][] diagram, int[][][] ps, int[] pos) {
        int[] p = ps[pos[0]][pos[1]];
        int wantedDest = dest[pos[0]];
        for (int i = 1; i <= 4; i++) {
            if (!(diagram[i][wantedDest] == '.' || diagram[i][wantedDest] == letters[pos[0]])){
                return false;
            }
        }

        if (p[1] > wantedDest) {
            for (int i = p[1]-1; i > wantedDest; i--){
                if (!(diagram[0][i] == '.' || diagram[0][i] == '-')) return false;
            }
        } else {
            for (int i = p[1] + 1; i < wantedDest; i++){
                if (!(diagram[0][i] == '.' || diagram[0][i] == '-')) return false;
            }
        }
        if (p[0] >= 2) {
            for (int j = p[0] - 1; j >= 1; j--) {
                if (diagram[j][p[1]] != '.') return false;
            }
        }
        return true;
    }

    public static long moveToDestinationPartTwo(long shortestPath, long currentPath, char[][] cD, int[][][] ps, int[][] found, int[] pos, int[] dest){
        int[] p = ps[pos[0]][pos[1]];
        cD[dest[0]][dest[1]] = cD[p[0]][p[1]];
        cD[p[0]][p[1]] = '.';
        ps[pos[0]][pos[1]] = dest;

        int move = Math.abs(p[1] - dest[1]) + p[0] + dest[0];
        move *= energy[pos[0]];
        currentPath += move;
        if (shortestPath <= currentPath) return shortestPath;
        boolean allFound = true;
        for (int[] i : found)
            for (int j : i){
                if (j == 0) {
                    allFound = false;
                    break;
                }
            }

        if (!allFound) {
            for (int j = 0; j < ps.length; j++) {
                for (int k =0; k < ps[j].length; k++) {
                    if (found[j][k] == 0){
                        int[][][] psCloned = new int[ps.length][ps[0].length][ps[0][0].length];
                        for (int n=0; n< ps.length; n++) {
                            for (int m=0; m< ps[0].length; m++) {
                                psCloned[n][m] = ps[n][m].clone();
                            }
                        }
                        char[][] clonedD = new char[cD.length][cD[0].length];
                        for (int i=0; i< cD.length; i++) {
                            clonedD[i] = cD[i].clone();
                        }
                        int[] destination = new int[] {j,k};
                        if (canMoveToHallwayPartTwo(clonedD,psCloned,destination) || canMoveToDestinationPartTwo(clonedD, psCloned,destination)){
                            shortestPath = findMovePartTwo(shortestPath, currentPath, clonedD, psCloned, found, destination);
                        }
                    }
                }

            }
        } else {
            System.out.println("Path length: " + currentPath);
            return currentPath;
        }
        return shortestPath;
    }

    public static long findMovePartTwo(long shortestPath, long currentPath, char[][] diagram, int[][][] ps, int[][] found, int[] pos){
        int[] p = ps[pos[0]][pos[1]];
        int wantedDest = dest[pos[0]];
        if (canMoveToDestinationPartTwo(diagram, ps, pos)) {
            int[][] newFound = new int[found.length][found[0].length];
            for (int i=0; i< found.length; i++) {
                newFound[i] = found[i].clone();
            }
            newFound[pos[0]][pos[1]] = 1;
            for (int i=4; i>=1; i--) {
                if (diagram[i][wantedDest] == '.') {
                    char[][] cloned = new char[diagram.length][diagram[0].length];
                    for (int n=0; n< diagram.length; n++) {
                        cloned[n] = diagram[n].clone();
                    }
                    int[][][] psCloned = new int[ps.length][ps[0].length][ps[0][0].length];
                    for (int n=0; n< ps.length; n++) {
                        for (int m=0; m< ps[0].length; m++) {
                            psCloned[n][m] = ps[n][m].clone();
                        }
                    }
                    shortestPath = moveToDestinationPartTwo(shortestPath, currentPath, cloned, psCloned, newFound, pos, new int[]{i, wantedDest});
                    break;
                }
            }
        }
        else if ((p[0] != 0) && canMoveToHallwayPartTwo(diagram, ps, pos)){
            for (int i = p[1]-1; i >= 0; i--) {
                if (diagram[0][i] == '.'){
                    char[][] cloned = new char[diagram.length][diagram[0].length];
                    for (int n=0; n< diagram.length; n++) {
                        cloned[n] = diagram[n].clone();
                    }
                    int[][][] psCloned = new int[ps.length][ps[0].length][ps[0][0].length];
                    for (int n=0; n< ps.length; n++) {
                        for (int m=0; m< ps[0].length; m++) {
                            psCloned[n][m] = ps[n][m].clone();
                        }
                    }
                    shortestPath = moveToDestinationPartTwo(shortestPath, currentPath, cloned, psCloned, found, pos, new int[]{0, i});
                } else if (diagram[0][i] != '-'){
                    //letter!
                    break;
                }
            }
            for (int i = (p[1]+1); i < diagram[0].length; i++) {
                if (diagram[0][i] == '.'){
                    char[][] cloned = new char[diagram.length][diagram[0].length];
                    for (int n=0; n< diagram.length; n++) {
                        cloned[n] = diagram[n].clone();
                    }
                    int[][][] psCloned = new int[ps.length][ps[0].length][ps[0][0].length];
                    for (int n=0; n< ps.length; n++) {
                        for (int m=0; m< ps[0].length; m++) {
                            psCloned[n][m] = ps[n][m].clone();
                        }
                    }
                    shortestPath = moveToDestinationPartTwo(shortestPath, currentPath, cloned, psCloned, found, pos, new int[]{0, i});
                } else if (diagram[0][i] != '-'){
                    //letter!
                    break;
                }
            }
        }
//        System.out.println("Shortest path: " + shortestPath);
        return shortestPath;
    }

    public static void solutionPartTwo(){
        char[][] diagram = new char[][]{   {'.', '.', '-', '.', '-', '.', '-', '.','-', '.', '.'},
                {'-', '-', 'A', '-', 'C', '-', 'C', '-', 'D', '-', '-'},
                {'-', '-', 'D', '-', 'C', '-', 'B', '-', 'A', '-', '-'},
                {'-', '-', 'D', '-', 'B', '-', 'A', '-', 'C', '-', '-'},
                {'-', '-', 'B', '-', 'D', '-', 'A', '-', 'B', '-', '-'} };
        int[][][] ps = { { {1,2}, {3,6}, {4,6}, {2,8} }, { {4,2}, {4,8}, {3,4}, {2,6} },
                { {1,4}, {1,6}, {2,4}, {3,8} }, { {4,4}, {1,8}, {2,2}, {3,2} } };
        int[][] found = { {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
//        char[][] diagram = new char[][]{   {'.', '.', '-', '.', '-', '.', '-', '.','-', '.', '.'},
//                {'-', '-', 'B', '-', 'C', '-', 'B', '-', 'D', '-', '-'},
//                {'-', '-', 'D', '-', 'C', '-', 'B', '-', 'A', '-', '-'},
//                {'-', '-', 'D', '-', 'B', '-', 'A', '-', 'C', '-', '-'},
//                {'-', '-', 'A', '-', 'D', '-', 'C', '-', 'A', '-', '-'} };
//        int[][][] ps = { { {4,2}, {3,6}, {2,8}, {4,8} }, { {1,2}, {1,6}, {3,4}, {2,6} },
//                { {1,4}, {4,6}, {2,4}, {3,8} }, { {2,2}, {3,2}, {4,4}, {1,8} } };
//        int[][] found = { {1, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        long shortestPath = Integer.MAX_VALUE;
        for (int i = 0; i < ps.length; i++){
            for (int j = 0; j<ps[i].length; j++) {
                if (found[i][j] == 0 && ps[i][j][0] == 1){
                    shortestPath = findMovePartTwo(shortestPath, 0, diagram, ps, found, new int[] {i,j});
                }
            }
        }

        System.out.println("Part two: " + shortestPath);
    }


    public static void main(String[] args){
//        String filename="day23.txt";
//        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
//
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        solutionPartOne();
        solutionPartTwo();
    }
}
