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
        List<String> lines = formatInput(reader).toList();

        long totalRisk = 0;
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            for (int j = 0; j < l.length(); j++) {
                int c = l.codePointAt(j);
                if ((i + 1 < lines.size() && lines.get(i + 1).codePointAt(j) <= c) ||
                    (i - 1 >= 0 && lines.get(i - 1).codePointAt(j) <= c) ||
                    (j + 1 < l.length() && lines.get(i).codePointAt(j + 1) <= c) ||
                    (j - 1 >= 0 && lines.get(i).codePointAt(j - 1) <= c)) {
                    continue;
                }
                totalRisk += c - '0' + 1;
            }
        }

        return String.valueOf(totalRisk);
    }

    @Override
    public String part2(BufferedReader reader) {
        List<String> lines = formatInput(reader).toList();

        Set<Point> exploredPoints = new HashSet<>();
        List<Integer> basinSizes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            for (int j = 0; j < l.length(); j++) {
                int c = l.codePointAt(j);
                if (c == '9') {
                    continue;
                }
                if (exploredPoints.contains(new Point(i, j))) {
                    continue;
                }

                int basinSize = 0;
                Queue<Point> toExplore = new ArrayDeque<>();
                toExplore.add(new Point(i, j));

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
                    if (point.y + 1 < l.length()) {
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

    private Stream<String> formatInput(BufferedReader reader){
        StringBuilder input = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null){
                input.append(line).append("\n");
            }
        }catch (IOException ignored) {}

        return Arrays.stream(input.toString().split(System.lineSeparator()));
    }
}
