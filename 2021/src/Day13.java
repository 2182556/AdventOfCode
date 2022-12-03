import java.nio.file.Paths;
import java.util.*;

public class Day13 {

    public static void solution(ArrayList<String[]> fieldValues, ArrayList<String[]> folds, boolean partOne){
        ArrayList<Integer> xs = new ArrayList<>(), ys = new ArrayList<>(); 
        for (int i=0; i<fieldValues.size(); i++){
            xs.add(Integer.parseInt(fieldValues.get(i)[0]));
            ys.add(Integer.parseInt(fieldValues.get(i)[1]));
        }
        String[][] field = new String[Collections.max(xs)+1][Collections.max(ys)+1];
        for (int i=0; i<field.length; i++){
            for (int j=0; j<field[0].length; j++){
                field[i][j]=" ";
            }
        }
        for (int k=0; k<xs.size();k++){
            field[xs.get(k)][ys.get(k)]="#";
        }

        int foldLineY = 0;
        int foldLineX = 0;
        int iterations = 0;
        for (String[] fold: folds){
            if (partOne && iterations==1) break;
            if (fold[0].equals("y")){
                foldLineY = Integer.parseInt(fold[1]);
                for (int j=foldLineY+1; j<field[0].length; j++){
                    for (int i= 0; i<field.length; i++){
                        if (field[i][j].equals("#")){
                            field[i][j]=" ";
                            field[i][foldLineY-(j-foldLineY)]="#";
                        }
                    }
                }
            } else if (fold[0].equals("x")){
                foldLineX = Integer.parseInt(fold[1]);
                for (int i=foldLineX+1; i<field.length; i++){
                    for (int j= 0; j<field[0].length; j++){
                        if (field[i][j].equals("#")){
                            field[i][j]=" ";
                            field[foldLineX-(i-foldLineX)][j]="#";
                        }
                    }
                }
            }
            iterations++;
        }

        if (partOne){
            int dotCount = 0;
            for (int i=0; i<field.length; i++){
                for (int j=0; j<field[0].length; j++){
                    if (field[i][j].equals("#")) dotCount++;
                }
            }
            System.out.println("Part one: " + dotCount);
        } else {
            System.out.println("Part two:");
            for (int j=0;j<foldLineY;j++){
                for (int i=0; i<foldLineX;i++){
                    System.out.print(field[i][j]+" ");
                }
                System.out.println("");
            }
        }
    }

    public static void main(String[] args){
        String filename="day13.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> field = new ArrayList<>();
            ArrayList<String[]> folds = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty()) break;
                field.add(line.split(","));
            }
            while(fileReader.hasNextLine()){
                String[] fold = fileReader.nextLine().trim().split("fold along ");
                folds.add(fold[1].split("="));
            }
            solution(field,folds,true);
            solution(field,folds,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
