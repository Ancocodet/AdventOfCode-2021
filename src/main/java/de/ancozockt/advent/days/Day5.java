package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ADay(day = "day5")
public class Day5 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        HashMap<Integer, HashMap<Integer, Integer>> positions = formatInput(reader, false);

        AtomicInteger counter = new AtomicInteger(0);
        positions.forEach((integer, integerIntegerHashMap) -> {
            integerIntegerHashMap.forEach((integer1, integer2) -> {
                if(integer2 >= 2){
                    counter.getAndIncrement();
                }
            });
        });

        return String.valueOf(counter.get());
    }

    @Override
    public String part2(BufferedReader reader) {
        HashMap<Integer, HashMap<Integer, Integer>> positions = formatInput(reader, true);

        AtomicInteger counter = new AtomicInteger(0);
        positions.forEach((integer, integerIntegerHashMap) -> {
            integerIntegerHashMap.forEach((integer1, integer2) -> {
                if(integer2 >= 2){
                    counter.getAndIncrement();
                }
            });
        });

        return String.valueOf(counter.get());
    }

    private HashMap<Integer, HashMap<Integer, Integer>> formatInput(BufferedReader reader, boolean diagonal){
        HashMap<Integer, HashMap<Integer, Integer>> positions = new HashMap<>();

        String line;
        try {
            while ((line = reader.readLine()) != null){
                String[] input = line.split(" -> ");

                String[] from = input[0].split(",");
                String[] to = input[1].split(",");

                int[] fromPos = new int[]{Integer.parseInt(from[0]), Integer.parseInt(from[1])};
                int[] toPos = new int[]{Integer.parseInt(to[0]), Integer.parseInt(to[1])};

                if(fromPos[0] == toPos[0] || fromPos[1] == toPos[1]){
                    for(int y = Math.min(fromPos[1], toPos[1]); y <= Math.max(fromPos[1], toPos[1]); y++){
                        HashMap<Integer, Integer> tempPos = positions.getOrDefault(y, new HashMap<>());
                        for(int x = Math.min(fromPos[0], toPos[0]); x <= Math.max(fromPos[0], toPos[0]); x++){
                            tempPos.put(x, tempPos.getOrDefault(x, 0) + 1);
                        }
                        positions.put(y, tempPos);
                    }
                }else if(diagonal){
                    int x = -1;
                    int y = -1;
                    do{
                        if(x == -1 && y == -1){
                            x = fromPos[0];
                            y = fromPos[1];
                        }else{
                            if(x != toPos[0]) x = toPos[0] > fromPos[0] ? x + 1 : x - 1;
                            if(y != toPos[1]) y = toPos[1] > fromPos[1] ? y + 1 : y - 1;
                        }

                        HashMap<Integer, Integer> tempPos = positions.getOrDefault(y, new HashMap<>());
                        tempPos.put(x, tempPos.getOrDefault(x, 0) + 1);
                        positions.put(y, tempPos);
                    }while (x != toPos[0] && y != toPos[1]);
                }
            }
        }catch (IOException ignored) {}

        return positions;
    }
}
