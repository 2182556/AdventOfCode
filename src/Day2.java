import java.nio.file.Paths;
// import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {

    public static void main(String[] args) throws Exception {
        String filename="day2.txt";
        // ArrayList<Integer> numbers = new ArrayList<>();
        int positionx = 0;
        int positiony = 0;
        int aim = 0;
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] lineSplit = line.split(" ");
                if (lineSplit[0].equals("forward")){
                    positionx += Integer.valueOf(lineSplit[1]);
                    positiony += aim*Integer.valueOf(lineSplit[1]);
                } else if (lineSplit[0].equals("down")){
                    aim += Integer.valueOf(lineSplit[1]);
                } else if (lineSplit[0].equals("up")){
                    aim -= Integer.valueOf(lineSplit[1]);
                }
            }
            System.out.println(positionx + " " + positiony);
            System.out.println(positionx*positiony);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}
