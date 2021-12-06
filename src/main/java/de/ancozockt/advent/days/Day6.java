package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.Lanternfish;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@ADay(day = "day6")
public class Day6 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        return String.valueOf(simulateDays(80, formatInput(reader)).size());
    }

    @Override
    public String part2(BufferedReader reader) {
        return String.valueOf(simulateYears(1, formatInput(reader)));
    }

    private long simulateYears(int years, ArrayList<Lanternfish> input){
        ArrayList<Lanternfish> lanternfish = new ArrayList<>();

        input.forEach(fish -> {
            Optional<Lanternfish> existingFish = lanternfish.stream().filter(counterFish -> counterFish.getTimer() == fish.getTimer()).findFirst();
            if(existingFish.isPresent()){
                existingFish.get().addAmount();
            }else{
                lanternfish.add(fish);
            }
        });

        for(int i = 0; i < 256 * years; i++){
            AtomicLong newFishes = new AtomicLong();
            lanternfish.forEach(fish -> {
                if(fish.isFinished()){
                    fish.setTimer(6);
                    newFishes.getAndAdd(fish.getAmount());
                }else{
                    fish.countDown();
                }
            });
            lanternfish.add(new Lanternfish(8, newFishes.get()));
        }

        AtomicLong amount = new AtomicLong(0);
        lanternfish.forEach(fish -> {
            amount.getAndAdd(fish.getAmount());
        });

        return amount.get();
    }

    private ArrayList<Lanternfish> simulateDays(int days, ArrayList<Lanternfish> input){
        ArrayList<Lanternfish> lanternfish = input;

        for(int i = 0; i < days; i++){
            AtomicInteger newFishes = new AtomicInteger();
            lanternfish.forEach(fish -> {
                if(fish.isFinished()){
                    fish.setTimer(6);
                    newFishes.getAndIncrement();
                }else{
                    fish.countDown();
                }
            });
            for(int f = 0; f < newFishes.get(); f++){
                lanternfish.add(new Lanternfish(8));
            }
        }

        return lanternfish;
    }

    private ArrayList<Lanternfish> formatInput(BufferedReader reader){
        StringBuilder input = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                input.append(line);
            }
        }catch (IOException ignored) {}

        ArrayList<Lanternfish> result = new ArrayList<>();

        for(String fish : input.toString().split(",")){
            result.add(new Lanternfish(Integer.parseInt(fish)));
        }

        return result;
    }
}
