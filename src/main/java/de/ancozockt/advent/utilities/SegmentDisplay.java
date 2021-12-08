package de.ancozockt.advent.utilities;

import java.util.HashMap;

public class SegmentDisplay {

    private final HashMap<Position, String> coding;

    public SegmentDisplay(){
        coding = new HashMap<>();
    }

    public void setCoding(Position position, String coding){
        System.out.println(position.toString() + "-> " + coding);
        this.coding.put(position, coding);
    }

    public String getCoding(Position position){
        return coding.getOrDefault(position, "a");
    }

    public String encodeString(String input){
        String result = isEasy(input);
        if(result == null) {
            if(input.length() == 6){
                if(input.contains(getCoding(Position.MIDDLE))){
                    if(input.contains(getCoding(Position.TOP_RIGHT))) return "9";
                    return "6";
                }else{
                    return "0";
                }
            }else if(input.length() == 5){
                if(input.contains(getCoding(Position.TOP_LEFT))) return "5";
                if(input.contains(getCoding(Position.BOTTOM_RIGHT))) return "3";
                return "2";
            }
        }
        return result;
    }

    private String isEasy(String input){
        switch (input.length()){
            case 2:
                return "1";
            case 3:
                return "7";
            case 4:
                return "4";
            case 7:
                return "8";
        }
        return null;
    }

    public enum Position{
        TOP,
        TOP_LEFT,
        TOP_RIGHT,
        MIDDLE,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM
    }


}
