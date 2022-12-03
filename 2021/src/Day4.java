import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) throws Exception {
        String filename="day4.txt";
        ArrayList<String> bingoNumbers = new ArrayList<>();
        ArrayList<String[][]> allCards = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            String[] allNumbers = fileReader.nextLine().trim().split(",");
            for (int s=0; s<allNumbers.length; s++){
                bingoNumbers.add(allNumbers[s]);
            }

            int i = -1, j = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (!line.isEmpty()){
                    allCards.get(i)[j] = line.replaceAll("\\s+", " ").split(" ");
                    j++;
                } else {
                    allCards.add(new String[5][5]);
                    i++;
                    j=0;
                }
            }
            boolean[] whichWon = new boolean[allCards.size()];
            outer: while(true){
                for(String number : bingoNumbers){
                    for (String[][] bingoCard : allCards){
                        for (int x=0; x<bingoCard.length; x++){
                            for(int y=0; y<bingoCard.length; y++){
                                if (bingoCard[x][y].equals(number)){
                                    bingoCard[x][y] = "-1";
                                    boolean rowWin = true, columnWin = true;
                                    for (int a=0; a<bingoCard.length; a++){
                                        if (!bingoCard[x][a].equals("-1")) rowWin = false;
                                        if (!bingoCard[a][y].equals("-1")) columnWin = false;
                                    }
                                    if (rowWin || columnWin){
                                        boolean firstWin = true;
                                        for (boolean item : whichWon){
                                            if (item) firstWin = false;
                                        }
                                        whichWon[allCards.indexOf(bingoCard)] = true;
                                        boolean allWon = true;
                                        for (boolean item : whichWon){
                                            if (!item) allWon = false;
                                        }
                                        int sum = 0;
                                        String[] tempArray = Stream.of(bingoCard)
                                            .flatMap(Stream::of)
                                            .toArray(String[]::new);
                                        for (String uncalledNumber: tempArray){
                                            if (!uncalledNumber.equals("-1")){
                                                sum += Integer.valueOf(uncalledNumber);
                                            }
                                        }
                                        if (firstWin){
                                            System.out.println("First winning number: " + sum * Integer.valueOf(number));
                                        }
                                        if (allWon){
                                            System.out.println("Last winning number: " + sum * Integer.valueOf(number));
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("No one won?");
                break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
