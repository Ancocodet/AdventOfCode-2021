package de.ancozockt.advent.utilities.day18;

import java.util.Objects;

public class Literal extends Element{
    public int value;

    public Literal(Pair parent, int value) {
        super(parent);
        this.value = value;
    }

    public long getMagnitude() {
        return value;
    }

    public Pair split() {
        int split = value / 2;
        return new Pair(this.parent, new Literal(null, split), new Literal(null, value - split));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal = (Literal) o;
        return value == literal.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public Literal clone() {
        return new Literal(null, value);
    }
}
