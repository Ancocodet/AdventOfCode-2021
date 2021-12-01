package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@ADay(day = "day1")
public class Day1 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        int answer = 0;

        int lastValue = -1;
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(lastValue != -1 && lastValue < Integer.parseInt(line)) {
                    answer++;
                }
                lastValue = Integer.parseInt(line);
            }
        }catch (IOException ignored) {}

        return String.valueOf(answer);
    }

    @Override
    public String part2(BufferedReader reader) {
        int answer = 0;
        ArrayList<Integer> values = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null){
                values.add(Integer.parseInt(line));
            }
        }catch (IOException ignored){}

        int lastValue = -1;
        for(int i = 0; i < values.size() - 2; i++){
            int checkValue = values.get(i) + values.get(i + 1) + values.get(i + 2);
            if(lastValue != -1 && lastValue < checkValue){
                answer++;
            }
            lastValue = checkValue;
        }

        return String.valueOf(answer);
    }
}
