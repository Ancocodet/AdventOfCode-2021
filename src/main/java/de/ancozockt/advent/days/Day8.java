package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ADay(day = "day8")
public class Day8 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        Stream<String> lines = formatInput(reader);

        return String.valueOf(lines.filter(e -> !e.isBlank())
                .map(e -> e.split("\\|")[1])
                .flatMap(e -> Stream.of(e.split(" ")))
                .filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7)
                .count());
    }

    @Override
    public String part2(BufferedReader reader) {
        Stream<String> lines = formatInput(reader);

        return String.valueOf(lines.filter(e -> !e.isBlank())
                .map(x -> x.replace(" | ", " ").split(" "))
                .mapToInt(Day8::decodeSignal)
                .sum());
    }

    private static Set<Character> strToSet(String str) {
        return str.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    private static <R> long intersectCount(Set<R> a, Set<R> b) {
        return a.stream().filter(b::contains).count();
    }

    private static int decodeSignal(String[] digits) {
        Map<Set<Character>, Integer> correspondence = new HashMap<>();

        String one = Arrays.stream(digits).filter(s -> s.length() == 2).findAny().get();
        Set<Character> oneSet = strToSet(one);
        correspondence.put(oneSet, 1);

        String seven = Arrays.stream(digits).filter(s -> s.length() == 3).findAny().get();
        Set<Character> sevenSet = strToSet(seven);
        correspondence.put(sevenSet, 7);

        String four = Arrays.stream(digits).filter(s -> s.length() == 4).findAny().get();
        Set<Character> fourSet = strToSet(four);
        correspondence.put(fourSet, 4);

        String eight = Arrays.stream(digits).filter(s -> s.length() == 7).findAny().get();
        Set<Character> eightSet = strToSet(eight);
        correspondence.put(eightSet, 8);

        Set<Set<Character>> fiveLengths = Arrays.stream(digits).filter(s -> s.length() == 5)
                .map(Day8::strToSet)
                .collect(Collectors.toSet());

        for (Set<Character> s : fiveLengths) {
            if (intersectCount(s, sevenSet) == 3) {
                correspondence.put(s, 3);
            } else {
                if (intersectCount(s, fourSet) == 3) {
                    correspondence.put(s, 5);
                } else {
                    correspondence.put(s, 2);
                }
            }
        }

        Set<Set<Character>> sixLengths = Arrays.stream(digits).filter(s -> s.length() == 6)
                .map(Day8::strToSet)
                .collect(Collectors.toSet());

        for (Set<Character> s : sixLengths) {
            if (intersectCount(s, fourSet) == 4) {
                correspondence.put(s, 9);
            } else {
                if (intersectCount(s, sevenSet) == 3) {
                    correspondence.put(s, 0);
                } else {
                    correspondence.put(s, 6);
                }
            }
        }

        return correspondence.get(strToSet(digits[digits.length - 1]))
                + 10 * correspondence.get(strToSet(digits[digits.length - 2]))
                + 100 * correspondence.get(strToSet(digits[digits.length - 3]))
                + 1000 * correspondence.get(strToSet(digits[digits.length - 4]));
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
