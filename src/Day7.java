import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) throws Exception {
        String filename="day7.txt";
        ArrayList<Integer> positions = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] lineSplit = line.split(",");
                for (String position : lineSplit){
                    positions.add(Integer.valueOf(position));
                } 
            }
            ArrayList<Integer> distances = new ArrayList<>();
            for (int i =0; i < Collections.max(positions); i++){
                int currentSum = 0;
                for (int j=0; j < positions.size();j++){
                    int distance = Math.abs(positions.get(j) - i);
                    currentSum+= (distance*distance + distance)/2;
                }
                distances.add(currentSum);
            }
            System.out.println(Collections.min(distances));



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    
}
