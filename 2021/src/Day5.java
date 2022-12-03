import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {

    public static void main(String[] args) throws Exception {
        String filename="day5.txt";
        ArrayList<String[]> begin = new ArrayList<>();
        ArrayList<String[]> end = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] lineSplit = line.split(" -> ");
                begin.add(lineSplit[0].split(","));
                end.add(lineSplit[1].split(","));
            }
            Integer[][] field = new Integer[1000][1000];
            for (int a=0; a<1000;a++){
                for (int b=0; b<1000;b++){
                    field[a][b] = 0;
                }
            }
            
            for (int i=0; i<begin.size();i++){
                int x1 = Integer.parseInt(begin.get(i)[0]), y1 = Integer.parseInt(begin.get(i)[1]);
                int x2 = Integer.parseInt(end.get(i)[0]), y2 = Integer.parseInt(end.get(i)[1]);
                if (x1==x2 && y1<=y2) {
                    for (int j=y1; j<=y2; j++) field[x1][j]++;
                } else if (x1==x2 && y1>y2){
                    for (int j=y2; j<=y1; j++) field[x1][j]++;
                } else if (y1==y2 && x1<=x2){
                    for (int j=x1; j<=x2; j++) field[j][y1]++;
                } else if (y1==y2 && x1>x2){
                    for (int j=x2; j<=x1; j++) field[j][y1]++;
                }

                if (Math.abs(x1-x2)==Math.abs(y1-y2)){
                    if (x1<x2 && y1<y2){
                        for (int j=0; j<=x2-x1 ; j++) field[x1+j][y1+j]++;
                    } else if (x1<x2 && y1>y2) {
                        for (int j=0; j<=x2-x1 ; j++) field[x1+j][y1-j]++;
                    } else if (x1>x2 && y1<y2){
                        for (int j=0; j<=x1-x2 ; j++) field[x1-j][y1+j]++;
                    } else {
                        for (int j=0; j<=x1-x2 ; j++) field[x1-j][y1-j]++;
                    }
                }
            }
            int overlap = 0;
            for (int a=0; a<1000;a++){
                for (int b=0; b<1000;b++){
                    if (field[a][b] > 1) overlap++;
                }
            }
            System.out.println(overlap);
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
