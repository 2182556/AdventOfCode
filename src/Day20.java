import java.nio.file.Paths;
import java.util.*;

public class Day20 {

    public static String binaryToPixel(String binary, String[] toBinary){
        int binaryInt = 0;
        for (int j=0; j<binary.length(); j++){
            if (binary.charAt(j) == '#') binaryInt+= Math.pow(2,binary.length()-1-j);
        }
        return toBinary[binaryInt];
    }

    public static String[][] IEA(String[][] field, String[] toBinary){
        String[][] transformed = new String[field.length+2][field[0].length+2];
        for (int i=0; i<transformed.length; i++){
            for (int j=0; j<transformed[0].length; j++){
                if (i>1 && j>1 && i<transformed.length-2 && j<transformed[0].length-2){
                    int[][] indices = {{i-1,j-1},{i-1,j},{i-1,j+1},{i,j-1},{i,j},{i,j+1},{i+1,j-1},{i+1,j},{i+1,j+1}};
                    StringBuilder newStatus = new StringBuilder();
                    for (int[] index: indices){
                        newStatus.append(field[index[0]-1][index[1]-1]);
                    }
                    transformed[i][j] = binaryToPixel(newStatus.toString(), toBinary);
                } else if (i==0 || i==1 || i==transformed.length-2 || i==transformed.length-1 ||
                        j==0 || j==1 || j==transformed[0].length-2 || j==transformed[0].length-1){
                    transformed[i][j] = binaryToPixel(field[0][0].repeat(9), toBinary);
                }
            }
        }
        return transformed;
    }

    public static void solution(String[] toBinary, ArrayList<String[]> field){
        String[][] fieldArray = new String[field.size()+4][field.get(0).length+4];
        for (int i=0; i<fieldArray.length;i++){
            for (int j=0; j<fieldArray[0].length;j++){
                if (i==0 || i==1 || i==fieldArray.length-2 || i==fieldArray.length-1 ||
                    j==0 || j==1 || j==fieldArray[0].length-2 || j==fieldArray[0].length-1){
                    fieldArray[i][j] = ".";
                } else {
                    fieldArray[i][j] = field.get(i-2)[j-2];
                }
            }
        }
        for (int i=0; i<2; i++){
            fieldArray = IEA(fieldArray, toBinary);
        }
        int lit = 0;
        for (String[] line : fieldArray) {
            for (int j = 0; j < fieldArray[0].length; j++) {
                if (line[j].equals("#")) lit++;
            }
        }
        System.out.println("Part one: " + lit);
        lit = 0;
        for (int i=2; i<50; i++){
            fieldArray = IEA(fieldArray, toBinary);
        }
        for (String[] line : fieldArray) {
            for (int j = 0; j < fieldArray[0].length; j++) {
                if (line[j].equals("#")) lit++;
            }
        }
        System.out.println("Part two: " + lit);

    }

    public static void main(String[] args){
        String filename="day20.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            String[] toBinary = fileReader.nextLine().trim().split("");
            ArrayList<String[]> field = new ArrayList<>();
            while(fileReader.hasNextLine()){
                String[] line = fileReader.nextLine().trim().split("");
                if (!line[0].isEmpty()) {
                    field.add(line);
                }
            }
            solution(toBinary, field);
//            solution(input,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
