package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@ADay(day = "day17")
public class Day17 implements AdventDay {

    public record Point(int x, int y){}

    public record Area(Point topLeft, Point bottomRight){
        public boolean inArea(Point point){
            return point.x>=topLeft.x && point.y >= topLeft.y && point.x <=bottomRight.x && point.y <= bottomRight.y;
        }
    }

    @Override
    public String part1(BufferedReader reader) {
        Area area = formatInput(reader);

        int minX = (int) (area.topLeft.x * -1.5);
        int maxX = (int) (area.bottomRight.x * 1.5);

        int minY = (int) (area.bottomRight.y * (area.bottomRight.y < 0 ? 1.5 : -1.5));
        int maxY = (int) (area.topLeft.y * (area.topLeft.y < 0 ? -1.5 : 1.5));

        LongStream simulation = IntStream.range(minX, maxX).boxed().flatMap(x -> IntStream.range(minY, maxY)
                .mapToObj(y -> new Point(x, y))).mapToLong(p -> simulateSteps(area, p));

        return String.valueOf(simulation.max().getAsLong());
    }

    @Override
    public String part2(BufferedReader reader) {
        Area area = formatInput(reader);

        int minX = (int) (area.topLeft.x * -1.5);
        int maxX = (int) (area.bottomRight.x * 1.5);

        int minY = (int) (area.bottomRight.y * (area.bottomRight.y < 0 ? 2 : -2));
        int maxY = (int) (area.topLeft.y * (area.topLeft.y < 0 ? -2 : 2));

        LongStream simulation = IntStream.range(minX, maxX).boxed().flatMap(x -> IntStream.range(minY, maxY)
                .mapToObj(y -> new Point(x, y))).mapToLong(p -> simulateSteps(area, p));

        return String.valueOf(simulation.filter(value -> value != Integer.MIN_VALUE).count());
    }

    private long simulateSteps(Area area, Point point){
        Point current = new Point(0, 0);
        long highest = 0;

        while(current.y > area.topLeft.y && !area.inArea(current)) {
            current = new Point(current.x + point.x, current.y + point.y);
            point = new Point(point.x > 0 ? point.x - 1 : (point.x < 0 ? point.x - 1 : point.x), point.y - 1);
            if(current.y > highest) highest = current.y;
            if(current.x == 0 && (current.y < area.topLeft.y || current.y > area.bottomRight.y)) break;
        }

        if(area.inArea(current)){
            return highest;
        }
        return Integer.MIN_VALUE;
    }

    private Area formatInput(BufferedReader reader){
        Area result = null;
        try {
            String areaInput = reader.readLine();
            areaInput = areaInput.replace("target area:", "")
                    .replace("x=", "")
                    .replace("y=", "")
                    .replace(" ", "");
            String[] region = areaInput.split(",");

            String[] x = region[0].split("\\..");
            String[] y = region[1].split("\\..");

            Point topLeft = new Point(Math.min(Integer.parseInt(x[0]), Integer.parseInt(x[1])),
                    Math.min(Integer.parseInt(y[0]), Integer.parseInt(y[1])));
            Point bottomRight = new Point(Math.max(Integer.parseInt(x[0]), Integer.parseInt(x[1])),
                    Math.max(Integer.parseInt(y[0]), Integer.parseInt(y[1])));

            result = new Area(topLeft, bottomRight);
        } catch (IOException ignored) {}
        return result;
    }
}
