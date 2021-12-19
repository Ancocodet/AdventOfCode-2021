package de.ancozockt.advent.utilities;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Measurement {

    Vector3D measurement;

    List<VectorDistance> relative;

    public Measurement(Vector3D measurement, List<Vector3D> all) {
        this.measurement = measurement;
        relative = new ArrayList<>();
        for (Vector3D v : all) {
            if (v != measurement) {
                relative.add(new VectorDistance(measurement, v));
            }
        }
    }

    public Pair<List<VectorDistance>, List<VectorDistance>> has12Common(Measurement m2) {
        Set<Integer> m2Dists = m2.relative.stream().map(VectorDistance::getDistance).collect(
                Collectors.toSet());
        List<Integer> commonDists = relative.stream().map(VectorDistance::getDistance)
                .filter(m2Dists::contains).collect(Collectors.toList());
        if (commonDists.size() < 11) {
            return null;
        }
        List<VectorDistance> ofThis = relative.stream()
                .filter(vd -> commonDists.contains(vd.distance))
                .collect(Collectors.toList());
        List<VectorDistance> ofOther = m2.relative.stream()
                .filter(vd -> commonDists.contains(vd.distance))
                .collect(Collectors.toList());
        return Pair.of(ofThis, ofOther);
    }

}
