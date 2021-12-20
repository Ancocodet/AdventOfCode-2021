package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ADay(day = "day20")
public class Day20 implements AdventDay {

    public record Pixel(int x, int y){
        public Stream<Pixel> getNeighbors() {
            return Stream.of(
                    new Pixel(x - 1, y - 1),
                    new Pixel(x, y - 1),
                    new Pixel(x + 1, y - 1),
                    new Pixel(x - 1, y),
                    this,
                    new Pixel(x + 1, y),
                    new Pixel(x - 1, y + 1),
                    new Pixel(x, y + 1),
                    new Pixel(x + 1, y + 1)
            );
        }
    }

    public record Input(List<Boolean> enhancement, List<Pixel> pixels){}

    @Override
    public String part1(BufferedReader reader) {
        Input input = formatInput(reader);

        Set<Pixel> litPixels = new HashSet<>(input.pixels());

        return String.valueOf(doubleEnhance(input.enhancement(), litPixels).size());
    }

    @Override
    public String part2(BufferedReader reader) {
        Input input = formatInput(reader);
        Set<Pixel> litPixels = new HashSet<>(input.pixels());

        for (int i = 0; i < 50; i += 2) {
            litPixels = doubleEnhance(input.enhancement, litPixels);
        }

        return String.valueOf(litPixels.size());
    }

    public Set<Pixel> enhance(List<Boolean> enhancements, Set<Pixel> pixels){
        return pixels.stream()
                .flatMap(Pixel::getNeighbors)
                .filter(pixel -> {
                    List<Boolean> binary = pixel.getNeighbors().map(pixels::contains).toList();
                    return enhancements.get(toInt(binary));
                }).collect(Collectors.toSet());
    }

    public Set<Pixel> doubleEnhance(List<Boolean> enhancements, Set<Pixel> pixels){
        if (enhancements.get(0) && !enhancements.get(enhancements.size() - 1)) {
            Set<Pixel> unlitPixels = pixels.stream()
                    .flatMap(Pixel::getNeighbors)
                    .filter(pixel -> {
                        List<Boolean> l = pixel.getNeighbors().map(pixels::contains).toList();
                        int idx = toInt(l);
                        return !enhancements.get(idx);
                    })
                    .collect(Collectors.toSet());

            return unlitPixels.stream()
                    .flatMap(Pixel::getNeighbors)
                    .filter(p -> {
                        List<Boolean> l = p.getNeighbors().map(unlitPixels::contains).map(b -> !b).toList();
                        int idx = toInt(l);
                        return enhancements.get(idx);
                    }).collect(Collectors.toSet());
        } else {
            return enhance(enhancements, enhance(enhancements, pixels));
        }
    }

    public Input formatInput(BufferedReader reader){
        List<String> lines = reader.lines().toList();

        StringBuilder enhancement = new StringBuilder();
        List<Pixel> pixels = new ArrayList<>();

        int subsetY = 0;
        boolean enhance = true;
        for(int y = 0; y < lines.size(); y++){
            String line = lines.get(y);
            if(line.isEmpty() && enhance) {
                subsetY = y + 1;
                enhance = false;
                continue;
            }
            if(enhance) enhancement.append(line);
            if(!enhance){
                String[] values = line.split("");
                for(int x = 0; x < line.length(); x++){
                    if(values[x].equals("#"))
                        pixels.add(new Pixel(x, y - subsetY));
                }
            }
        }

        return new Input(enhancement.toString().chars().mapToObj(character -> character == '#').toList(), pixels);
    }

    public static int toInt(List<Boolean> l) {
        int idx = 0;
        for (Boolean b : l) {
            idx <<= 1;
            if (b) {
                idx += 1;
            }
        }
        return idx;
    }


    private int binaryArrayToDecimal(List<Boolean> binArr){
        StringBuilder binary = new StringBuilder();
        binArr.forEach(aBoolean -> {
            if(aBoolean){
                binary.append("1");
            }else{
                binary.append("0");
            }
        });
        System.out.println(binary.toString());
        return binaryToDecimal(binary.toString());
    }

    private int binaryToDecimal(String binary){
        int response = 0;
        for(int i = 0; i < binary.length(); i++){
            int value = Integer.parseInt(String.valueOf(binary.charAt(i)));
            response += value * Math.pow(2, (binary.length()-1) - i);
        }
        return response;
    }
}
