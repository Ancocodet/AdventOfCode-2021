package de.ancozockt.advent.utilities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Cave {

    private String value;
    private boolean small;
    private List<Cave> nextCaves = new ArrayList<>();

    public void addNextCave(Cave cave) {
        this.nextCaves.add(cave);
    }

}
