import java.nio.file.Paths;
import java.util.*;

public class Day10 {

    public static boolean containsAllCharacters(char[] first, char[] second){
        for (char two : second){
            boolean contains = false;
            for (char one : first){
                if (one==two){
                    contains = true;
                }
            }
            if (!contains){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String filename="day10.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> completeFile = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] allSymbols = line.split("");
                completeFile.add(allSymbols);
            }
            String[] startingSymbols = new String[4];
            String[] endingSymbols = new String[4];
            ArrayList<String> wrongClosers = new ArrayList<>();
            ArrayList<String[]> wrongLines = new ArrayList<>();
            startingSymbols[0] = "("; endingSymbols[0] = ")";
            startingSymbols[1] = "["; endingSymbols[1] = "]";
            startingSymbols[2] = "{"; endingSymbols[2] = "}";
            startingSymbols[3] = "<"; endingSymbols[3] = ">";
            for (int i=0; i < completeFile.size(); i++){
                ArrayList<String> unfinishedSymbols = new ArrayList<>();
                line: for (String symbol : completeFile.get(i)){
                    boolean closingSymbol = false;
                    for (int j=0; j<endingSymbols.length; j++){
                        if (symbol.equals(endingSymbols[j])){
                            closingSymbol = true;
                            if (startingSymbols[j].equals(unfinishedSymbols.get(unfinishedSymbols.size()-1))){
                                unfinishedSymbols.remove(unfinishedSymbols.size()-1);
                            } else {
                                wrongClosers.add(symbol);
                                wrongLines.add(completeFile.get(i));
                                break line;
                            }
                        }
                    }
                    if (!closingSymbol){
                        unfinishedSymbols.add(symbol);
                    }
                }
            }
            System.out.println(completeFile.size());
            completeFile.removeAll(wrongLines);
            System.out.println(completeFile.size());
            ArrayList<Long> pointsPerLine = new ArrayList<>();
            for (int i=0; i < completeFile.size(); i++){
                ArrayList<String> allStartSymbols = new ArrayList<>();
                // ArrayList<String> allEndSymbols = new ArrayList<>();
                for (String symbol : completeFile.get(i)){
                    symbolCompare: for (int j=0; j<endingSymbols.length; j++){
                        if (symbol.equals(startingSymbols[j])){
                            allStartSymbols.add(symbol);
                        } else if (symbol.equals(endingSymbols[j])){
                            for (int k=allStartSymbols.size()-1; k>=0;k--){
                                if(allStartSymbols.get(k).equals(startingSymbols[j])){
                                    // System.out.println(allStartSymbols.size());
                                    allStartSymbols.remove(k);
                                    // System.out.println(allStartSymbols.size());
                                    break symbolCompare;
                                }
                            }
                        }
                    }
                }
                long linePoints = 0;
                for (int j=allStartSymbols.size()-1; j>=0;j--){
                    linePoints*= 5;
                    for (int s=0; s<startingSymbols.length;s++){
                        if(allStartSymbols.get(j).equals(startingSymbols[s])){
                            linePoints+= s+1;
                        }
                    }
                    System.out.println(linePoints);
                }
                
                pointsPerLine.add(linePoints);
            }

            int totalSum = 0;
            for (String symbol : wrongClosers){
                if (symbol.equals(endingSymbols[0])){
                    totalSum += 3;
                } else if (symbol.equals(endingSymbols[1])){
                    totalSum += 57;
                } else if (symbol.equals(endingSymbols[2])){
                    totalSum += 1197;
                } else if (symbol.equals(endingSymbols[3])){
                    totalSum += 25137;
                }
            }
            System.out.println("Part one: " + totalSum);
            Collections.sort(pointsPerLine);
            for (int i =0; i<pointsPerLine.size();i++){
                System.out.println(pointsPerLine.get(i));
            }
            System.out.println(pointsPerLine.size());
            System.out.println("Part two: " + pointsPerLine.get((pointsPerLine.size()/2)));
            // System.out.println(pointsPerLine.size()/2 -1);
            // System.out.println("Part two: " + pointsPerLine.get(24));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    
}
