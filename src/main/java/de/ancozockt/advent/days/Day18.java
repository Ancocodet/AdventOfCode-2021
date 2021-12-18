package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.day18.Element;
import de.ancozockt.advent.utilities.day18.Literal;
import de.ancozockt.advent.utilities.day18.Pair;

import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@ADay(day = "day18")
public class Day18 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        List<Element> input = parseInput(reader.lines());
        return String.valueOf(sum(input).getMagnitude());
    }

    @Override
    public String part2(BufferedReader reader) {
        List<Element> input = parseInput(reader.lines());
        long max = Long.MIN_VALUE;

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if(i == j){
                    continue;
                }
                max = Math.max(max, sum(List.of(input.get(i), input.get(j))).getMagnitude());
            }
        }

        return String.valueOf(max);
    }

    private Element parse(String input) {
        Deque<Element> queue = new ArrayDeque<>();
        AtomicBoolean newNumber = new AtomicBoolean(true);

        input.chars().forEach(i -> {
            if (i == ']') {
                Element right = queue.removeLast();
                Element left = queue.removeLast();
                queue.addLast(new Pair(null, left, right));
            } else if (i >= '0' && i <= '9') {
                if(newNumber.get()) {
                    queue.addLast(new Literal(null, i - '0'));
                    newNumber.set(false);
                } else {
                    Literal last = (Literal) queue.getLast();
                    last.value = last.value * 10 + (i - '0');
                }
            } else if (i == ',') {
                newNumber.set(true);
            }
        });

        return queue.removeLast();
    }

    private Element sum(List<Element> input) {
        AtomicReference<Element> result = new AtomicReference<>(input.get(0).clone());

        for(int i = 1; i < input.size(); i++){
            Pair newPair = new Pair(null, result.get(), input.get(i).clone());
            while (newPair.explode() || newPair.split());
            result.set(newPair);
        }

        return result.get();
    }

    private List<Element> parseInput(Stream<String> stream) {
        return stream.map(this::parse).toList();
    }


}
