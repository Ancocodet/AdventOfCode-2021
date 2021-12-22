package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@ADay(day = "day22")
public class Day22 implements AdventDay {

    public record Cuboid(int x, int y, int z){

    }

    public record Cuboids(long x1, long x2, long y1, long y2, long z1, long z2, long on){
        public long count(){
            return (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1) * on;
        }
    }

    public record Reboot(boolean turnOn, int[] xRegion, int[] yRegion, int[] zRegion){

    }

    @Override
    public String part1(BufferedReader reader) {
        Queue<Reboot> steps = formatInput(reader);
        Set<Cuboid> litCuboids = new HashSet<>();

        steps.stream().filter(this::isDefaultRegion).forEach(currentStep -> {
            IntStream.range(currentStep.xRegion[0], currentStep.xRegion[1] + 1).forEach(x -> {
                IntStream.range(currentStep.yRegion[0], currentStep.yRegion[1] + 1).forEach(y -> {
                    IntStream.range(currentStep.zRegion[0], currentStep.zRegion[1] + 1).forEach(z -> {
                        Cuboid cuboid = new Cuboid(x, y, z);
                        if(currentStep.turnOn()) litCuboids.add(cuboid);
                        else litCuboids.remove(cuboid);
                    });
                });
            });
        });

        long[] region = {-50, 50};
        return String.valueOf(litCuboids.stream().filter(cuboid -> isInRegion(cuboid, region, region, region)).count());
    }

    @Override
    public String part2(BufferedReader reader) {
        Queue<Reboot> steps = formatInput(reader);
        List<Cuboids> cuboids = new ArrayList<>();

        steps.forEach(currentStep -> {
            long minX = Math.min(currentStep.xRegion[0], currentStep.xRegion[1]);
            long maxX = Math.max(currentStep.xRegion[0], currentStep.xRegion[1]);
            long minY = Math.min(currentStep.yRegion[0], currentStep.yRegion[1]);
            long maxY = Math.max(currentStep.yRegion[0], currentStep.yRegion[1]);
            long minZ = Math.min(currentStep.zRegion[0], currentStep.zRegion[1]);
            long maxZ = Math.max(currentStep.zRegion[0], currentStep.zRegion[1]);
            Cuboids c = new Cuboids(minX, maxX, minY, maxY, minZ, maxZ, currentStep.turnOn ? 1 : -1);
            List<Cuboids> toAdd = new ArrayList<>();
            cuboids.forEach(cuboid -> {
                long x0 = Math.max(c.x1, cuboid.x1);
                long x1 = Math.min(c.x2, cuboid.x2);
                long y0 = Math.max(c.y1, cuboid.y1);
                long y1 = Math.min(c.y2, cuboid.y2);
                long z0 = Math.max(c.z1, cuboid.z1);
                long z1 = Math.min(c.z2, cuboid.z2);
                if(x0 <= x1 && y0 <= y1 && z0 <= z1){
                    toAdd.add(new Cuboids(x0, x1, y0, y1, z0, z1, -cuboid.on));
                }
            });
            if(currentStep.turnOn()) toAdd.add(c);
            cuboids.addAll(toAdd);
        });

        AtomicLong sum = new AtomicLong(0);
        cuboids.forEach(cuboid -> {
            sum.getAndAdd(cuboid.count());
        });
        return String.valueOf(sum.get());
    }

    private boolean isInRegion(Cuboid cuboid, long[] xRegion, long[] yRegion, long[] zRegion){
        return (cuboid.x() >= Math.min(xRegion[0], xRegion[1]) && cuboid.x() <= Math.max(xRegion[0], xRegion[1])) &&
                (cuboid.y() >= Math.min(yRegion[0], yRegion[1]) && cuboid.y() <= Math.max(yRegion[0], yRegion[1])) &&
                (cuboid.z() >= Math.min(zRegion[0], zRegion[1]) && cuboid.z() <= Math.max(zRegion[0], zRegion[1]));
    }

    private boolean isDefaultRegion(Reboot reboot){
        return (reboot.xRegion[0] >= -50 && reboot.xRegion[0] <= 50 && reboot.yRegion[0] >= -50 && reboot.yRegion[0] <= 50 && reboot.zRegion[0] >= -50 && reboot.zRegion[0] <= 50) &&
                (reboot.xRegion[1] >= -50 && reboot.xRegion[1] <= 50 && reboot.yRegion[1] >= -50 && reboot.yRegion[1] <= 50 && reboot.zRegion[1] >= -50 && reboot.zRegion[1] <= 50);
    }

    private Queue<Reboot> formatInput(BufferedReader reader){
        Queue<Reboot> steps = new ArrayDeque<>();

        reader.lines()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
            boolean turnOn = line.startsWith("on");
            String[] coordinates = line.replace("on ", "").replace("off ", "").split(",");

            String[] x = coordinates[0].replace("x=", "").replace("..", "_").split("_");
            int[] xRegion = {Integer.parseInt(x[0]), Integer.parseInt(x[1])};
            String[] y = coordinates[1].replace("y=", "").replace("..", "_").split("_");
            int[] yRegion = {Integer.parseInt(y[0]), Integer.parseInt(y[1])};
            String[] z = coordinates[2].replace("z=", "").replace("..", "_").split("_");
            int[] zRegion = {Integer.parseInt(z[0]), Integer.parseInt(z[1])};

            steps.add(new Reboot(turnOn, xRegion, yRegion, zRegion));
        });

        return steps;
    }

}
