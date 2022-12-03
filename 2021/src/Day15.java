import java.nio.file.Paths;
import java.util.*;

public class Day15 {

    public static int shortestPath(int[][] field){
        int[][] riskLevel = new int[field.length][field[0].length];
        int[][] neighbours = new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0,0});
        while (queue.size() > 0) {
            int[] current = queue.poll();
            for (int[] neighbour : neighbours) {
                int[] next = new int[]{current[0] + neighbour[0], current[1] + neighbour[1]};
                if (next[0] >= 0 && next[0] < field.length && next[1] >= 0 && next[1] < field[0].length) {
                    int risk = riskLevel[current[0]][current[1]] + field[next[0]][next[1]];
                    if (risk < riskLevel[next[0]][next[1]] || riskLevel[next[0]][next[1]] == 0) {
                        riskLevel[next[0]][next[1]] = risk;
                        queue.remove(next);
                        queue.add(next);
                    }
                }
            }
        }
        return riskLevel[field.length-1][field[0].length-1];
    }

    public static void solution(ArrayList<String[]> arrayField, boolean partOne){
        int[][] field = new int[arrayField.size()][arrayField.get(0).length];
        for (int i=0; i<arrayField.size(); i++){
            for (int j=0; j<arrayField.get(0).length; j++){
                field[i][j] = Integer.parseInt(arrayField.get(i)[j]);
            }
        }
        if (!partOne){
            int[][] newField = new int[field.length*5][field[0].length*5];
            int a = 0, b = 0;
            int extrax = 0, extray = 0;
            for (int i=0; i<newField.length; i++){
                extray=0;
                for (int j=0; j<newField[0].length; j++){
                    newField[i][j] = field[a][b]+extray+extrax;
                    if (newField[i][j]>9) {
                        newField[i][j]-=9;
                    }
                    b++;
                    if (b==field[0].length){
                        b=0;
                        extray++;
                    }
                }
                a++;
                if (a==field.length) {
                    a=0;
                    extrax++;
                }
            }
            System.out.println("Part two: " + shortestPath(newField));
        } else {
            System.out.println("Part one: " + shortestPath(field));
        }
        

    }

    public static void main(String[] args){
        String filename="day15.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> field = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (!line.isEmpty()) field.add(line.split(""));
            }
            solution(field,true);
            solution(field,false);
            System.out.println("");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
