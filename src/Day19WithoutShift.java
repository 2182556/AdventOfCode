import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day19WithoutShift {

    public static int addXPossibilities(int[] firstScanner, int[] beacon, int[] beaconsPerOption, int first, int d) {
        int amount = 0;
        if (first == 0) {
            if (firstScanner[first] == beacon[0] + d) {
                for (int i = 0; i < 4; i++) beaconsPerOption[i]++;
                amount++;
            }
            if (firstScanner[first] == -beacon[0] + d) {
                for (int i = 4; i < 8; i++) beaconsPerOption[i]++;
                amount++;
            }
            if (firstScanner[first] == beacon[1] + d) {
                for (int i = 8; i < 12; i++) beaconsPerOption[i]++;
                amount++;
            }
            if (firstScanner[first] == -beacon[1] + d) {
                for (int i = 12; i < 16; i++) beaconsPerOption[i]++;
                amount++;
            }
            if (firstScanner[first] == beacon[2] + d) {
                for (int i = 16; i < 20; i++) beaconsPerOption[i]++;
                amount++;
            }
            if (firstScanner[first] == -beacon[2] + d) {
                for (int i = 20; i < 24; i++) beaconsPerOption[i]++;
                amount++;
            }
        }
        return amount;
    }

    public static void solution(ArrayList<ArrayList<int[]>> input, boolean partOne) {
        int x = 1, y = 2, z = 3;
        int[][] beaconVariations = new int[][]{{x, y, z}, {x, z, -y}, {x, -y, -z}, {x, -z, y},
                {-x, y, -z}, {-x, z, y}, {-x, -y, z}, {-x, -z, -y},
                {y, x, -z}, {-y, x, z}, {z, x, y}, {-z, x, -y},
                {y, -x, z}, {-y, -x, -z}, {z, -x, -y}, {-z, -x, y},
                {y, z, x}, {-y, -z, x}, {z, -y, x}, {-z, y, x},
                {y, -z, -x}, {-y, z, -x}, {z, y, -x}, {-z, -y, -x}};

        ArrayList<int[]> toBeDone = new ArrayList<>();
        ArrayList<Integer> indexesFound = new ArrayList<>();
        indexesFound.add(0);
        toBeDone.add(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        for (int index=0 ;index<indexesFound.size() ;index++) {
            int lowrangex = -2000, highrangex = 2000;
            int lowrangey = -2000, highrangey = 2000;
            int lowrangez = -2000, highrangez = 2000;
            for (int[] newRange : toBeDone){
                if (newRange[1]==index){
                    lowrangex = newRange[2]-2000;
                    highrangex = newRange[2]+2000;
                    lowrangey = newRange[3]-2000;
                    highrangey = newRange[3]+2000;
                    lowrangez = newRange[4]-2000;
                    highrangez = newRange[4]+2000;
                }
            }
            int i = indexesFound.get(index);
            System.out.println("start " + i);
//            System.out.println(input.get(i).size());
            for (int j=0; j<input.size(); j++) {
                if (i!=j && (!indexesFound.contains(j))) {
//                System.out.println(Arrays.toString(input.get(i).get(j)));
//                    System.out.println(lowrangex + ":"+highrangex);
//                    System.out.println(lowrangey + ":"+highrangey);
//                    System.out.println(lowrangez + ":"+highrangez);
                    help:
                    for (int dx = lowrangex; dx <= highrangex; dx++) {
                        int[] beaconsPerOption = new int[24];
                        for (int a = 0; a < 24; a++) {
                            beaconsPerOption[a] = 0;
                        }
                        int[] indexes0 = new int[input.get(i).size()];
                        for (int a = 0; a < input.get(i).size(); a++) {
                            indexes0[a] = -1;
                        }
                        for (int s = 0; s < input.get(i).size(); s++) {
                            for (int t = 0; t < input.get(j).size(); t++) {
                                int posX = addXPossibilities(input.get(i).get(s).clone(), input.get(j).get(t).clone(), beaconsPerOption, 0, dx);
                                if (posX > 0) {
                                    indexes0[s] = t;
                                }
                            }
                        }

                        int matches = 0;
                        for (int t : indexes0) {
                            if (t != -1) {
                                matches++;
                            }
                        }
                        if (i==31) System.out.println(Arrays.toString(beaconsPerOption));
                        if (matches > 11) {
                            System.out.println(Arrays.toString(beaconsPerOption));
                            int[] beaconsPerOptionX = beaconsPerOption.clone();
                            System.out.println("x: " + dx);
                            for (int dy = lowrangey; dy <= highrangey; dy++) {
                                for (int s = 0; s < indexes0.length; s++) {
                                    if (indexes0[s] != -1) {
                                        int[] s0 = input.get(i).get(s).clone();
                                        int[] beacon = input.get(j).get(indexes0[s]).clone();
                                        for (int a = 0; a < 24; a++) {
                                            if (beaconsPerOption[a] > 11) {
                                                if (beaconVariations[a][0] == 2) {
                                                    if (s0[1] == beacon[0] + dy) beaconsPerOption[a]++;
                                                } else if (beaconVariations[a][0] == -2) {
                                                    if (s0[1] == -beacon[0] + dy) beaconsPerOption[a]++;
                                                } else if (beaconVariations[a][1] == 2) {
                                                    if (s0[1] == beacon[1] + dy) beaconsPerOption[a]++;
                                                } else if (beaconVariations[a][1] == -2) {
                                                    if (s0[1] == -beacon[1] + dy) beaconsPerOption[a]++;
                                                } else if (beaconVariations[a][2] == 2) {
                                                    if (s0[1] == beacon[2] + dy) beaconsPerOption[a]++;
                                                } else if (beaconVariations[a][2] == -2) {
                                                    if (s0[1] == -beacon[2] + dy) beaconsPerOption[a]++;
                                                }
                                            }
                                        }
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
                                    System.out.println("y: " + dy);
                                    System.out.println(Arrays.toString(beaconsPerOption));
                                    int[] beaconsPerOptionY = beaconsPerOption.clone();
                                    for (int dz = lowrangez; dz <= highrangez; dz++) {
                                        for (int s1 = 0; s1 < indexes0.length; s1++) {
                                            if (indexes0[s1] != -1) {
                                                int[] s01 = input.get(i).get(s1).clone();
                                                int[] beacon1 = input.get(j).get(indexes0[s1]).clone();
                                                for (int a = 0; a < 24; a++) {
                                                    if (beaconsPerOption[a] > 23) {
                                                        if (beaconVariations[a][0] == 3) {
                                                            if (s01[2] == beacon1[0] + dz)
                                                                beaconsPerOption[a]++;
                                                        } else if (beaconVariations[a][0] == -3) {
                                                            if (s01[2] == -beacon1[0] + dz)
                                                                beaconsPerOption[a]++;
                                                        } else if (beaconVariations[a][1] == 3) {
                                                            if (s01[2] == beacon1[1] + dz) {
                                                                beaconsPerOption[a]++;
                                                            }
                                                        } else if (beaconVariations[a][1] == -3) {
                                                            if (s01[2] == -beacon1[1] + dz)
                                                                beaconsPerOption[a]++;
                                                        } else if (beaconVariations[a][2] == 3) {
                                                            if (s01[2] == beacon1[2] + dz)
                                                                beaconsPerOption[a]++;
                                                        } else if (beaconVariations[a][2] == -3) {
                                                            if (s01[2] == -beacon1[2] + dz){
                                                                beaconsPerOption[a]++;
                                                            }

                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        boolean possibleZ = false;
                                        int[] currentOperations = new int[3];
                                        for (int e = 0; e < beaconsPerOption.length; e++) {
                                            if (beaconsPerOption[e] > 35) {
                                                System.out.println(Arrays.toString(beaconVariations[e]));
                                                currentOperations[0] = beaconVariations[e][0];
                                                currentOperations[1] = beaconVariations[e][1];
                                                currentOperations[2] = beaconVariations[e][2];
                                                possibleZ = true;
                                                break;
                                            }
                                        }
                                        if (possibleZ) {
                                            System.out.println("z: " + dz);

                                            System.out.println("i: " + i + " j: " + j);
                                            System.out.println(Arrays.toString(beaconsPerOption));
                                            System.out.println("");

                                            indexesFound.add(j);
//                                            for (int[] singleBeacon: input.get(j)){
//                                                int[] temp = singleBeacon.clone();
//                                                if (currentOperations[0]==1 || currentOperations[0]==-1){
//                                                    singleBeacon[0] = temp[0]*currentOperations[0] + dx;
//                                                } else if (currentOperations[1]==1 || currentOperations[1]==-1){
//                                                    singleBeacon[0] = temp[1]*currentOperations[1] + dx;
//                                                } else if (currentOperations[2]==1 || currentOperations[2]==-1){
//                                                    singleBeacon[0] = temp[2]*currentOperations[2] + dx;
//                                                }
//                                                if (currentOperations[0]==2 || currentOperations[0]==-2){
//                                                    singleBeacon[1] = temp[0]*currentOperations[0]/2 + dy;
//                                                } else if (currentOperations[1]==2 || currentOperations[1]==-2){
//                                                    singleBeacon[1] = temp[1]*currentOperations[1]/2 + dy;
//                                                } else if (currentOperations[2]==2 || currentOperations[2]==-2){
//                                                    singleBeacon[1] = temp[2]*currentOperations[2]/2 + dy;
//                                                }
//                                                if (currentOperations[0]==3 || currentOperations[0]==-3){
//                                                    singleBeacon[2] = temp[0]*currentOperations[0]/3 + dz;
//                                                } else if (currentOperations[1]==3 || currentOperations[1]==-3){
//                                                    singleBeacon[2] = temp[1]*currentOperations[1]/3 + dz;
//                                                } else if (currentOperations[2]==3 || currentOperations[2]==-3){
//                                                    singleBeacon[2] = temp[2]*currentOperations[2]/3 + dz;
//                                                }
//                                            }
                                            toBeDone.add(new int[]{i, j, dx, dy, dz, currentOperations[0], currentOperations[1], currentOperations[2]});
                                            break help;
                                        }
                                        beaconsPerOption = beaconsPerOptionY;
                                    }
                                    beaconsPerOption = beaconsPerOptionX;
                                    System.out.println("hi there");
//                                    break help;
                                }

                            }
                        }

                    }
                }
            }
        }
//        System.out.println(total);
//        System.out.println(amountOfBreaks*12);
        for (int[] tobe : toBeDone){
            System.out.println(Arrays.toString(tobe));
        }
        ArrayList<int[]> alreadyAdded = new ArrayList<>();
        for (ArrayList<int[]> scanner : input){
            for (int[] beacon : scanner ){
                if (!has(alreadyAdded,beacon)) alreadyAdded.add(beacon);
            }
        }

        //68,-1246,-43
        //1105,-1205,1229
        //-92,-2380,-20
        //-20,-1133,1061
        for (int i=0; i<alreadyAdded.size(); i++){
//            System.out.println(Arrays.toString(alreadyAdded.get(i)));
        }
        System.out.println(alreadyAdded.size());
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
//                    System.out.println(Arrays.toString(input.get(input.size() - 1).get(input.get(input.size()-1).size()-1)));
                }
//                System.out.println("");
            }
            solution(input,true);
//            solution(input,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

