package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;
import de.ancozockt.advent.utilities.BingoCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ADay(day = "day4")
public class Day4 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        ArrayList<BingoCard> cards = new ArrayList<>();
        String input = "";

        BingoCard card = null;
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(line.contains(",")){
                    input += line;
                }else if(line.length() <= 1){
                    if(card != null) cards.add(card);
                    card = new BingoCard();
                }else if(card != null){
                    card.addRow(line);
                }
            }
        }catch (IOException ignored) {}

        if(card != null) cards.add(card);

        int lastCall = 0;
        List<BingoCard> win = null;
        for(String currentInput : input.split(",")){
            int number = Integer.parseInt(currentInput);
            cards.forEach(bingoCard -> bingoCard.input(number));
            win = cards.stream().filter(BingoCard::hasWin).collect(Collectors.toList());
            if(win.size() > 0){
                lastCall = number;
                break;
            }
        }

        if(win != null && win.size() > 0){
            return String.valueOf(win.get(0).sumOfNotSet() * lastCall);
        }

        return "0";
    }

    @Override
    public String part2(BufferedReader reader) {
        ArrayList<BingoCard> cards = new ArrayList<>();
        String input = "";

        BingoCard card = null;
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(line.contains(",")){
                    input += line;
                }else if(line.length() <= 1){
                    if(card != null) cards.add(card);
                    card = new BingoCard();
                }else if(card != null){
                    card.addRow(line);
                }
            }
        }catch (IOException ignored) {}

        if(card != null) cards.add(card);

        int lastCall = 0;
        List<BingoCard> win = null;
        for(String currentInput : input.split(",")){
            int number = Integer.parseInt(currentInput);
            cards.forEach(bingoCard -> bingoCard.input(number));
            win = cards.stream().filter(BingoCard::hasWin).collect(Collectors.toList());
            lastCall = number;
            if(cards.size() > 1 && win.size() > 0){
                cards.removeAll(win);
                System.out.println(cards.size());
            }else if(win.size() > 0){
                break;
            }
        }

        if(cards.size() > 0){
            return String.valueOf(cards.get(0).sumOfNotSet() * lastCall);
        }

        return "0";
    }
}
