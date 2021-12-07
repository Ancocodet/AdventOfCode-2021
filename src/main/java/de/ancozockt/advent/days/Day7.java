package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.Lanternfish;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ADay(day = "day7")
public class Day7 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        HashMap<Integer, Integer> crabs = formatInput(reader);

        AtomicInteger finalFuel = new AtomicInteger(0);
        crabs.forEach((horizontal, crabAmount) -> {
            AtomicInteger usedFuel = new AtomicInteger(0);
            crabs.forEach((horizontalFrom, crabAmountFrom) -> {
                int distance = horizontalFrom - horizontal;
                if(distance < 0){
                    distance *= -1;
                }
                usedFuel.getAndAdd(distance * crabAmountFrom);
            });
            if(usedFuel.get() < finalFuel.get() || finalFuel.get() == 0){
                finalFuel.set(usedFuel.get());
            }
        });

        return String.valueOf(finalFuel.get());
    }

    @Override
    public String part2(BufferedReader reader) {
        HashMap<Integer, Integer> crabs = formatInput(reader);

        AtomicInteger finalFuel = new AtomicInteger(0);
        crabs.forEach((horizontal, crabAmount) -> {
            AtomicInteger usedFuel = new AtomicInteger(0);
            crabs.forEach((horizontalFrom, crabAmountFrom) -> {
                if(!horizontal.equals(horizontalFrom)){
                    int distance = horizontalFrom - horizontal;
                    if(distance < 0){
                        distance *= -1;
                    }
                    if(distance > 0){
                        int fuel = 0;
                        for(int i = 0; i < distance; i++){
                            fuel += (i + 1);
                        }
                        usedFuel.getAndAdd(fuel * crabAmountFrom);
                    }
                }
            });
            if(usedFuel.get() < finalFuel.get() || finalFuel.get() == 0){
                finalFuel.set(usedFuel.get());
            }
        });

        return String.valueOf(finalFuel.get());
    }

    private HashMap<Integer, Integer> formatInput(BufferedReader reader){
        StringBuilder input = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                input.append(line);
            }
        }catch (IOException ignored) {}

        HashMap<Integer, Integer> result = new HashMap<>();

        for(String crab : input.toString().split(",")){
            int horizontal = Integer.parseInt(crab);
            result.put(horizontal, result.getOrDefault(horizontal, 0) + 1);
        }

        return result;
    }
}
