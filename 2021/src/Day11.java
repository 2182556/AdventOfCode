import java.nio.file.Paths;
import java.util.*;

public class Day11 {

    public static int addToCurrentFlashes(ArrayList<Integer[]> currentFlashes, Integer[] position, Integer[][] field){
        boolean contains = false;
        for (int b=0; b<currentFlashes.size(); b++){
            if (currentFlashes.get(b)[0]==position[0] && currentFlashes.get(b)[1]==position[1]){
                contains = true;
            }
        }
        if (!contains){
            field[position[0]][position[1]]++;
            if (field[position[0]][position[1]]>9){
                currentFlashes.add(position);
                field[position[0]][position[1]]=0;
                return 1;
            }
        }
        return 0;
    }

    public static void solution(ArrayList<String[]> fileInput ){
        int fieldWidth = fileInput.size();
        int fieldLength = fileInput.get(0).length;
        Integer[][] field = new Integer[fieldWidth][fieldLength];
        for (int i=0; i<fieldWidth; i++){
            for (int j=0; j<fieldLength; j++){
                field[i][j] = Integer.valueOf(fileInput.get(i)[j]);
            }
        }

        int day100Flashes = 0;
        int totalFlashes = 0;
        int completeFlash = 0;
        int day = 0;
        while (completeFlash!=fieldLength*fieldWidth){
            if (day==100) day100Flashes = totalFlashes;
            for (int i=0; i<fieldWidth; i++){
                for (int j=0; j<fieldLength; j++){
                    field[i][j]++;
                }
            }
            
            ArrayList<Integer[]> currentFlashes = new ArrayList<>();
            for (int i=0; i<fieldWidth; i++){
                for (int j=0; j<fieldLength; j++){
                    if (field[i][j]>9){
                        field[i][j] = 0;
                        currentFlashes.add(new Integer[]{i,j});
                        totalFlashes++;
                        for (int a=currentFlashes.size()-1; a<currentFlashes.size(); a++){
                            int x = currentFlashes.get(a)[0];
                            int y = currentFlashes.get(a)[1];
                            Integer[][] surrounding = {
                                {x-1, y-1},
                                {x-1, y},
                                {x-1, y+1},
                                {x, y-1},
                                {x, y+1},
                                {x+1, y-1},
                                {x+1, y},
                                {x+1, y+1}};
                            for (Integer[] location: surrounding){
                                if (location[0]>=0 && location[1]>=0 && location[0]<fieldWidth && location[1]<fieldLength){
                                    totalFlashes += addToCurrentFlashes(currentFlashes, location, field);
                                }
                            }
                        }
                    }
                }
            }
            completeFlash = currentFlashes.size();
            day++;
        }
        System.out.println("Part one: " + day100Flashes);
        System.out.println("Part two: " + day);
    }

    public static void main(String[] args) {
        String filename="day11.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> fileInput = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                fileInput.add(fileReader.nextLine().trim().split(""));
            }
            solution(fileInput);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }   
}