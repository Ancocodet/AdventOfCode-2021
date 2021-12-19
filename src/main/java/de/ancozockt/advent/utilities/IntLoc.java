package de.ancozockt.advent.utilities;

import com.google.common.base.Objects;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntLoc {

    public final int x;
    public final int y;

    public IntLoc() {
        this(0, 0);
    }

    public IntLoc(IntLoc p) {
        this(p.x, p.y);
    }

    public IntLoc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntLoc(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IntLoc move(int dx, int dy) {
        return new IntLoc(x + dx, y + dy);
    }

    public IntLoc move(IntLoc l) {
        return new IntLoc(x + l.x, y + l.y);
    }

    public Point getPoint() {
        return new Point(Math.toIntExact(x), Math.toIntExact(y));
    }

    public static Stream<IntLoc> range(int i, int j){
        return IntStream.range(0, i).boxed().flatMap(x -> IntStream.range(0, j).mapToObj(y -> new IntLoc(x, y)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntLoc loc = (IntLoc) o;
        return x == loc.x && y == loc.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

}
