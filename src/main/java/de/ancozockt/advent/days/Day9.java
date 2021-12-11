package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@ADay(day = "day9")
public class Day9 implements AdventDay {

    public record Point(int x, int y) {
    }

    @Override
    public String part1(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        AtomicLong totalRisk = new AtomicLong(0);
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                int count = line.codePointAt(x);
                if ((y + 1 < lines.size() && lines.get(y + 1).codePointAt(x) <= count) ||
                    (y - 1 >= 0 && lines.get(y - 1).codePointAt(x) <= count) ||
                    (x + 1 < line.length() && lines.get(y).codePointAt(x + 1) <= count) ||
                    (x - 1 >= 0 && lines.get(y).codePointAt(x - 1) <= count)) {
                    continue;
                }
                totalRisk.getAndAdd(count - '0' + 1);
            }
        }

        return String.valueOf(totalRisk.get());
    }

    @Override
    public String part2(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        Set<Point> exploredPoints = new HashSet<>();
        List<Integer> basinSizes = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                int count = line.codePointAt(x);
                if (count == '9') {
                    continue;
                }
                if (exploredPoints.contains(new Point(x, y))) {
                    continue;
                }

                int basinSize = 0;
                Queue<Point> toExplore = new ArrayDeque<>();
                toExplore.add(new Point(x, y));

                while (!toExplore.isEmpty()) {
                    Point point = toExplore.remove();
                    if (!exploredPoints.add(point)) {
                        continue;
                    }

                    if (lines.get(point.x).codePointAt(point.y) == '9') {
                        continue;
                    }

                    basinSize++;

                    if (point.x + 1 < lines.size()) {
                        toExplore.add(new Point(point.x + 1, point.y));
                    }
                    if (point.x - 1 >= 0) {
                        toExplore.add(new Point(point.x - 1, point.y));
                    }
                    if (point.y + 1 < line.length()) {
                        toExplore.add(new Point(point.x, point.y + 1));
                    }
                    if (point.y - 1 >= 0) {
                        toExplore.add(new Point(point.x, point.y - 1));
                    }
                }

                basinSizes.add(basinSize);
            }
        }

        List<Integer> biggest = basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).toList();

        AtomicLong result = new AtomicLong(1);
        for (Integer integer : biggest) {
            result.set(result.get() * integer);
        }

        return String.valueOf(result.get());
    }

    private int getMax(Integer[] input){
        int result = 0;

        for(int i : input){
            if(i > result)
                result = i;
        }

        return result;
    }


    private Integer[] toInt(String[] input){
        Integer[] result = new Integer[input.length];

        for(int i = 0; i < input.length; i++){
            result[i] = Integer.parseInt(input[i]);
        }

        return result;
    }

}
