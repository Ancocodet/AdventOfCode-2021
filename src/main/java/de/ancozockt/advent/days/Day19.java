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
        Game game = readInput(reader);
        List<Vector3D> placement = game.scanners.stream().map(Scanner::getPlacementTo0).toList();
        int maxDistance = 0;
        for (int i = 0; i < placement.size(); i++) {
            for (int j = 0; j < placement.size(); j++) {
                int distance = getManhattanDistance(placement.get(i), placement.get(j));
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return String.valueOf(maxDistance);
    }


    private int getManhattanDistance(Vector3D a, Vector3D b) {
        return (int) (Math.abs(a.getX() - b.getX())
                + Math.abs(a.getY() - b.getY())
                + Math.abs(a.getZ() - b.getZ()));
    }

    private Game readInput(BufferedReader reader){
        List<Scanner> scanners = new ArrayList<>();

        java.util.Scanner scanner = new java.util.Scanner(reader);
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            String line = scanner.nextLine();
            List<Vector3D> measurements = new ArrayList<>();
            while (!"".equals(line) && line != null) {
                List<Integer> vector = Arrays.stream(line.split(",")).map(Integer::valueOf).toList();
                measurements.add(new Vector3D(vector.get(0), vector.get(1), vector.get(2)));
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    line = null;
                }
            }
            scanners.add(new Scanner(measurements));
        }

        return new Game(scanners);
    }

    public record Game(List<Scanner> scanners){ }

}
