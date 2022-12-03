import java.nio.file.Paths;
import java.util.*;

class Day22 {

    static boolean hasOverlap(int[] i, int[] j){
        return (i[0] <= j[1] && i[0] >= j[0]) || (j[0] <= i[1] && j[0] >= i[0]);
    }

    static ArrayList<int[][]> getOverlappingRegions(ArrayList<int[][]> regions, int i){
        ArrayList<int[][]> overlappingRegions = new ArrayList<>();
        int[] xi = regions.get(i)[0];
        int[] yi = regions.get(i)[1];
        int[] zi = regions.get(i)[2];

        for (int j = i + 1; j < regions.size(); j++) {
            int[] xj = regions.get(j)[0];
            int[] yj = regions.get(j)[1];
            int[] zj = regions.get(j)[2];

            if (hasOverlap(xi, xj) && hasOverlap(yi, yj) && hasOverlap(zi, zj)) {
                int[][] newRange = new int[][]{ {Math.max(xi[0], xj[0]), Math.min(xi[1], xj[1])},
                                                {Math.max(yi[0], yj[0]), Math.min(yi[1], yj[1])},
                                                {Math.max(zi[0], zj[0]), Math.min(zi[1], zj[1])} };
                if (getCubeSize(regions.get(i)) == getCubeSize(newRange)) return new ArrayList<>() { {add(newRange);} };
                overlappingRegions.add(newRange);
            }
        }
        return overlappingRegions;
    }

    static long getCubeSize(int[][] cube){
        return ( ((long) Math.abs(cube[0][1] - cube[0][0]) + 1) *
                 ((long) Math.abs(cube[1][1] - cube[1][0]) + 1) *
                 ((long) Math.abs(cube[2][1] - cube[2][0]) + 1) );
    }

    static long getLeftOver(ArrayList<int[][]> cubes) {
        long total = 0;
        for (int k = 0; k < cubes.size(); k++) {
            long currentCube = getCubeSize(cubes.get(k));
            ArrayList<int[][]> overlap = getOverlappingRegions(cubes, k);

            for (int m = 0; m < overlap.size(); m++){
                for (int n = m + 1; n < overlap.size(); n++){
                    if (Arrays.deepEquals(overlap.get(n), overlap.get(m))) {
                        overlap.remove(n);
                        n--;
                    }
                }
            }
            if (overlap.size() > 1) currentCube -= getLeftOver(overlap);
            else if (overlap.size() == 1) currentCube -= getCubeSize(overlap.get(0));
            total += currentCube;
        }
        return total;
    }

    static void solutionPartTwo(ArrayList<String[]> input){
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<int[][]> regions = new ArrayList<>();
        for (String[] line : input){
            String[] section = line[1].trim().split(",");
            regions.add(new int[section.length][2]);
            for (int i=0; i<section.length; i++) {
                String[] temp = section[i].split("=");
                String[] numbers = temp[1].trim().split("\\.\\.");
                regions.get(regions.size()-1)[i] = new int[]{Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])};
            }
            commands.add(line[0].trim());
        }

        long totalSum = 0;
        for (int i = 0; i < regions.size(); i++){
            if (commands.get(i).equals("on")){
                ArrayList<int[][]> overlappingRegions = getOverlappingRegions(regions, i);
                totalSum += getCubeSize(regions.get(i)) - getLeftOver(overlappingRegions);
            }
        }
        System.out.println("Solution part two: " + totalSum);
    }

    static void solutionPartOne(ArrayList<String[]> commands){
        int[][][] field = new int[101][101][101];
        for (int i=0;i<field.length;i++){
            for (int j=0;j<field[0].length;j++){
                for (int k=0; k<field[0][0].length; k++){
                    field[i][j][k]=0;
                }
            }
        }

        for (String[] command : commands){
            String[] section = command[1].trim().split(",");
            int[][] coords = new int[section.length][2];
            for (int i=0; i<section.length; i++) {
                String[] temp = section[i].split("=");
                String[] numbers = temp[1].trim().split("\\.\\.");
                coords[i] = new int[]{Integer.parseInt(numbers[0])+50, Integer.parseInt(numbers[1])+50};
            }
            if (coords[0][0]>=0 && coords[1][0]>=0 && coords[2][0]>=0 &&
            coords[0][1]<=100 && coords[1][1]<=100 && coords[2][1]<=100) {
                for (int i=coords[0][0];i<=coords[0][1];i++){
                    for (int j=coords[1][0];j<=coords[1][1];j++){
                        for (int k=coords[2][0]; k<=coords[2][1]; k++){
                            if (command[0].trim().equals("off")){
                                field[i][j][k]=0;
                            } else {
                                field[i][j][k]=1;
                            }
                        }
                    }
                }
            }
        }
        int cubesOn = 0;
        for (int[][] ints : field) {
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    if (ints[j][k] == 1) cubesOn++;
                }
            }
        }
        System.out.println("Solution part one: " + cubesOn);
    }

    public static void main(String[] args){
        String filename="day22.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> commands = new ArrayList<>();
            while(fileReader.hasNextLine()){
                commands.add(fileReader.nextLine().trim().split(" "));
            }
            solutionPartOne(commands);
            solutionPartTwo(commands);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
