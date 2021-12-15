package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ADay(day = "day15")
public class Day15 implements AdventDay {

    public record Chiton(int x, int y, long distance){

    }


    @Override
    public String part1(BufferedReader reader) {
        long[][] grid = formatInput(reader);
        return String.valueOf(findPath(grid, grid.length - 1,grid[0].length - 1 , 1));
    }

    @Override
    public String part2(BufferedReader reader) {
        long[][] grid = formatInput(reader);
        return String.valueOf(findPath(grid, (grid.length * 5) - 1,(grid[0].length * 5) - 1 , 5) - 1);
    }

    private long findPath(long[][] grid, int col, int row, int multiplier){
        long[][] dist = new long[grid.length * multiplier][grid[0].length * multiplier];

        int[] dx = { 1, 0 };
        int[] dy = { 0, 1 };

        for(int x = 0; x < dist.length; x++){
            for(int y = 0; y < dist[0].length; y++){
                dist[x][y] = Integer.MAX_VALUE;
            }
        }
        dist[0][0] = 0;

        PriorityQueue<Chiton> queue = new PriorityQueue<>(row * col, (o1, o2) -> {
            if(o1.distance < o2.distance) return -1;
            if(o1.distance > o2.distance) return 1;
            return 0;
        });

        queue.add(new Chiton(0, 0, 0));
        while (!queue.isEmpty()){
            Chiton current = queue.poll();
            for(int i = 0; i < dx.length; i++){
                int rows = current.x + dx[i];
                int columns = current.y + dy[i];
                if(rows >= 0 && rows < dist.length && columns >= 0 && columns < dist[0].length){
                    long gridNum = (grid[rows % grid.length][columns % grid.length] + (rows/dist.length)) % 10;
                    if (dist[rows][columns] > dist[current.x][current.y] + gridNum)
                    {
                        if (dist[rows][columns] != Integer.MAX_VALUE)
                        {
                            Chiton adj = new Chiton(rows, columns,
                                    dist[rows][columns]);
                            queue.remove(adj);
                        }

                        dist[rows][columns] = dist[current.x][current.y] +
                                gridNum;

                        queue.add(new Chiton(rows, columns,
                                dist[rows][columns]));
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
