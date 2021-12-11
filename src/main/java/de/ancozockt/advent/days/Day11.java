package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


@ADay(day = "day11")
public class Day11 implements AdventDay {

    public record Octopus(int x, int y) {
        public List<Octopus> getAdjacentPoints() {
            return List.of(
                    new Octopus(x - 1, y),
                    new Octopus(x - 1, y - 1),
                    new Octopus(x, y - 1),
                    new Octopus(x + 1, y - 1),
                    new Octopus(x + 1, y),
                    new Octopus(x + 1, y + 1),
                    new Octopus(x, y + 1),
                    new Octopus(x - 1, y + 1)
            );
        }
    }

    @Override
    public String part1(BufferedReader reader) {
        List<List<Integer>> input = parseInput(reader.lines());
        AtomicLong flashes = new AtomicLong(0);

        HashMap<Octopus, Integer> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            List<Integer> line = input.get(y);
            for (int x = 0; x < line.size(); x++) {
                map.put(new Octopus(x, y), line.get(x));
            }
        }

        for(int i = 0; i < 100; i++){
            Set<Octopus> flashing = new HashSet<>();
            int flashingBefore = 0;

            map.keySet().forEach(point -> {
                map.compute(point, (x, y) -> y + 1);
            });

            do{
                flashingBefore = flashing.size();

                map.keySet().forEach(point -> {
                    if(!flashing.contains(point) && map.get(point) > 9){
                        point.getAdjacentPoints().forEach(x -> map.computeIfPresent(x, (y, n) -> n + 1));
                        flashing.add(point);
                    }
                });
            }while(flashingBefore < flashing.size());

            flashing.forEach(point -> {
                map.put(point, 0);
            });

            flashes.getAndAdd(flashing.size());
        }

        return String.valueOf(flashes.get());
    }

    @Override
    public String part2(BufferedReader reader) {
        List<List<Integer>> input = parseInput(reader.lines());

        HashMap<Octopus, Integer> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            List<Integer> line = input.get(y);
            for (int x = 0; x < line.size(); x++) {
                map.put(new Octopus(x, y), line.get(x));
            }
        }

        for(int i = 0; i < Integer.MAX_VALUE; i++){
            Set<Octopus> flashing = new HashSet<>();
            int flashingBefore = 0;

            map.keySet().forEach(point -> {
                map.compute(point, (x, y) -> y + 1);
            });

            do{
                flashingBefore = flashing.size();

                map.keySet().forEach(point -> {
                    if(!flashing.contains(point) && map.get(point) > 9){
                        point.getAdjacentPoints().forEach(x -> map.computeIfPresent(x, (y, n) -> n + 1));
                        flashing.add(point);
                    }
                });
            }while(flashingBefore < flashing.size());

            if(flashing.size() == map.size()){
                return String.valueOf(i + 1);
            }

            flashing.forEach(point -> {
                map.put(point, 0);
            });
        }

        return "-1";
    }

    private List<List<Integer>> parseInput(Stream<String> inputStream){
        return inputStream.map(line -> line.chars().map(i -> i -'0').boxed().toList()).toList();
    }
}
