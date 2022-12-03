import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day25 {

    public static void solution(ArrayList<String[]> input){
        String[][] field = new String[input.size()][input.get(0).length];
        for (int i=0; i<input.size(); i++){
            field[i] = input.get(i);
        }

        boolean stoppedMoving = false;
        int numberOfMoves = 0;
        while (!stoppedMoving){
            stoppedMoving = true;
            String[][] oldField = new String[field.length][];
            for (int i = 0; i < field.length; i++) {
                oldField[i] = field[i].clone();
            }
            //east first
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++){
                    if (oldField[i][j].equals(">")){
                        int y = j + 1;
                        if (y >= field[0].length) y = 0;
                        if (oldField[i][y].equals(".")) {
                            field[i][y] = oldField[i][j];
                            field[i][j] = ".";
                            stoppedMoving = false;
                        }
                    }
                }
            }
            oldField = new String[field.length][];
            for (int i = 0; i < field.length; i++) {
                oldField[i] = field[i].clone();
            }
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++){
                    if (oldField[i][j].equals("v")){
                        int x = i + 1;
                        if (x >= field.length) x = 0;
                        if (oldField[x][j].equals(".")) {
                            field[x][j] = oldField[i][j];
                            field[i][j] = ".";
                            stoppedMoving = false;
                        }
                    }
                }
            }
            numberOfMoves++;
        }
        System.out.println(numberOfMoves);
    }

    public static void main(String[] args){
        String filename="day25.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> field = new ArrayList<>();
            while(fileReader.hasNextLine()){
                field.add(fileReader.nextLine().trim().split(""));
            }
            solution(field);
//            solutionPartTwo(commands);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
