import java.nio.file.Paths;
import java.util.*;

public class Day18 {

    public static void operationsPerLine(ArrayList<String> line){
        boolean operation = true;

        while (operation) {
            operation = false;

            int pairCount = 0;
            checkForExplosion:
            for (int i = 0; i < line.size(); i++) {
                for (String sub : line.get(i).split("")) {
                    if (pairCount >= 5) {
                        operation = true;
                        String[] currentSplit = line.get(i).split("(?<=\\D)(?=\\d)");
                        String[] currentSplit2 = line.get(i + 1).split("(?=\\D)(?<=\\d)");
                        if (i > 0) {
                            if (line.get(i - 1).endsWith("]")) {
                                String[] previousSplit = line.get(i - 1).split("(?=\\D)(?<=\\d)");
                                String newNumber = String.valueOf(Integer.parseInt(previousSplit[0]) + Integer.parseInt(currentSplit[1]));
                                line.set(i - 1, newNumber + previousSplit[1]);
                            } else {
                                String[] previousSplit = line.get(i - 1).split("(?<=\\D)(?=\\d)");
                                String newNumber = String.valueOf(Integer.parseInt(previousSplit[1]) + Integer.parseInt(currentSplit[1]));
                                line.set(i - 1, previousSplit[0] + newNumber);
                            }
                        } else {
                            line.set(i, currentSplit[0].substring(1) + "0");
                        }

                        if (i < line.size() - 2) {
                            if (line.get(i + 2).charAt(0) == '[') {
                                String[] nextSplit = line.get(i + 2).split("(?<=\\D)(?=\\d)");
                                String newNumber = String.valueOf(Integer.parseInt(nextSplit[1]) + Integer.parseInt(currentSplit2[0]));
                                line.set(i + 2, nextSplit[0] + newNumber);
                            } else {
                                String[] nextSplit = line.get(i + 2).split("(?=\\D)(?<=\\d)");
                                String newNumber = String.valueOf(Integer.parseInt(nextSplit[0]) + Integer.parseInt(currentSplit2[0]));
                                line.set(i + 2, newNumber + nextSplit[1]);
                            }
                        }

                        if (currentSplit2[1].length() > 1) {
                            line.set(i + 1, "0" + currentSplit2[1].substring(1));
                            if (i!=0) line.remove(i);
                        } else if (currentSplit[0].length() > 1) {
                            line.set(i, currentSplit[0].substring(1) + "0");
                            if (i < line.size() - 2) line.remove(i + 1);
                        }

                        break checkForExplosion;
                    }
                    if (sub.equals("[")) pairCount++;
                    if (sub.equals("]")) pairCount--;
                }
            }
            if (!operation) { //check for split if there hasn't been an explosion
                for (int i = 0; i < line.size(); i++) {
                    String number = "";
                    for (String sub : line.get(i).split("")) {
                        if (!(sub.equals("[") || sub.equals("]"))) {
                            number += sub;
                        }
                    }
                    if (number.length() > 1) {
                        String smallerHalf = String.valueOf(Integer.parseInt(number) / 2);
                        String biggerHalf = String.valueOf(Integer.parseInt(number) / 2 + Integer.parseInt(number) % 2);
                        if (line.get(i).startsWith("[")) {
                            line.set(i, line.get(i).substring(0, line.get(i).length() - 2) + "[" + smallerHalf);
                            line.add(i + 1, biggerHalf + "]");
                        } else {
                            line.set(i, biggerHalf + "]" + line.get(i).substring(2));
                            line.add(i, "[" + smallerHalf);
                        }
                        operation = true;
                        break;
                    }
                }
            }

        }
    }

    public static void solution(ArrayList<ArrayList<String>> input, boolean partOne){
        ArrayList<ArrayList<String>> possibleLines = new ArrayList<>();
        ArrayList<Integer> magnitudes = new ArrayList<>();
        ArrayList<String> linePartOne = new ArrayList<>();
        for (int current=0; current<input.size(); current++){
            if (partOne){
                linePartOne.addAll(input.get(current));
                if (current>0){
                    linePartOne.set(0, "[" + linePartOne.get(0));
                    linePartOne.set(linePartOne.size() - 1, linePartOne.get(linePartOne.size() - 1) + "]");
                }
                operationsPerLine(linePartOne);
            } else {
                ArrayList<String> line = new ArrayList<>(input.get(current));
                for (int possible=-1; possible<input.size(); possible++) {
                    if (possible>=0){
                        possibleLines.add(line);
                        line = new ArrayList<>(input.get(current));
                        line.addAll(input.get(possible));
                        line.set(0, "[" + line.get(0));
                        line.set(line.size() - 1, line.get(line.size() - 1) + "]");
                    }
                    operationsPerLine(line);
                }
            }
        }

        if (partOne) possibleLines.add(linePartOne);

        for (ArrayList<String> all : possibleLines){
            while (all.size()>1){
                for (int i=1; i<all.size(); i++) {
                    if (Character.isDigit(all.get(i-1).charAt(all.get(i-1).length()-1)) && Character.isDigit(all.get(i).charAt(0)) ) {
                        String[] first = all.get(i-1).split("(?<=\\D)(?=\\d)");
                        String[] second = all.get(i).split("(?=\\D)(?<=\\d)");
                        int sum = 3*Integer.parseInt(first[1]) + 2*Integer.parseInt(second[0]);
                        if (first[0].length()>1) all.set(i-1, first[0].substring(1) + sum);
                        else if (second[1].length()>1) all.set(i-1, sum + second[1].substring(1));
                        else all.set(i-1, String.valueOf(sum));
                        all.remove(i);
                        break;
                    }
                }
            }
            magnitudes.add(Integer.valueOf(all.get(0)));
        }

        if (partOne) {
            System.out.println("Part one: " + magnitudes.get(0));
        } else {
            System.out.println("Part two: "+ Collections.max(magnitudes));
        }

    }

    public static void main(String[] args){
        String filename="day18.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            ArrayList<ArrayList<String>> input = new ArrayList<>();
            while(fileReader.hasNextLine()){
                input.add(new ArrayList<>());
                String[] line = fileReader.nextLine().trim().split(",");
                for (String piece : line) input.get(input.size()-1).add(piece);
            }
            solution(input,true);
            solution(input,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
