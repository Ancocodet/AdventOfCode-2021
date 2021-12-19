package de.ancozockt.advent.utilities;

import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VectorDistance {

    Vector3D from;
    @Getter
    Vector3D to;
    @Getter
    Integer distance;

    public VectorDistance(Vector3D from, Vector3D to) {
        this.from = from;
        this.to = to;
        distance = (int) to.add(from.negate()).getNorm1();
    }

    public VectorDistance(Vector3D from, Vector3D to, Integer distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

}
