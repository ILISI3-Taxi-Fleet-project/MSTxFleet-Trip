package com.ilisi.mstxfleettrip.entity;


import com.netflix.discovery.shared.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    private List<Coordinate> coordinates;

    public void addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public void removeCoordinate(Coordinate coordinate) {
        coordinates.remove(coordinate);
    }

    public void removeCoordinate(int index) {
        coordinates.remove(index);
    }

    public void clearCoordinates() {
        coordinates.clear();
    }
}
