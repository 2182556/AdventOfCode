import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 {

    public static void main(String[] args) throws Exception {
        String filename="day1.txt";
        ArrayList<Integer> numbers = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                numbers.add(Integer.valueOf(fileReader.nextLine().trim()));
            }

            ArrayList<Integer> sums = new ArrayList<>();
            for (int i =0; i<numbers.size();i++){
                sums.add(numbers.get(i));
                if (i>0) sums.set(i-1,sums.get(i-1)+numbers.get(i));
                if (i>1) sums.set(i-2,sums.get(i-2)+numbers.get(i));
            }
            int largerPartOne = 0, largerPartTwo = 0;
            for (int i=1; i<numbers.size(); i++){
                if (numbers.get(i)>numbers.get(i-1)) largerPartOne++;
            }
            for (int i=1; i<sums.size(); i++){
                if (sums.get(i)>sums.get(i-1)) largerPartTwo++;
            }
            System.out.println("Part one: " + largerPartOne);
            System.out.println("Part two: " + largerPartTwo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
