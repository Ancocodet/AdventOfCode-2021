package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.Scanner;
import de.ancozockt.advent.utilities.Transformation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.BufferedReader;
import java.util.*;

@ADay(day = "day19")
public class Day19 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        Game game = readInput(reader);

        Set<Vector3D> fullMapFrom0 = new HashSet<>();
        List<Scanner> done = new ArrayList<>();
        done.add(game.scanners.get(0));
        int checkIndex = 0;
        while (done.size() < game.scanners.size()) {
            Scanner current = done.get(checkIndex);
            for (Scanner scanner : game.scanners) {
                if (done.contains(scanner)) {
                    continue;
                }
                Transformation transformation = current.getTransformationIfExists(scanner);
                if (transformation != null) {
                    scanner.transformationsTo0.addAll(current.transformationsTo0);
                    scanner.transformationsTo0.add(transformation);
                    done.add(scanner);
                }
            }
            checkIndex++;
        }
        done.stream().map(Scanner::getTransformedTo0).forEach(fullMapFrom0::addAll);
        return String.valueOf(fullMapFrom0.size());
    }

    @Override
    public String part2(BufferedReader reader) {
        return null;
    }


    public static Game readInput(BufferedReader reader){
        List<Scanner> scanners = new ArrayList<>();
        reader.lines().forEach(line -> {
            if(!line.startsWith("--- ")) {
                List<Vector3D> measurements = new ArrayList<>();
                while (!"".equals(line) && line != null) {
                    List<Integer> vector = Arrays.stream(line.split(",")).map(Integer::valueOf).toList();
                    measurements.add(new Vector3D(vector.get(0), vector.get(1), vector.get(2)));
                }
                scanners.add(new Scanner(measurements));
            }
        });
        return new Game(scanners);
    }

    public record Game(List<Scanner> scanners){ }

}
