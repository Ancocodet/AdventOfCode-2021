package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@ADay(day = "day2")
public class Day2 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        Integer[] position = {0, 0};

        String line;
        try {
            while ((line = reader.readLine()) != null){
                String[] input = line.split(" ");
                switch (input[0].replace(" ", "")){
                    case "forward":
                        position[0] += Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    case "down":
                        position[1] += Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    case "up":
                        position[1] -= Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    default:
                }
            }
        }catch (IOException ignored) {}

        return String.valueOf(position[0] * position[1]);
    }

    @Override
    public String part2(BufferedReader reader) {
        Integer[] position = {0, 0, 0};

        String line;
        try {
            while ((line = reader.readLine()) != null){
                String[] input = line.split(" ");
                switch (input[0].replace(" ", "")){
                    case "forward":
                        position[0] += Integer.parseInt(input[1].replace(" ", ""));
                        position[1] += position[2] * Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    case "down":
                        position[2] += Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    case "up":
                        position[2] -= Integer.parseInt(input[1].replace(" ", ""));
                        break;
                    default:
                }
            }
        }catch (IOException ignored) {}

        return String.valueOf(position[0] * position[1]);
    }
}
