package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.Cave;

import java.io.BufferedReader;
import java.util.*;


@ADay(day = "day12")
public class Day12 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        HashMap<String, Cave> caves = formatInput(reader);

        Cave startCave = caves.get("start");
        Set<List<Cave>> routesFromStart = new HashSet<>();
        List<Cave> route = List.of(startCave);

        findPathPartOne(routesFromStart, route, startCave);

        return String.valueOf(routesFromStart.size());
    }

    @Override
    public String part2(BufferedReader reader) {
        HashMap<String, Cave> caves = formatInput(reader);

        Cave startCave = caves.get("start");
        Set<List<Cave>> routesFromStart = new HashSet<>();
        List<Cave> route = List.of(startCave);

        findPathPartTwo(routesFromStart, route, startCave, false);

        return String.valueOf(routesFromStart.size());
    }

    private void findPathPartTwo(Set<List<Cave>> routesFromStart, List<Cave> route, Cave cave, boolean containSmallCaveTwice) {
        if (cave.getValue().equals("end")) {
            route.add(cave);
            routesFromStart.add(route);
        } else {
            for (Cave nextCave : cave.getNextCaves()) {
                if (!nextCave.isSmall() || !route.contains(nextCave)) {
                    List<Cave> continuingRoute = new ArrayList<>(route);
                    continuingRoute.add(nextCave);

                    findPathPartTwo(routesFromStart, continuingRoute, nextCave, containSmallCaveTwice);
                } else if (nextCave.isSmall() && !containSmallCaveTwice && !nextCave.getValue().equals("start")) {
                    List<Cave> continuingRoute = new ArrayList<>(route);
                    continuingRoute.add(nextCave);

                    findPathPartTwo(routesFromStart, continuingRoute, nextCave, true);
                }
            }
        }
    }

    private void findPathPartOne(Set<List<Cave>> routesFromStart, List<Cave> route, Cave cave) {

        if (cave.getValue().equals("end")) {
            route.add(cave);
            routesFromStart.add(route);
        }

        for (Cave nextCave : cave.getNextCaves()) {
            if (!nextCave.isSmall() || !route.contains(nextCave)) {
                List<Cave> continuingRoute = new ArrayList<>(route);
                continuingRoute.add(nextCave);
                findPathPartOne(routesFromStart, continuingRoute, nextCave);
            }
        }
    }

    private HashMap<String, Cave> formatInput(BufferedReader reader){
        HashMap<String, Cave> result = new HashMap<>();

        reader.lines().forEach(line -> {
            String[] caves = line.split("-");

            Cave firstCave = result.getOrDefault(caves[0], new Cave());
            Cave secondCave = result.getOrDefault(caves[1], new Cave());

            if(firstCave.getValue() == null){
                firstCave.setValue(caves[0]);
                firstCave.setSmall(caves[0].matches("[a-z]+"));

                result.put(caves[0], firstCave);
            }

            if(secondCave.getValue() == null){
                secondCave.setValue(caves[1]);
                secondCave.setSmall(caves[1].matches("[a-z]+"));

                result.put(caves[1], secondCave);
            }

            firstCave.addNextCave(secondCave);
            secondCave.addNextCave(firstCave);
        });

        return result;
    }

}
