package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@ADay(day = "day3")
public class Day3 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        Integer[] parts = {0, 0};
        int length = 0;
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(length == 0) length = line.length();
                lines.add(line.replace(" ", ""));
            }
        }catch (IOException ignored) {}

        StringBuilder binary = new StringBuilder();
        for(int i = 0; i < length; i++){
            Integer[] counter = {0, 0};
            for(String inputLine : lines){
                if(inputLine.charAt(i) == '0'){
                    counter[0]++;
                }else if(inputLine.charAt(i) == '1'){
                    counter[1]++;
                }
            }
            if(counter[0] > counter[1]){
                binary.append("0");
            }else{
                binary.append("1");
            }
        }

        parts[0] = binaryToDecimal(binary.toString());
        parts[1] = binaryToDecimal(mirrorBinary(binary.toString()));

        return String.valueOf(parts[0] * parts[1]);
    }

    @Override
    public String part2(BufferedReader reader) {
        int length = 0;
        ArrayList<String> linesRating = new ArrayList<>();
        ArrayList<String> linesScrubber = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(length == 0) length = line.length();
                linesRating.add(line.replace(" ", ""));
                linesScrubber.add(line.replace(" ", ""));
            }
        }catch (IOException ignored) {}

        for(int i = 0; i < length; i++){
            if(linesRating.size() > 1){
                Integer[] counterRating = countBinaryNumbers(linesRating, i);
                ArrayList<String> toRemove = new ArrayList<>();
                for(String inputLine : linesRating){
                    if(counterRating[1] >= counterRating[0]
                            && inputLine.charAt(i) == '1'){
                        toRemove.add(inputLine);
                    }else if((counterRating[0] > counterRating[1])
                            && inputLine.charAt(i) == '0'){
                        toRemove.add(inputLine);
                    }
                }
                linesRating.removeAll(toRemove);
            }
            if(linesScrubber.size() > 1){
                Integer[] counterScrubber = countBinaryNumbers(linesScrubber, i);
                ArrayList<String> toRemove = new ArrayList<>();
                for(String inputLine : linesScrubber){
                    if(counterScrubber[1] < counterScrubber[0]
                            && inputLine.charAt(i) == '1'){
                        toRemove.add(inputLine);
                    }else if(counterScrubber[0] <= counterScrubber[1]
                                && inputLine.charAt(i) == '0'){
                        toRemove.add(inputLine);
                    }
                }
                linesScrubber.removeAll(toRemove);
            }
            if(linesRating.size() == 1 && linesScrubber.size() == 1){
                break;
            }
        }

        return String.valueOf(binaryToDecimal(linesRating.get(0)) * binaryToDecimal(linesScrubber.get(0)));
    }

    private Integer[] countBinaryNumbers(ArrayList<String> binaries, int pos){
        Integer[] counter = {0, 0};
        for(String inputLine : binaries){
            if(inputLine.charAt(pos) == '0'){
                counter[0]++;
            }else if(inputLine.charAt(pos) == '1'){
                counter[1]++;
            }
        }
        return counter;
    }

    private String mirrorBinary(String binary){
        return binary.replace("1", "2").replace("0", "1").replace("2", "0");
    }

    private int binaryToDecimal(String binary){
        int response = 0;
        for(int i = 0; i < binary.length(); i++){
            int value = Integer.parseInt(String.valueOf(binary.charAt(i)));
            response += value * Math.pow(2, (binary.length()-1) - i);
        }
        return response;
    }
}
