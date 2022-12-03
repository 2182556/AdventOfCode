import java.nio.file.Paths;
import java.util.*;

public class Day12 {

    public static boolean canBeAdded(boolean partOne, ArrayList<String> lowerCases, ArrayList<String> ccj, String currentCave){
        if (Character.isUpperCase(currentCave.charAt(0)) || (partOne && !ccj.contains(currentCave))){
            return true;
        } else if (!partOne) {
            int[] lowerCaseCounts = new int[lowerCases.size()];
            int currentCaveIndex = -1;
            for (int i=0; i<ccj.size(); i++){
                if (Character.isLowerCase(ccj.get(i).charAt(0))){
                    for (int j=0; j<lowerCases.size(); j++){
                        if (ccj.get(i).equals(lowerCases.get(j))) lowerCaseCounts[j]++;
                        if (lowerCases.get(j).equals(currentCave)) currentCaveIndex = j;
                    }
                }
            }
            boolean caveVisitedTwice = Arrays.stream(lowerCaseCounts).anyMatch(x -> x==2);
            if ( !caveVisitedTwice || (caveVisitedTwice && lowerCaseCounts[currentCaveIndex]==0) ) return true;
        }
        return false;
    }


    public static int solution(ArrayList<String[]> fileInput, boolean partOne){
        ArrayList<String> startingPoints = new ArrayList<>(), endingPoints = new ArrayList<>();
        ArrayList<String[]> connections = new ArrayList<>();
        ArrayList<String> lowerCases = new ArrayList<>();
        for (int i=0; i<fileInput.size(); i++){
            if (fileInput.get(i)[0].equals("start")){
                startingPoints.add(fileInput.get(i)[1]);
            } else if (fileInput.get(i)[1].equals("start")){
                startingPoints.add(fileInput.get(i)[0]);
            } else if (fileInput.get(i)[0].equals("end")){
                endingPoints.add(fileInput.get(i)[1]);
            } else if (fileInput.get(i)[1].equals("end")){
                endingPoints.add(fileInput.get(i)[0]);
            } else {
                connections.add(fileInput.get(i));
                if (Character.isLowerCase(fileInput.get(i)[0].charAt(0))){
                    if (!lowerCases.contains(fileInput.get(i)[0])) lowerCases.add(fileInput.get(i)[0]);
                }
                if (Character.isLowerCase(fileInput.get(i)[1].charAt(0))){
                    if (!lowerCases.contains(fileInput.get(i)[1])) lowerCases.add(fileInput.get(i)[1]);
                }
            }
        }

        int totalPaths = 0;
        for (int i=0; i<startingPoints.size();i++){
            String start = startingPoints.get(i);
            ArrayList<ArrayList<String>> cc = new ArrayList<>();
            cc.add(new ArrayList<>());
            cc.get(0).add(start);
            
            for (int j=0; j<cc.size(); j++){
                String last = cc.get(j).get(cc.get(j).size()-1);
                for (int k=0; k<connections.size(); k++){
                    if (last.equals(connections.get(k)[0])){
                        String nextCave = connections.get(k)[1];
                        if (canBeAdded(partOne, lowerCases, cc.get(j), nextCave)){
                            //check if in end?
                            cc.add(new ArrayList<>(cc.get(j)));
                            cc.get(cc.size()-1).add(nextCave);
                        }
                    } else if (last.equals(connections.get(k)[1])){
                        String nextCave = connections.get(k)[0];
                        if (canBeAdded(partOne, lowerCases, cc.get(j), nextCave)){
                            cc.add(new ArrayList<>(cc.get(j)));
                            cc.get(cc.size()-1).add(nextCave);
                        }
                    }
                }
            }
            for (int j=0; j<cc.size();j++){
                for (int k=0; k<endingPoints.size(); k++){
                    if (cc.get(j).get(cc.get(j).size()-1).equals(endingPoints.get(k))){
                        totalPaths++;
                    }
                }
            }
        }
        return totalPaths;
    }

    public static void main(String[] args){
        String filename="day12.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> fileInput = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                fileInput.add(fileReader.nextLine().trim().split("-"));
            }
            System.out.println("Part one: " + solution(fileInput,true));
            System.out.println("Part two: " + solution(fileInput,false));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}