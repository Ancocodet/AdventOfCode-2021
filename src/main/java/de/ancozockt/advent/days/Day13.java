package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ADay(day = "day13")
public class Day13 implements AdventDay {

    public record Input(List<Point> points, List<Fold> folds) {
    }

    public record Point(int x, int y) {
        public Point fold(Fold fold) {
            if (fold.type == FoldType.HORIZONTAL) {
                if (y <= fold.x) {
                    return this;
                } else {
                    return new Point(x, fold.x - (y - fold.x));
                }
            } else {
                if (x <= fold.x) {
                    return this;
                } else {
                    return new Point(fold.x - (x - fold.x), y);
                }
            }
        }
    }

    public record Fold(FoldType type, int x) {
    }

    public enum FoldType {
        HORIZONTAL, VERTICAL
    }

    @Override
    public String part1(BufferedReader reader) {
        Input input = parseInput(reader);

        Set<Point> afterFold = new HashSet<>();
        Fold f = input.folds.get(0);
        for (Point point : input.points) {
            afterFold.add(point.fold(f));
        }

        return String.valueOf(afterFold.size());
    }

    @Override
    public String part2(BufferedReader reader) {
        Input input = parseInput(reader);

        Set<Point> points = new HashSet<>(input.points);
        for (Fold f : input.folds) {
            Set<Point> afterFold = new HashSet<>(points.size());

            for (Point point : points) {
                afterFold.add(point.fold(f));
            }

            points = afterFold;
        }

        int maxX = points.stream().mapToInt(Point::x).max().getAsInt();
        int maxY = points.stream().mapToInt(Point::y).max().getAsInt();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                if (points.contains(new Point(j, i))) {
                    sb.append('X');
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    private Input parseInput(BufferedReader reader){
        List<Point> points = new ArrayList<>();
        List<Fold> folds = new ArrayList<>();

        reader.lines().forEach(line -> {
            if(!line.isEmpty()){
                if (line.startsWith("fold along")) {
                    String[] splits = line.split("=");

                    FoldType foldType;
                    if (splits[0].charAt(splits[0].length() - 1) == 'x') {
                        foldType = FoldType.VERTICAL;
                    } else {
                        foldType = FoldType.HORIZONTAL;
                    }

                    folds.add(new Fold(foldType, Integer.parseInt(splits[1])));
                } else {
                    String[] splits = line.split(",");
                    points.add(new Point(Integer.parseInt(splits[0]), Integer.parseInt(splits[1])));
                }
            }
        });

        return new Input(points, folds);
    }
}
