import java.nio.file.Paths;
import java.util.*;

public class Day19 {

    public static void addPossibilities(int[] firstScanner, int[] beacon, int[] beaconsPerOption, int[][] beaconVariations, int index, int d) {
        for (int a = 0; a < 24; a++) {
            if (beaconVariations[a][0] == index+1) {
                if (firstScanner[index] == beacon[0] + d) beaconsPerOption[a]++;
            } else if (beaconVariations[a][0] == -(index+1)) {
                if (firstScanner[index] == -beacon[0] + d) beaconsPerOption[a]++;
            } else if (beaconVariations[a][1] == index+1) {
                if (firstScanner[index] == beacon[1] + d) beaconsPerOption[a]++;
            } else if (beaconVariations[a][1] == -(index+1)) {
                if (firstScanner[index] == -beacon[1] + d) beaconsPerOption[a]++;
            } else if (beaconVariations[a][2] == index+1) {
                if (firstScanner[index] == beacon[2] + d) beaconsPerOption[a]++;
            } else if (beaconVariations[a][2] == -(index+1)) {
                if (firstScanner[index] == -beacon[2] + d) beaconsPerOption[a]++;
            }
        }
    }

    public static void solution(ArrayList<ArrayList<int[]>> input, boolean partOne) {
        int x = 1, y = 2, z = 3;
        int[][] beaconVariations = new int[][]{
                {x, y, z}, {x, z, -y}, {x, -y, -z}, {x, -z, y},
                {-x, y, -z}, {-x, z, y}, {-x, -y, z}, {-x, -z, -y},
                {y, x, -z}, {-y, x, z}, {z, x, y}, {-z, x, -y},
                {y, -x, z}, {-y, -x, -z}, {z, -x, -y}, {-z, -x, y},
                {y, z, x}, {-y, -z, x}, {z, -y, x}, {-z, y, x},
                {y, -z, -x}, {-y, z, -x}, {z, y, -x}, {-z, -y, -x}};

        ArrayList<int[]> info = new ArrayList<>();
        ArrayList<Integer> indexesFound = new ArrayList<>();
        indexesFound.add(0);
        info.add(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        for (int index=0; index<indexesFound.size(); index++) {
            int bottomrangex = -2000, toprangex = 2000;
            int bottomrangey = -2000, toprangey = 2000;
            int bottomrangez = -2000, toprangez = 2000;
            int i = indexesFound.get(index);
            for (int[] newRange : info){
                if (newRange[1]==i){
                    bottomrangex = newRange[2]-2000;
                    toprangex = newRange[2]+2000;
                    bottomrangey = newRange[3]-2000;
                    toprangey = newRange[3]+2000;
                    bottomrangez = newRange[4]-2000;
                    toprangez = newRange[4]+2000;
                }
            }
            for (int j=0; j<input.size(); j++) {
                if (i!=j && (!indexesFound.contains(j))) {
                    outer:
                    for (int dx = bottomrangex; dx <= toprangex; dx++) {
                        int[] beaconsPerOption = new int[24];
                        for (int a = 0; a < 24; a++) {
                            beaconsPerOption[a] = 0;
                        }

                        for (int s = 0; s < input.get(i).size(); s++) {
                            for (int t = 0; t < input.get(j).size(); t++) {
                                addPossibilities(input.get(i).get(s), input.get(j).get(t), beaconsPerOption, beaconVariations,0, dx);
//                                if (posX > 0) {
//                                    indexes0[s] = t; //tried to get the indices of matching beacons but ran into problems
//                                }
                            }
                        }

//                        int matches = 0;
//                        for (int t : indexes0) {
//                            if (t != -1) {
//                                matches++;
//                            }
//                        }
                        boolean possibleX = false;
                        for (int amount : beaconsPerOption) {
                            if (amount > 11) {
                                possibleX = true;
                                break;
                            }
                        }

                        if (possibleX) {
                            int[] beaconsPerOptionX = beaconsPerOption.clone();
//                            System.out.println("x: " + dx);
//                            System.out.println(Arrays.toString(beaconsPerOption));
                            for (int dy = bottomrangey; dy <= toprangey; dy++) {
                                for (int s = 0; s < input.get(i).size(); s++) {
                                    for (int t = 0; t < input.get(j).size(); t++) {
                                        addPossibilities(input.get(i).get(s), input.get(j).get(t), beaconsPerOption, beaconVariations,1, dy);
                                    }
                                }
                                boolean possibleY = false;
                                for (int amount : beaconsPerOption) {
                                    if (amount > 23) {
                                        possibleY = true;
                                        break;
                                    }
                                }
                                if (possibleY) {
//                                    System.out.println("y: " + dy);
//                                    System.out.println(Arrays.toString(beaconsPerOption));
                                    int[] beaconsPerOptionY = beaconsPerOption.clone();
                                    for (int dz = bottomrangez; dz <= toprangez; dz++) {
                                        for (int s = 0; s < input.get(i).size(); s++) {
                                            for (int t = 0; t < input.get(j).size(); t++) {
                                                addPossibilities(input.get(i).get(s), input.get(j).get(t), beaconsPerOption, beaconVariations,2, dz);
                                            }
                                        }
                                        boolean possibleZ = false;
                                        int[] currentOperations = new int[3];
                                        for (int e = 0; e < beaconsPerOption.length; e++) {
                                            if (beaconsPerOption[e] > 35) {
                                                currentOperations = beaconVariations[e].clone();
                                                possibleZ = true;
                                                break;
                                            }
                                        }
                                        if (possibleZ) {
//                                            System.out.println("z: " + dz);
//                                            System.out.println("i: " + i + " j: " + j);
//                                            System.out.println(Arrays.toString(beaconsPerOption));
                                            indexesFound.add(j);
                                            for (int[] singleBeacon: input.get(j)){
                                                int[] temp = singleBeacon.clone();
                                                if (currentOperations[0]==1 || currentOperations[0]==-1){
                                                    singleBeacon[0] = temp[0]*currentOperations[0] + dx;
                                                } else if (currentOperations[1]==1 || currentOperations[1]==-1){
                                                    singleBeacon[0] = temp[1]*currentOperations[1] + dx;
                                                } else if (currentOperations[2]==1 || currentOperations[2]==-1){
                                                    singleBeacon[0] = temp[2]*currentOperations[2] + dx;
                                                }
                                                if (currentOperations[0]==2 || currentOperations[0]==-2){
                                                    singleBeacon[1] = temp[0]*currentOperations[0]/2 + dy;
                                                } else if (currentOperations[1]==2 || currentOperations[1]==-2){
                                                    singleBeacon[1] = temp[1]*currentOperations[1]/2 + dy;
                                                } else if (currentOperations[2]==2 || currentOperations[2]==-2){
                                                    singleBeacon[1] = temp[2]*currentOperations[2]/2 + dy;
                                                }
                                                if (currentOperations[0]==3 || currentOperations[0]==-3){
                                                    singleBeacon[2] = temp[0]*currentOperations[0]/3 + dz;
                                                } else if (currentOperations[1]==3 || currentOperations[1]==-3){
                                                    singleBeacon[2] = temp[1]*currentOperations[1]/3 + dz;
                                                } else if (currentOperations[2]==3 || currentOperations[2]==-3){
                                                    singleBeacon[2] = temp[2]*currentOperations[2]/3 + dz;
                                                }
                                            }
                                            info.add(new int[]{i, j, dx, dy, dz, currentOperations[0], currentOperations[1], currentOperations[2]});
                                            break outer;
                                        } else {
                                            beaconsPerOption = beaconsPerOptionY.clone();
                                        }
                                    }
                                } else {
                                    beaconsPerOption = beaconsPerOptionX.clone();
                                }
                            }
                        }
                    }
                }
            }
        }

        ArrayList<int[]> allBeacons = new ArrayList<>();
        for (ArrayList<int[]> scanner : input){
            for (int[] beacon : scanner ){
                if (!has(allBeacons,beacon)) allBeacons.add(beacon);
            }
        }

        ArrayList<Integer> distances = new ArrayList<>();
        for (int i=0; i<info.size(); i++){
            for (int j=0; j<info.size(); j++){
                if (j!=i){
                    int[] first = info.get(i);
                    int[] second = info.get(j);
                    int distance = Math.abs(first[2]-second[2]) + Math.abs(first[3]-second[3]) + Math.abs(first[4]-second[4]);
                    distances.add(distance);
                }
            }
        }
        System.out.println("Part one: "+allBeacons.size());
        System.out.println("Part two: "+Collections.max(distances));
    }

    public static boolean has(ArrayList<int[]> alreadyAdded, int[] beacon ){
        for (int [] added : alreadyAdded){
            if (added[0]==beacon[0] && added[1]==beacon[1] && added[2]==beacon[2]){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        String filename="day19.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<ArrayList<int[]>> input = new ArrayList<>();
            while(fileReader.hasNextLine()){
                String[] line = fileReader.nextLine().trim().split(",");
                if (line[0].contains("scanner")){
                    input.add(new ArrayList<>());
                } else if (!line[0].isBlank()) {
                    input.get(input.size()-1).add(new int[]{Integer.parseInt(line[0]),Integer.parseInt(line[1]),Integer.parseInt(line[2])});
                }
            }
            solution(input,true);
//            solution(input,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}