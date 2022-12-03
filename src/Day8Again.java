import java.nio.file.Paths;
import java.util.*;

public class Day8Again {

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
        String filename="day8.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> completeFile = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] twoSegments = line.split(" \\| ");
                completeFile.add(twoSegments);
            }

            int totalSum = 0;
            for (int i=0; i<completeFile.size(); i++){
                String[] inputs = completeFile.get(i)[0].split(" ");
                String[] outputs = completeFile.get(i)[1].split(" ");
                ArrayList<char[]> charsInput = new ArrayList<>();
                String[] known = new String[10];
                ArrayList<char[]> lengthFive = new ArrayList<>();
                ArrayList<char[]> lengthSix = new ArrayList<>();

                for(int c=0; c<inputs.length;c++){
                    charsInput.add(inputs[c].toCharArray());
                }

                for(char[] sequence : charsInput){
                    Arrays.sort(sequence);
                    if(sequence.length==2){
                        known[1]=new String(sequence);
                    } else if(sequence.length==3) {
                        known[7]=new String(sequence);
                    } else if(sequence.length==4) {
                        known[4]=new String(sequence);
                    } else if(sequence.length==7) {
                        known[8]=new String(sequence);
                    } else if(sequence.length==5) {
                        lengthFive.add(sequence);
                    } else if(sequence.length==6) {
                        lengthSix.add(sequence);
                    }
                }
                
                for (char[] sequence : lengthFive){
                    if(containsAllCharacters(sequence,known[1].toCharArray())){
                        known[3] = new String(sequence);
                    }
                }
                for (char[] sequence : lengthSix){
                    if(containsAllCharacters(sequence,known[3].toCharArray())){
                        known[9] = new String(sequence);
                    } else if (containsAllCharacters(sequence,known[1].toCharArray()) && !containsAllCharacters(sequence,known[3].toCharArray())){
                        known[0] = new String(sequence);
                    } else {
                        known[6] = new String(sequence);
                    }
                }
                for (char[] sequence : lengthFive){
                    if (!containsAllCharacters(sequence,known[1].toCharArray())){
                        if(containsAllCharacters(known[6].toCharArray(),sequence)){
                            known[5] = new String(sequence);
                        } else {
                            known[2] = new String(sequence);
                        }
                    }
                }

                ArrayList<char[]> charsOutput = new ArrayList<>();
                String currentNumber = "";
                for(int c=0; c<outputs.length;c++){
                    charsOutput.add(outputs[c].toCharArray());
                }
                for (char[] sequence : charsOutput){
                    Arrays.sort(sequence);
                    String seq = new String(sequence);
                    for (int index=0; index<known.length; index++){
                        if (seq.equals(known[index])) currentNumber+= index;
                    }
                }
                totalSum += Integer.valueOf(currentNumber);
            }
            System.out.println("Part two: " + totalSum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
