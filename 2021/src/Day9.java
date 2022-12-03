import java.nio.file.Paths;
import java.util.*;

public class Day9 {

    public static void addToBasin(ArrayList<Integer[]> basin, Integer[] position){
        boolean contains = false;
        for (int b=0; b<basin.size(); b++){
            if (basin.get(b)[0]==position[0] && basin.get(b)[1]==position[1]){
                contains = true;
            }
        }
        if (!contains) basin.add(position);
    }

    public static void solution(ArrayList<String[]> fileInput ){
        int fieldWidth = fileInput.size();
        int fieldLength = fileInput.get(0).length;
        Integer[][] field = new Integer[fieldWidth][fieldLength];
        for (int i=0; i<fieldWidth; i++){
            for (int j=0; j<fieldLength; j++){
                field[i][j] = Integer.valueOf(fileInput.get(i)[j]);
            }
        }

        int riskLevel = 0;
        ArrayList<Integer> basinSizes = new ArrayList<>();
        for (int i=0; i<fieldWidth; i++){
            for (int j=0; j<fieldLength; j++){
                int fieldNumber = field[i][j];
                boolean smallest = true;
                if (i>0 && fieldNumber>=field[i-1][j]) smallest = false; 
                if (j>0 && fieldNumber>=field[i][j-1]) smallest = false; 
                if (i<fieldWidth-1 && fieldNumber>=field[i+1][j]) smallest = false; 
                if (j<fieldLength-1 && fieldNumber>=field[i][j+1]) smallest = false; 

                if (smallest){
                    riskLevel += 1 + field[i][j];

                    ArrayList<Integer[]> inBasin = new ArrayList<>();
                    inBasin.add(new Integer[]{i,j});
                    for (int a=0; a<inBasin.size(); a++){
                        int x = inBasin.get(a)[0];
                        int y = inBasin.get(a)[1];
                        if (x>0 && field[x][y]<field[x-1][y] && field[x-1][y]!=9) addToBasin(inBasin,new Integer[]{x-1,y});
                        if (y>0 && field[x][y]<field[x][y-1] && field[x][y-1]!=9) addToBasin(inBasin,new Integer[]{x,y-1});
                        if (x<fieldWidth-1 && field[x][y]<field[x+1][y] && field[x+1][y]!=9) addToBasin(inBasin,new Integer[]{x+1,y});
                        if (y<fieldLength-1 && field[x][y]<field[x][y+1] && field[x][y+1]!=9) addToBasin(inBasin,new Integer[]{x,y+1});
                    }
                    basinSizes.add(inBasin.size());
                }
            }
        }
        System.out.println("Part one: " + riskLevel);
        Collections.sort(basinSizes, Collections.reverseOrder());
        System.out.println("Part two: " + basinSizes.get(0)*basinSizes.get(1)*basinSizes.get(2));
    }

    public static void main(String[] args) {
        String filename="day9.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> fileInput = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                fileInput.add(fileReader.nextLine().trim().split(""));
            }
            solution(fileInput);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }   
}
