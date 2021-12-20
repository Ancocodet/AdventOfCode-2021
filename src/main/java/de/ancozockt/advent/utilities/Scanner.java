package de.ancozockt.advent.utilities;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scanner {

    private final List<Vector3D> givenMeasurements;
    private final List<Measurement> measurements;

    public List<Transformation> transformationsTo = new ArrayList<>();

    public Scanner(List<Vector3D> givenMeasurements) {
        this.givenMeasurements = givenMeasurements;
        measurements = givenMeasurements.stream()
                .map(measurement -> new Measurement(measurement, givenMeasurements))
                .toList();
    }

    public Vector3D getPlacementTo() {
        Vector3D transformed = new Vector3D(0, 0, 0);
        for (int i = transformationsTo.size() - 1; i >= 0; i--) {
            transformed = transformationsTo.get(i).apply(transformed);
        }
        return transformed;
    }

    public List<Vector3D> getTransformedTo() {
        return givenMeasurements.stream().map(measurement -> {
            Vector3D transformed = measurement;
            for (int i = transformationsTo.size() - 1; i >= 0; i--) {
                transformed = transformationsTo.get(i).apply(transformed);
            }
            return transformed;
        }).toList();
    }

    public Transformation getTransformationIfExists(Scanner scanner) {
        Pair<List<VectorDistance>, List<VectorDistance>> commons = null;
        for (Measurement m : measurements) {
            if (commons != null) {
                break;
            }
            for (Measurement s : scanner.measurements) {
                if (commons != null) {
                    break;
                }
                commons = m.has12Common(s);
            }
        }
        if (commons == null) {
            return null;
        }
        return calculateTransformation(commons);
    }

    private Transformation calculateTransformation(
            Pair<List<VectorDistance>, List<VectorDistance>> commons) {
        for (int i = 1; i < 25; i++) {
            Transformation transformation = Transformation.of(i);
            Vector3D move = transformation.matches(commons);
            if (move != null) {
                transformation.setMove(move);
                return transformation;
            }
        }
        return null;
    }

}
