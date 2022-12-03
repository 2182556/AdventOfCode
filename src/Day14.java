import java.nio.file.Paths;
import java.util.*;

public class Day14 {

    public static void solution(ArrayList<String> input, ArrayList<String[]> connections, boolean partOne){
        int steps = 10;
        if (!partOne) steps = 40;

        ArrayList<String> differentLetters = new ArrayList<>();
        for (String letter: input){
            if (!differentLetters.contains(letter)) differentLetters.add(letter);
        }
        
        ArrayList<String> pairs = new ArrayList<>();
        for (int i=0; i<connections.size(); i++){
            pairs.add(connections.get(i)[0]);
            if (!differentLetters.contains(connections.get(i)[1])) differentLetters.add(connections.get(i)[1]);
        }
        Long[] amountPerPair = new Long[pairs.size()];
        for (int i=0; i<amountPerPair.length; i++){
            amountPerPair[i] = (long) 0;
        }
        for (int i=1; i<input.size(); i++){
            String pair = input.get(i-1)+input.get(i);
            amountPerPair[pairs.indexOf(pair)]++;
        }

        for (int i=0; i<steps; i++){
            Long[] newAmountPerPair = new Long[pairs.size()];
            for (int j=0; j<newAmountPerPair.length; j++){
                newAmountPerPair[j] = (long) 0;
            }
            for (int j=0; j<amountPerPair.length; j++){
                if (!amountPerPair[j].equals( (long) 0 )){
                    newAmountPerPair[pairs.indexOf(pairs.get(j).charAt(0)+connections.get(j)[1])]+=amountPerPair[j];
                    newAmountPerPair[pairs.indexOf(connections.get(j)[1]+pairs.get(j).charAt(1))]+=amountPerPair[j];
                }
            }
            amountPerPair = newAmountPerPair;
        }

        Long[] amountPerLetter = new Long[differentLetters.size()];
        for (int i=0; i<amountPerLetter.length; i++){
            amountPerLetter[i] = (long) 0;
            if (differentLetters.get(i).equals(input.get(0)) || 
                differentLetters.get(i).equals(input.get(input.size()-1))){
                amountPerLetter[i] = (long) 1;
            } 
        }
        for (String pair : pairs){
            amountPerLetter[differentLetters.indexOf(pair.substring(0, 1))]+= (amountPerPair[pairs.indexOf(pair)]) ;
            amountPerLetter[differentLetters.indexOf(pair.substring(1))]+= (amountPerPair[pairs.indexOf(pair)]) ;
        }
        System.out.print( (Collections.max(Arrays.asList(amountPerLetter))/2-Collections.min(Arrays.asList(amountPerLetter))/2));
    }

    public static void main(String[] args){
        String filename="day14.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String> input = new ArrayList<>();
            ArrayList<String[]> connections = new ArrayList<>();
            String[] firstLine = fileReader.nextLine().trim().split("");
            for (String letter : firstLine){
                input.add(letter);
            }
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (!line.isEmpty()) connections.add(line.split(" -> "));
            }
            System.out.print("Part one: ");
            solution(input,connections,true);
            System.out.println("");
            System.out.print("Part two: ");
            solution(input,connections,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}