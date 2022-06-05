import java.nio.file.Paths;
import java.util.*;

public class Day22 {

    public static boolean isBetween(int a, int b, int c) {
        return b >= a ? c >= a && c <= b : c >= b && c <= a;
    }

    public static boolean overlap(int[] on, int[] off){
        return (isBetween(on[0],on[1],off[0]) || isBetween(on[0],on[1],off[1]) || isBetween(off[0],off[1],on[0]) || isBetween(off[0],off[1],on[1]) );
    }

    public static boolean isCompletelyInside(int[] on, int[] off){
        return (isBetween(off[0],off[1],on[0]) && isBetween(off[0],off[1],on[1]));
    }

    public static int[][] halved(int[] same1, int[] same2, int[] newRange){
        return new int[][]{same1,same2,newRange};
    }

    public static int[][][] throughTheMiddle(int[] same1, int[] same2, int[] newRange1, int[] newRange2){
        return new int[][][]{{same1,same2,newRange1},{same1,same2,newRange2}};
    }

    public static int sumCube(int[][] ranges){
        return Math.abs(ranges[0][1]-ranges[0][0]) +
                Math.abs(ranges[1][1]-ranges[1][0]) +
                Math.abs(ranges[2][1]-ranges[2][0]);
    }




    public static void solutionPartTwo(ArrayList<String[]> input){
        //only consider on regions
        //check if on region overlaps with off region in the future and remove that part
        //if either of 2 points are inside the other 2 points and likewise -> overlap on that axis
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<ArrayList<int[][]>> ranges = new ArrayList<>();
        ArrayList<int[][][]> cubes = new ArrayList<>();
        ArrayList<int[][]> onRegions = new ArrayList<>();

        for (String[] line : input){
            commands.add(line[0].trim());
            String[] section = line[1].trim().split(",");
            ranges.add(new ArrayList<>());
            ranges.get(ranges.size()-1).add(new int[section.length][2]);
            for (int i=0; i<section.length; i++) {
                String[] temp = section[i].split("=");
                String[] numbers = temp[1].trim().split("\\.\\.");
                ranges.get(ranges.size()-1).get(0)[i] = new int[]{Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])};
            }
        }
        for (int i =0; i<commands.size();i++){
            if (commands.get(i).equals("on")){
                boolean hasOverlap = false;
                for (int j=i+1; j<commands.size(); j++){
                    int[] onx = ranges.get(i).get(0)[0].clone();
                    int[] ony = ranges.get(i).get(0)[1].clone();
                    int[] onz = ranges.get(i).get(0)[2].clone();

                    int[] offx = ranges.get(j).get(0)[0].clone();
                    int[] offy = ranges.get(j).get(0)[1].clone();
                    int[] offz = ranges.get(j).get(0)[2].clone();

                    if (overlap(onx,offx) && overlap(ony,offy) && overlap(onz,offz)){
                        if (isCompletelyInside(onx,offx) && isCompletelyInside(ony,offy) && isCompletelyInside(onz,offz)){
                            ranges.get(i).set(0,new int[1][1]);
                            break;
                        } else {
                            hasOverlap = true;
                            int[] rangex = onx.clone();
                            int[] rangey = ony.clone();
                            int[] rangez = onz.clone();

                            if (offx[0]>onx[0]) rangex[0] = offx[0];
                            if (offx[1]<onx[1]) rangex[1] = offx[1];
                            if (offy[0]>ony[0]) rangey[0] = offy[0];
                            if (offy[1]<ony[1]) rangey[1] = offy[1];
                            if (offz[0]>onz[0]) rangez[0] = offz[0];
                            if (offz[1]<onz[1]) rangez[1] = offz[1];

                            ranges.get(i).add(new int[][]{rangex,rangey,rangez});
                        }
                    }
                }
            }
        }
        System.out.println("how long does this take");
        int howmany = 0;
        for (int i=0; i<ranges.size(); i++) {
            if (commands.get(i).equals("off")) {
                commands.remove(i);
                ranges.remove(i);
            } else {
                for (int j=0; j<ranges.get(i).size(); j++){
                    if (ranges.get(i).get(j).length==1){
                        ranges.get(i).remove(ranges.get(i).get(j));
                    } else {
                        for (int[] cube : ranges.get(i).get(j)) {
//                            System.out.println(Arrays.toString(cube));
                            howmany++;
                        }
                    }
                }
            }

        }
        System.out.println(howmany);
        long total = 0;
        for (int i=0; i<ranges.size(); i++){
            int[] rangex = ranges.get(i).get(0)[0];
            int[] rangey = ranges.get(i).get(0)[1];
            int[] rangez = ranges.get(i).get(0)[2];
            int stillOn = sumCube(ranges.get(i).get(0));
            if (ranges.get(i).size()>1) {
                stillOn -= sumCube(ranges.get(i).get(1));
            }
            if (ranges.get(i).size()>2){
                ArrayList<int[][][]> alreadyRemoved = new ArrayList<>();
                alreadyRemoved.add(new int[1][3][2]);
//                Arrays.fill(alreadyRemoved.get(0),0);
                for (int a=2; a<ranges.get(i).size(); a++) {
                    alreadyRemoved.add(new int[a-1][3][2]);
//                    Arrays.fill(alreadyRemoved.get(alreadyRemoved.size()-1),1);
                    if (ranges.get(i).get(a)[0].length > 2) {
                        boolean hasOverlap = false;
                        for (int b = 1; b < a; b++) {
                            if (ranges.get(i).get(b)[0].length > 2) {
                                int[] firstx = ranges.get(i).get(b)[0].clone();
                                int[] firsty = ranges.get(i).get(b)[1].clone();
                                int[] firstz = ranges.get(i).get(b)[2].clone();

                                int[] secondx = ranges.get(i).get(a)[0].clone();
                                int[] secondy = ranges.get(i).get(a)[1].clone();
                                int[] secondz = ranges.get(i).get(a)[2].clone();
                                if (overlap(firstx, secondx) && overlap(firsty, secondy) && overlap(firstz, secondz)) {
                                    hasOverlap = true;
                                    if (isCompletelyInside(firstx, secondx) && isCompletelyInside(firsty, secondy) && isCompletelyInside(firstz, secondz)) {
                                        stillOn -= (sumCube(ranges.get(i).get(a)) - sumCube(ranges.get(i).get(b)));
                                        break;
                                    } else if (isCompletelyInside(secondx, firstx) && isCompletelyInside(secondy, firsty) && isCompletelyInside(secondz, firstz)) {
                                        ranges.get(i).set(a, new int[1][1]);
                                        break;
                                    } else {

                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(total);
        //215620772313
        //2758514936282235


    }

    public static void solution(ArrayList<String[]> commands, boolean partOne){
        int[][][] field = new int[101][101][101];
        for (int i=0;i<field.length;i++){
            for (int j=0;j<field[0].length;j++){
                for (int k=0; k<field[0][0].length; k++){
                    field[i][j][k]=0;
                }
            }
        }

        for (String[] command : commands){
            String[] section = command[1].trim().split(",");
            int[][] coords = new int[section.length][2];
            for (int i=0; i<section.length; i++) {
                String[] temp = section[i].split("=");
                String[] numbers = temp[1].trim().split("\\.\\.");
                coords[i] = new int[]{Integer.parseInt(numbers[0])+50, Integer.parseInt(numbers[1])+50};
            }
            if (coords[0][0]>=0 && coords[1][0]>=0 && coords[2][0]>=0 &&
            coords[0][1]<=100 && coords[1][1]<=100 && coords[2][1]<=100) {
                for (int i=coords[0][0];i<=coords[0][1];i++){
                    for (int j=coords[1][0];j<=coords[1][1];j++){
                        for (int k=coords[2][0]; k<=coords[2][1]; k++){
                            if (command[0].trim().equals("off")){
                                field[i][j][k]=0;
                            } else {
                                field[i][j][k]=1;
                            }
                        }
                    }
                }
            }
        }
        int cubesOn = 0;
        for (int i=0;i<field.length;i++){
            for (int j=0;j<field[0].length;j++){
                for (int k=0; k<field[0][0].length; k++){
                    if (field[i][j][k]==1) cubesOn++;
                }
            }
        }

        System.out.println(cubesOn);


    }

    public static void main(String[] args){
        String filename="day22test.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<String[]> commands = new ArrayList<>();
            while(fileReader.hasNextLine()){
                commands.add(fileReader.nextLine().trim().split(" "));
            }
//            solution(commands, true);
            solutionPartTwo(commands);
//            solution(commands,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
