package de.ancozockt.advent.utilities.day18;

public abstract class Element implements Cloneable{

    public Pair parent;

    public Element(Pair parent) {
        this.parent = parent;
    }

    public abstract long getMagnitude();
    public abstract Element clone();

}
