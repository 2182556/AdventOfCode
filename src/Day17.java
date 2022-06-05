import java.nio.file.Paths;
import java.util.Scanner;

public class Day17 {

    public static int getMaxY(int[][] target, int[] speed, boolean ending){
        int x=0, y=0;
        int maxY = 0;
        while (x<=target[0][1] && y>=target[1][0]){
//            if (speed[0] == 0 && speed[1]<10) return -1;
            x+= speed[0];
            y+= speed[1];
            if (y>maxY) maxY=y;
            if (target[0][0]<=x && x<=target[0][1] && target[1][0]<=y && y<=target[1][1]){
                return maxY;
            }
            if (speed[0]>0) speed[0]--;
//            if (speed[0]<0) speed[0]++;
            speed[1]--;
        }
        if (speed[0]==0 && x<target[0][0]){
            System.out.println("breaking");
            return -2;
        }
        return -1;
    }

    public static void solution(int[][] target, boolean partOne){
        int maxY = -1;
        int total = 0;
        for (int x=1; x<=target[0][1]; x++){
            int xReach = 0;
            for (int i=0 ;i<=x; i++){
                xReach+= i;
            }
            if (xReach>=target[0][0]) {
                int y = target[1][0];
                boolean ending = false;
                while (y<=10000) {
                    int currentY = getMaxY(target, new int[]{x, y}, ending);
                    if (currentY == -2) break;
                    //                previousY=currentY;
                    if (currentY != -1){
//                        System.out.println("speed added for "+x+": "+y);
                        total++;
                        ending=true;
                    }
                    if (maxY < currentY) {
                        maxY = currentY;
//                        System.out.println(x + ": " + currentY);
                    }
                    y++;
                }
            }
        }
        System.out.println("Part one: "+maxY);
        System.out.println("Part two: "+total);

    }

    public static void main(String[] args){
        String filename="day17.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            String[] input = fileReader.nextLine().trim().split(", ");
            String[] xs = input[0].split("=")[1].split("\\.\\.");
            String[] ys = input[1].split("=")[1].split("\\.\\.");
            int[][] target = new int[][]{{Integer.parseInt(xs[0]),Integer.parseInt(xs[1])},
                    {Integer.parseInt(ys[0]),Integer.parseInt(ys[1])}};
            solution(target,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
