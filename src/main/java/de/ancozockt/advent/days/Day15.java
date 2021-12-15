package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

@ADay(day = "day15")
public class Day15 implements AdventDay {

    public record Chiton(int x, int y, long distance){

    }


    @Override
    public String part1(BufferedReader reader) {
        long[][] grid = formatInput(reader);
        return String.valueOf(findPath(grid));
    }

    @Override
    public String part2(BufferedReader reader) {
        long[][] grid = formatInput(reader);
        grid = transformGrid(grid, 5);
        return String.valueOf(findPath(grid));
    }

    private long[][] transformGrid(long[][] currentGrid, int multiplier){
        long[][] newGrid = new long[(currentGrid.length * multiplier)][(currentGrid[0].length * multiplier)];

        for(int x = 0; x < newGrid.length; x++){
            for(int y = 0; y < newGrid[0].length; y++){
                long gridNum = currentGrid[x % currentGrid.length][y % currentGrid[0].length] + (x / currentGrid.length) + (y / currentGrid[0].length);
                if (gridNum > 9) {
                    gridNum -= 9;
                }
                newGrid[x][y] = gridNum;
            }
        }

        return newGrid;
    }

    private long findPath(long[][] grid){
        long[][] dist = new long[grid.length][grid[0].length];

        int[] dx = { 1, 0, -1, 0};
        int[] dy = { 0, 1, 0, -1};

        for(int x = 0; x < dist.length; x++){
            for(int y = 0; y < dist[0].length; y++){
                dist[x][y] = Long.MAX_VALUE;
            }
        }
        dist[0][0] = 0;

        PriorityQueue<Chiton> queue = new PriorityQueue<>((grid.length-1) * (grid[0].length - 1), Comparator.comparingLong(o -> o.distance));

        queue.add(new Chiton(0, 0, 0));
        while (!queue.isEmpty()){
            Chiton current = queue.poll();
            for(int i = 0; i < dx.length; i++){
                int rows = current.x + dx[i];
                int columns = current.y + dy[i];
                if(rows >= 0 && rows < dist.length && columns >= 0 && columns < dist[0].length){
                    long gridNum = grid[rows % grid.length][columns % grid.length] + (rows/grid.length) + (columns/grid[0].length);

                    if (dist[rows][columns] > dist[current.x][current.y] + gridNum) {
                        if (dist[rows][columns] != Long.MAX_VALUE) {
                            Chiton adj = new Chiton(rows, columns, dist[rows][columns]);
                            queue.remove(adj);
                        }
                        dist[rows][columns] = dist[current.x][current.y] + gridNum;
                        queue.add(new Chiton(rows, columns, dist[rows][columns]));
                    }
                }
            }
        }

        return dist[dist.length-1][dist[0].length-1];
    }

   private long[][] formatInput(BufferedReader reader){
       List<String> input = reader.lines().toList();
       long[][] grid = new long[input.size()][];

       AtomicInteger pos = new AtomicInteger(0);
       input.forEach(line -> {
           grid[pos.getAndIncrement()] = Arrays.stream(line.split("")).map(String::trim).filter(v -> !v.isEmpty()).mapToLong(Long::parseLong).toArray();
       });

       return grid;
   }

}
