package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@ADay(day = "day10")
public class Day10 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        Stream<String> input = reader.lines();

        Map<Character, Character> syntax = Map.of(')', '(', ']', '[', '}', '{', '>', '<');
        Map<Character, Long> errorPoints = Map.of(')', 3L, ']', 57L, '}', 1197L, '>', 25137L);

        AtomicLong points = new AtomicLong(0);

        out: for(String line : input.toList()){
            Stack<Character> stack = new Stack<>();
            for(Character character : line.toCharArray()){
                if(syntax.containsKey(character)){
                    if(!stack.isEmpty()){
                        Character lastCharacter = stack.pop();
                        if(!syntax.get(character).equals(lastCharacter)){
                            points.getAndAdd(errorPoints.get(character));
                            continue out;
                        }
                    }
                }else{
                    stack.push(character);
                }
            }
        }

        return String.valueOf(points.get());
    }

    @Override
    public String part2(BufferedReader reader) {
        Stream<String> input = reader.lines();

        Map<Character, Character> syntax = Map.of(')', '(', ']', '[', '}', '{', '>', '<');
        Map<Character, Long> errorPoints = Map.of('(', 1L, '[', 2L, '{', 3L, '<', 4L);

        List<Long> scores = new ArrayList<>();

        out: for(String line : input.toList()){
            Stack<Character> stack = new Stack<>();
            for(Character character : line.toCharArray()){
                if(syntax.containsKey(character)){
                    Character lastCharacter = stack.pop();
                    if(!syntax.get(character).equals(lastCharacter)){
                        continue out;
                    }
                }else{
                    stack.push(character);
                }
            }
            long score = 0;
            while(!stack.isEmpty()){
                Character character = stack.pop();
                score = (score * 5) + errorPoints.getOrDefault(character, 0L);
            }
            scores.add(score);
        }

        return String.valueOf(scores.stream().sorted().skip(scores.size() / 2).findFirst().get());
    }

}
