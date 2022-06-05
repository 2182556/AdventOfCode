import java.nio.file.Paths;
import java.util.*;

public class Day12PartOne {


    public static void solution(ArrayList<String[]> fileInput){
        ArrayList<String> startingPoints = new ArrayList<>();
        ArrayList<String> endingPoints = new ArrayList<>();
        ArrayList<String[]> connections = new ArrayList<>();
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
                        String currentCave = connections.get(k)[1];
                        if (Character.isUpperCase(currentCave.charAt(0)) || !cc.get(j).contains(currentCave)){
                            cc.add(new ArrayList<>(cc.get(j)));
                            cc.get(cc.size()-1).add(currentCave);
                        }
                    } else if (last.equals(connections.get(k)[1])){
                        String currentCave = connections.get(k)[0];
                        if (Character.isUpperCase(currentCave.charAt(0)) || !cc.get(j).contains(currentCave)){
                            cc.add(new ArrayList<>(cc.get(j)));
                            cc.get(cc.size()-1).add(currentCave);
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
        System.out.println("Part one: " + totalPaths);
    }

    public static void main(String[] args){
        String filename="day12.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> fileInput = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                fileInput.add(fileReader.nextLine().trim().split("-"));
            }
            solution(fileInput);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
