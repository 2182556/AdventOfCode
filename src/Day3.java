import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {

    public static void onesOverZeros(ArrayList<StringBuilder> lines, int index, boolean most){
        int ones = 0;
        int zeros = 0;
        for (int i = 0; i < lines.size(); i++){
            if (lines.get(i).substring(index,index+1).equals("1")){
                ones++;
            } else {
                zeros++;
            }
        }
        if (ones>=zeros){
            if (most){
                lines.removeIf(element -> (element.substring(index,index+1).equals("0")));
            } else {
                lines.removeIf(element -> (element.substring(index,index+1).equals("1")));
            }
        } else {
            if (most){
                lines.removeIf(element -> (element.substring(index,index+1).equals("1")));
            } else {
                lines.removeIf(element -> (element.substring(index,index+1).equals("0")));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String filename="day3.txt";
        ArrayList<StringBuilder> lines = new ArrayList<>();
        ArrayList<ArrayList<Character>> numbers = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                StringBuilder line = new StringBuilder();
                line.append(fileReader.nextLine().trim());
                lines.add(line);
            }
            int length = lines.get(0).length();
            for (int i = 0; i < length; i++){
                numbers.add(new ArrayList<>());
            }
            for (int i =0; i < lines.size(); i++){
                for (int j = 0; j < length; j++){
                    numbers.get(j).add((Character) lines.get(i).charAt(j));
                }
            }
            ArrayList<StringBuilder> copiedLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++){
                copiedLines.add(new StringBuilder(lines.get(i).toString()));
            }
            int k = 0;
            while (lines.size()>1){
                onesOverZeros(lines, k, true);
                k++;
            }
            int l = 0;
            while (copiedLines.size()>1){
                onesOverZeros(copiedLines, l, false);
                l++;
            }
            System.out.println(lines);
            System.out.println(copiedLines);

            int most =0;
            int least =0;
            for (int i=0; i<lines.get(0).length(); i++){
                if(lines.get(0).charAt(i)=='1'){
                    most += Math.pow(2,lines.get(0).length()-i-1);
                }
                if(copiedLines.get(0).charAt(i)=='1'){
                    least += Math.pow(2,copiedLines.get(0).length()-i-1);
                }
            }
            System.out.println(most*least);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}

    
