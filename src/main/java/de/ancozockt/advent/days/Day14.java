package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.LongCountMap;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@ADay(day = "day14")
public class Day14 implements AdventDay {

    public record Input(String start, Set<String> rules){

    }

    @Override
    public String part1(BufferedReader reader) {
        Input input = formatInput(reader);
        return String.valueOf(simulateSteps(input, 10));
    }

    @Override
    public String part2(BufferedReader reader) {
        Input input = formatInput(reader);
        return String.valueOf(simulateSteps(input, 40));
    }

    private long simulateSteps(Input input, int steps){
        Map<String, String> strings = input.rules().stream().map(e -> e.split(" -> ")).collect(Collectors.toMap(e -> e[0], e -> e[1]));

        LongCountMap<String> pairs = new LongCountMap<String>();
        for(int i = 0; i < input.start().length() - 1; i++) {
            pairs.increment(input.start().substring(i, i+2));
        }

        LongCountMap<Character> characters = new LongCountMap<>();
        input.start().chars().forEach(e -> characters.increment((char)e));

        for(int i = 0; i < steps; i++){
            LongCountMap<String> newPairs = new LongCountMap<>();
            LongCountMap<String> finalPairs = pairs;
            pairs.keySet().forEach(pair -> {
                long increment = finalPairs.get(pair);
                if(strings.containsKey(pair)){
                    String key = strings.get(pair);

                    String n1 = pair.charAt(0) + key;
                    String n2 = key + pair.charAt(1);

                    newPairs.increment(n1, increment);
                    newPairs.increment(n2, increment);

                    characters.increment(key.charAt(0), increment);
                }else{
                    newPairs.increment(pair, increment);
                }
            });

            pairs = newPairs;
        }

        return characters.values().stream().mapToLong( value -> value).max().getAsLong() - characters.values().stream().mapToLong( value -> value).min().getAsLong();
    }

    private Input formatInput(BufferedReader reader){
        AtomicReference<String> start = new AtomicReference<>("");
        Set<String> rules = new HashSet<>();
        reader.lines().forEach(line -> {
            if(!line.isEmpty()){
                if(line.contains("->")){
                    rules.add(line);
                }else{
                    start.set(line);
                }
            }
        });
        return new Input(start.get(), rules);
    }
}
