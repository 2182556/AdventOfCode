import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day24 {

    public static int calculateZPerInput(int previousResult, int w, int i){
        switch (i) {
            case 0: return calculateZ(previousResult, w,  12, 7);
            case 1: return calculateZ(previousResult, w,  12, 8);
            case 2: return calculateZ(previousResult, w,  13, 2);
            case 3: return calculateZ(previousResult, w,  12, 11);
            case 4: return calculateZ(previousResult, w,  -3, 6);
            case 5: return calculateZ(previousResult, w,  10, 12);
            case 6: return calculateZ(previousResult, w,  14, 14);
            case 7: return calculateZ(previousResult, w, -16, 13);
            case 8: return calculateZ(previousResult, w,  12, 15);
            case 9: return calculateZ(previousResult, w,  -8, 10);
            case 10: return calculateZ(previousResult, w,  -12, 6);
            case 11: return calculateZ(previousResult, w,  -7, 10);
            case 12: return calculateZ(previousResult, w,  -6, 8);
            case 13: return calculateZ(previousResult, w,  -11, 5);
            default: return previousResult;
        }
    }

    public static int calculateZ(int previousResult, int w, int var2, int var3){
        if (var2 > 0) return previousResult*26 + w + var3;
        else if ((previousResult%26 + var2) == w) return previousResult/26;
        else return previousResult + w + var3;
    }

    public static int[] getValidInputs(int[] largestNumber, int previousResult, int i){
        for (int n = 1; n <= 9; n++) {
//            if (i==0) System.out.println("First number at " + n);
//            if (i==1) System.out.println("Second number at " + n);
            largestNumber[i] = n;
            int currentResult = calculateZPerInput(previousResult, n, i);
            if (i < 8 ||
                    (i == 8 && currentResult < (26*26*26*26*20) && currentResult%26 >= 9 && currentResult%26 <= 17) ||
                    (i == 9 && currentResult < (26*26*26*20) && currentResult%26 >= 13 && currentResult%26 <= 21) ||
                    (i == 10 && currentResult < (26*26*20) && currentResult%26 >= 8 && currentResult%26 <= 16) ||
                    (i == 11 && currentResult <= (26*20) && currentResult%26 >= 7 && currentResult%26 <= 15) ||
                    (i == 12 && currentResult <= (20)) ){
                largestNumber =  getValidInputs(largestNumber.clone(), currentResult, i+1);
            }
            else if (currentResult == 0) {
                System.out.println("Found one!");
                for (int j : largestNumber) System.out.print(j);
                System.out.println();
                return largestNumber;
            }
        }
        return largestNumber;
    }

    public static void solution(ArrayList<ArrayList<String[]>> commands){
        getValidInputs(new int[14], 0, 0);
    }

    public static void main(String[] args){
        String filename="day24.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<ArrayList<String[]>> commands = new ArrayList<>();
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                String[] sections = line.trim().split(" ");
                if (sections[0].equals("inp")) {
                    commands.add(new ArrayList<>());
                }
                commands.get(commands.size()-1).add(sections);
            }
            solution(commands);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
