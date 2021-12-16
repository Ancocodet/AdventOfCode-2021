package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.PacketFinder;

import java.io.BufferedReader;
import java.math.BigInteger;

@ADay(day = "day16")
public class Day16 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        String binary = hexToBin(formatInput(reader));
        long packets = new PacketFinder().findPackets(binary);
        return String.valueOf(packets);
    }

    @Override
    public String part2(BufferedReader reader) {
        String binary = hexToBin(formatInput(reader));
        long sum = new PacketFinder().findPackets2(binary, Integer.MAX_VALUE).get(0);
        return String.valueOf(sum);
    }

    private String hexToBin(String s) {
        return String.format("%"+(s.length()*4)+"s", new BigInteger(s, 16).toString(2)).replace(" ", "0");
    }

    private String formatInput(BufferedReader reader){
        StringBuilder result = new StringBuilder();

        reader.lines().forEach(result::append);

        return result.toString();
    }
}
