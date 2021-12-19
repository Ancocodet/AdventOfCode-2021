package de.ancozockt.advent.utilities;

import lombok.Getter;

@Getter
public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
