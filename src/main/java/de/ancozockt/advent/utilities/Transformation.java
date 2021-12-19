package de.ancozockt.advent.utilities;

import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformation {

    private final Function<Vector3D, Double> getX;
    private final Function<Vector3D, Double> getY;
    private final Function<Vector3D, Double> getZ;

    private final int num;

    @Setter
    private Vector3D move;

    public Transformation(Function<Vector3D, Double> getX,
                          Function<Vector3D, Double> getY,
                          Function<Vector3D, Double> getZ,
                          int num) {
        this.getX = getX;
        this.getY = getY;
        this.getZ = getZ;
        this.num = num;
    }

    public Vector3D apply(Vector3D to) {
        Vector3D rotated = new Vector3D(getX.apply(to), getY.apply(to), getZ.apply(to));
        return move == null ? rotated : rotated.add(move);
    }

    public static Transformation of(int num) {
        switch (num) {
            case 1:
                return new Transformation(v -> v.getX(), v -> v.getY(), v -> v.getZ(), 1);
            case 2:
                return new Transformation(v -> v.getX(), v -> v.getZ(), v -> -v.getY(), 2);
            case 3:
                return new Transformation(v -> v.getX(), v -> -v.getY(), v -> -v.getZ(), 3);
            case 4:
                return new Transformation(v -> v.getX(), v -> -v.getZ(), v -> v.getY(), 4);///
            case 5:
                return new Transformation(v -> -v.getX(), v -> -v.getY(), v -> v.getZ(), 5);
            case 6:
                return new Transformation(v -> -v.getX(), v -> v.getZ(), v -> v.getY(), 6);
            case 7:
                return new Transformation(v -> -v.getX(), v -> v.getY(), v -> -v.getZ(), 7);
            case 8:
                return new Transformation(v -> -v.getX(), v -> -v.getZ(), v -> -v.getY(), 8);///
            case 9:
                return new Transformation(v -> v.getY(), v -> -v.getX(), v -> v.getZ(), 9);
            case 10:
                return new Transformation(v -> v.getY(), v -> v.getZ(), v -> v.getX(), 10);
            case 11:
                return new Transformation(v -> v.getY(), v -> v.getX(), v -> -v.getZ(), 11);
            case 12:
                return new Transformation(v -> v.getY(), v -> -v.getZ(), v -> -v.getX(), 12);///
            case 13:
                return new Transformation(v -> -v.getY(), v -> v.getX(), v -> v.getZ(), 13);
            case 14:
                return new Transformation(v -> -v.getY(), v -> -v.getZ(), v -> v.getX(), 14);
            case 15:
                return new Transformation(v -> -v.getY(), v -> -v.getX(), v -> -v.getZ(), 15);
            case 16:
                return new Transformation(v -> -v.getY(), v -> v.getZ(), v -> -v.getX(), 16);///
            case 17:
                return new Transformation(v -> v.getZ(), v -> v.getY(), v -> -v.getX(), 17);
            case 18:
                return new Transformation(v -> v.getZ(), v -> v.getX(), v -> v.getY(), 18);
            case 19:
                return new Transformation(v -> v.getZ(), v -> -v.getY(), v -> v.getX(), 19);
            case 20:
                return new Transformation(v -> v.getZ(), v -> -v.getX(), v -> -v.getY(), 20);///
            case 21:
                return new Transformation(v -> -v.getZ(), v -> v.getY(), v -> v.getX(), 21);
            case 22:
                return new Transformation(v -> -v.getZ(), v -> -v.getX(), v -> v.getY(), 22);
            case 23:
                return new Transformation(v -> -v.getZ(), v -> -v.getY(), v -> -v.getX(), 23);
            case 24:
                return new Transformation(v -> -v.getZ(), v -> v.getX(), v -> -v.getY(), 24);
        }
        throw new IllegalStateException("Transformation num should be between 1 and 24!");
    }

    public Vector3D matches(Pair<List<VectorDistance>, List<VectorDistance>> commons) {
        Vector3D newOrigin = this.apply(commons.getRight().get(0).from);
        List<VectorDistance> transformed = commons.getRight().stream()
                .map(vd -> new VectorDistance(newOrigin, this.apply(vd.to), vd.distance)).toList();
        int errorLimit = transformed.size() - 11;
        Map<Integer, Long> grouped = commons.getLeft().stream().map(VectorDistance::getDistance)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Long val : grouped.values()) {
            if (val > 1) {
                errorLimit += val * (val - 1);
            }
        }
        Vector3D dist = commons.getLeft().get(0).from.add(transformed.get(0).from.negate());
        int errCount = 0;
        for (int i = 0; i < commons.getLeft().size(); i++) {
            VectorDistance left = commons.getLeft().get(i);
            for (int j = 1; j < transformed.size(); j++) {
                VectorDistance right = transformed.get(j);
                if (left.distance.equals(right.distance)) {
                    Vector3D currentDist = left.to.add(right.to.negate());
                    if (!dist.equals(currentDist)) {
                        errCount++;
                    }
                }
                if (errCount > errorLimit) {
                    return null;
                }
            }
        }
        return dist;
    }

}
