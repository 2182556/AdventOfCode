import java.nio.file.*;
import java.util.*;

public class Day16 {
    private final ArrayList<Long> versionNumbers = new ArrayList<>();
    private int index;

    public long binaryToDecimal(String binary){
        long binaryInt = 0;
        for (int j=0; j<binary.length(); j++){
            if (binary.charAt(j) == '1') binaryInt+= Math.pow(2,binary.length()-1-j);
        }
        return binaryInt;
    }

    public long IDBasedValue(long typeID, ArrayList<Long> previousValues){
        long value = 0;
        if (typeID==0){
            for(long previous : previousValues){
                value += previous;
            }
        } else if (typeID==1){
            value = 1;
            for(long previous : previousValues){
                value *= previous;
            }
        } else if (typeID==2){
            value = Collections.min(previousValues);
        } else if (typeID==3){
            value = Collections.max(previousValues);
        } else if ((typeID==5 && previousValues.get(0)>previousValues.get(1)) || 
        (typeID==6 && previousValues.get(0)<previousValues.get(1)) ||
        (typeID==7 && Objects.equals(previousValues.get(0), previousValues.get(1))) ){
            value = 1;
        }
        return value;
    }

    public long literalValue(String inputBinary){
        StringBuilder currentBinary = new StringBuilder();
        while(true){
            if (inputBinary.charAt(index) == '0'){
                currentBinary.append(inputBinary, index + 1, index + 5);
                index+=5;
                break;
            } else {
                currentBinary.append(inputBinary, index + 1, index + 5);
                index+=5;
            }
        }
        return binaryToDecimal(currentBinary.toString());
    }

    public ArrayList<Long> operator(String inputBinary){
        String lengthType = inputBinary.substring(index, index+1);
        index++;
        long totalLength = 0;
        long totalSubs = 0;
        boolean length = false;
        long subCount = 0;
        if (lengthType.equals("0")){
            totalLength = binaryToDecimal(inputBinary.substring(index, index+15));
            length = true;
            index+=15;
        } else {
            totalSubs = binaryToDecimal(inputBinary.substring(index, index+11));
            index+=11;
        }
        int startIndex = index;
        ArrayList<Long> currentValues = new ArrayList<>();
        while( (length && (index-startIndex)<(totalLength)) || (!length && subCount<totalSubs) ){
            versionNumbers.add(binaryToDecimal(inputBinary.substring(index,index+3)));
            long typeID = binaryToDecimal(inputBinary.substring(index+3,index+6));
            index+=6;
            if (typeID==4){ 
                currentValues.add(literalValue(inputBinary));
            } else {
                ArrayList<Long> previousValues = operator(inputBinary);
                currentValues.add(IDBasedValue(typeID, previousValues));
            }
            subCount++;
        }
        return currentValues;
    }

    public void solution(String[] input, boolean partOne){
        String[] hex = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String[] binary = new String[]{"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};
        StringBuilder inputBinary = new StringBuilder();
        for (String s : input) {
            for (int j = 0; j < hex.length; j++) {
                if (s.equals(hex[j])) inputBinary.append(binary[j]);
            }
        }
        index=0;
        versionNumbers.add(binaryToDecimal(inputBinary.substring(index,index+3)));
        long typeID = binaryToDecimal(inputBinary.substring(index+3,index+6));
        index+=6;
        if (typeID!=4){
            ArrayList<Long> previousValues = operator(inputBinary.toString());
            if (!partOne){
                System.out.println("Part two: "+ IDBasedValue(typeID, previousValues));
            }
        } else {
            System.out.println(literalValue(inputBinary.toString()));
        }
        
        if (partOne){
            int sum = 0;
            for(long versionNumber : versionNumbers){
                sum += versionNumber;
            }
            System.out.println("Part one: " + sum);
        }
    }

    public static void main(String[] args){
        String filename="day16.txt";
        try (Scanner fileReader = new Scanner(Paths.get(filename))) {
            String[] input = fileReader.nextLine().trim().split("");
            Day16 day16 = new Day16();
            day16.solution(input,false);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
