package de.ancozockt.advent;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.FileHelper;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        Reflections reflections = new Reflections("de.ancozockt.advent.days");
        FileHelper fileHelper = new FileHelper();

        File folder = new File("outputs");
        if(!folder.exists()){
            folder.mkdirs();
        }

        fileHelper.downloadInput("https://adventofcode.com/2021/day/5/input", new File("outputs", "day5-input"));

        reflections.getTypesAnnotatedWith(ADay.class).forEach(aClass -> {
            ADay aDay = aClass.getAnnotation(ADay.class);
            AdventDay adventDay = (AdventDay) createNewInstanceOfClass(aClass);

            File file = new File("outputs", aDay.day() + ".txt");
            if(!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException exception){}
            }

            try {
                FileWriter fileWriter = new FileWriter(file);

                fileWriter.write("Part1: " + Objects.requireNonNull(adventDay).part1(fileHelper.getFileInput("inputs/" + aDay.day() + "-input")));
                fileWriter.write("\nPart2: " + Objects.requireNonNull(adventDay).part2(fileHelper.getFileInput("inputs/" + aDay.day() + "-input")));

                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private static <T> T createNewInstanceOfClass(Class<T> someClass) {
        try {
            return someClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }


}
