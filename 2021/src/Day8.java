import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) {
        String filename="day8.txt";
        ArrayList<String[]> allOutputs = new ArrayList<>();
        ArrayList<String[]> allInputs = new ArrayList<>();
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                String[] twoSegments = line.split(" \\| ");
                allInputs.add(twoSegments[0].split(" "));
                allOutputs.add(twoSegments[1].split(" "));
            }
            
            int totalSum = 0;
            for (String[] input : allInputs){
                String[] digitOne = new String[2];
                String[] digitFour = new String[4];
                String[] digitSeven = new String[3];
                String[] digitEight = new String[7];
                String[] digitNine = new String[6];
                String[] digitThree = new String[5];
                ArrayList<String[]> fiveDigitNumbers = new ArrayList<>();
                ArrayList<String[]> sixDigitNumbers = new ArrayList<>();
                String[] finalDigits = {"","","","","","",""};

                for (String number : input){
                    String[] array = number.split("");
                    Arrays.sort(array);
                    if (array.length==2){
                        digitOne = array;
                    } else if (array.length==3){
                        digitSeven = array;
                    } else if (array.length==4) {
                        digitFour = array;
                    } else if (array.length==5){
                        if (!fiveDigitNumbers.contains(array)){
                            fiveDigitNumbers.add(array);
                        }
                    } else if (array.length==6){
                        if (!sixDigitNumbers.contains(array)){
                            sixDigitNumbers.add(array);
                        }
                    }else if (number.length()==7){
                        digitEight = array;
                    }
                }
                for (String digit : digitSeven){
                    if (!Arrays.asList(digitOne).contains(digit)){
                        finalDigits[0] = digit;
                    }
                }
                for (String[] sixDigit : sixDigitNumbers){
                    if (Arrays.asList(sixDigit).containsAll(Arrays.asList(digitFour))){
                        digitNine = sixDigit;
                        for (String digit: sixDigit){
                            if (!Arrays.asList(digitFour).contains(digit) && !digit.equals(finalDigits[0])){
                                finalDigits[2] = digit;
                            }
                        }
                    }
                }
                for (String[] fiveDigit : fiveDigitNumbers){
                    if (Arrays.asList(fiveDigit).containsAll(Arrays.asList(digitOne))){
                        digitThree = fiveDigit;
                        for (String digit : fiveDigit){
                            if (!Arrays.asList(digitOne).contains(digit) && !digit.equals(finalDigits[0]) && !digit.equals(finalDigits[2])){
                                finalDigits[1] = digit;
                            }
                        }
                    }
                }
                for (String digit : digitNine){
                    if (!Arrays.asList(digitThree).contains(digit)){
                        finalDigits[3] = digit;
                    }
                }
                for (String[] fiveDigit : fiveDigitNumbers){
                    List<String> finalDigitsTemp = new ArrayList<>();
                    for (String item : finalDigits){
                        finalDigitsTemp.add(item.toString());
                    }
                    Arrays.asList(finalDigits);
                    finalDigitsTemp.removeAll(Collections.singleton(""));
                    if (Arrays.asList(fiveDigit).containsAll(finalDigitsTemp)){
                        for (String digit : fiveDigit){
                            if (!Arrays.asList(finalDigits).contains(digit)){
                                finalDigits[6] = digit;
                            }
                        }
                    }
                }
                for (String digit : digitOne){
                    if (!Arrays.asList(finalDigits).contains(digit)){
                        finalDigits[5] = digit;
                    }
                }
                for (String digit : digitEight){
                    if (!Arrays.asList(finalDigits).contains(digit)){
                        finalDigits[4] = digit;
                    }
                }
            
                // for (String finalDigit : finalDigits){
                //     System.out.print(finalDigit.toString());
                // }
                String currentNumber = "";

                for (String output : allOutputs.get(allInputs.indexOf(input))){
                    if (output.length()==7){
                        currentNumber += "8";
                    } else if (output.length()==4){
                        currentNumber += "4";
                    } else if (output.length()==3){
                        currentNumber += "7";
                    } else if (output.length()==2){
                        currentNumber += "1";
                    } else if (output.length()==5){
                        if (output.contains(finalDigits[3])){
                            currentNumber += "5";
                        } else if (output.contains(finalDigits[6])){
                            currentNumber += "3";
                        } else {
                            currentNumber += "2";
                        }
                    } else if (output.length()==6){
                        if (!output.contains(finalDigits[1])){
                            currentNumber += "0";
                        } else if (output.contains(finalDigits[4])){
                            currentNumber += "6";
                        } else {
                            currentNumber += "9";
                        }
                    }
                }
                totalSum += Integer.valueOf(currentNumber);

                System.out.println("");
                System.out.println(currentNumber);
                System.out.println("");

            }
            System.out.println(totalSum);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
