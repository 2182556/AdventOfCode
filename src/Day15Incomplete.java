import java.nio.file.Paths;
import java.util.*;

public class Day15Incomplete {

    public static int[][] smallestSumArray(int[][] field){
        int[][] riskLevel = new int[field.length][field[0].length];
        riskLevel[0][0] = 0;
        for (int i=0; i<field.length; i++){
            for(int j=0; j<field[0].length; j++){
                if (i>0 && j>0){
                    if (riskLevel[i-1][j]>riskLevel[i][j-1]){
                        riskLevel[i][j]=riskLevel[i][j-1]+field[i][j];
                    } else {
                        riskLevel[i][j]=riskLevel[i-1][j]+field[i][j];
                    }
                } else if (j>0) {
                    riskLevel[i][j]=riskLevel[i][j-1]+field[i][j];
                } else if (i>0) {
                    riskLevel[i][j]=riskLevel[i-1][j]+field[i][j];
                }
            }
        }
        return riskLevel;
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
                    // System.out.print(" "+ newField[i][j]);
                    b++;
                    if (b==field[0].length){
                        b=0;
                        extray++;
                    }
                }
                // System.out.println("");
                a++;
                if (a==field.length) {
                    a=0;
                    extrax++;
                }
            }
            int[][] riskLevel = smallestSumArray(newField);
            System.out.println(riskLevel[newField.length-1][newField[0].length-1]);
        } else {
            int [][] riskLevel = smallestSumArray(field);
            System.out.println(riskLevel[field.length-1][field[0].length-1]);
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
            // System.out.print("Part one: ");
            solution(field,false);
            System.out.println("");
            // System.out.print("Part two: ");
            // solution(field,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
