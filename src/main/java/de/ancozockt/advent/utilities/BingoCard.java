package de.ancozockt.advent.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class BingoCard {

    private HashMap<Integer, Integer[]> rows;

    public BingoCard(){
        rows = new HashMap<Integer, Integer[]>();
    }

    public void addRow(String line){
        ArrayList<Integer> numbers = new ArrayList<>();
        String usedLine = line.replace(" ", "-").replace("--", "-");
        for(String number : usedLine.split("-")){
            if(number.length() > 0){
                numbers.add(Integer.parseInt(number));
            }
        }
        rows.put(rows.size(), numbers.toArray(new Integer[]{}));
    }

    public void input(int number){
        rows.forEach((integer, integers) -> {
            int pos = 0;
            while (pos < integers.length){
                if(integers[pos] == number){
                    integers[pos] = -1;
                }
                pos++;
            }
        });
    }

    private boolean hasHorizontalWin(){
        boolean won = true;
        for(int i = 0; i < 5; i++){
            won = true;
            Integer[] row = rows.get(i);
            for(int x = 0; x < 5; x++){
                if(row[x] != -1){
                    won = false;
                    break;
                }
            }
            if(won) break;
        }
        return won;
    }

    private boolean hasVerticalWin(){
        boolean won = true;
        for(int x = 0; x < 5; x++){
            won = true;
            for(int y = 0; y < 5; y++){
                if(rows.get(y)[x] != -1){
                    won = false;
                    break;
                }
            }
            if(won) break;
        }
        return won;
    }

    public boolean hasWin(){
        return hasHorizontalWin() || hasVerticalWin();
    }

    public int sumOfNotSet(){
        int value = 0;
        for(int x = 0; x < 5; x++){
            Integer[] row = rows.get(x);
            for(int y = 0; y < 5; y++){
                if(row[y] != -1){
                    value += row[y];
                }
            }
        }
        return value;
    }

    @Override
    public String toString() {
        AtomicReference<String> output = new AtomicReference<>("");
        rows.forEach((integer, integers) -> {
            String row = "";
            for(int num : integers){
                row += " " + num;
            }
            output.set(output.get() + row + "\n");
        });
        return output.get();
    }
}
