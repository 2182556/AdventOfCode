import java.nio.file.Paths;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) {
        String filename="day6.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            long[][] allFish = new long[9][2];
            for (int i=0; i<allFish.length;i++){ allFish[i][0] = i; }
            
            String[] line = fileReader.nextLine().trim().split(",");
            for (String days : line){
                allFish[Integer.valueOf(days)][1]++;
            }

            int amountOfDays = 256;
            for (int j=0; j<amountOfDays; j++){
                long amountOfNewFish = 0;
                for (int i=0; i<allFish.length;i++){
                    allFish[i][0]--;
                    if (allFish[i][0]==-1){
                        amountOfNewFish = allFish[i][1];
                        allFish[i][0]=8;
                    } 
                }
                for (int k=0; k<allFish.length;k++){
                    if (allFish[k][0]==6){ allFish[k][1]+=amountOfNewFish;}
                }
            }
            long numberOfFish = 0;
            for (int i=0; i<allFish.length;i++){ numberOfFish += allFish[i][1]; }
            System.out.println(numberOfFish);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}