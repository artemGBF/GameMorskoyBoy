package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShipList {
    private ArrayList<Ship> ships = new ArrayList<Ship>();

    private Set<Pair<Integer, Integer>> coordinates = new HashSet<Pair<Integer, Integer>>();

    public int getLength() {
        return ships.size();
    }

    public Ship getShip(int i) {
        return ships.get(i);
    }

    public ShipList(int count, int size) {
        for (int i = 0; i < count; i++) {
            Ship a = new Ship("name", size);
            Pair<Integer, Integer> p = new Pair<Integer, Integer>(a.getxPos(), a.getyPos());
            if (!coordinates.contains(p)) {
                ships.add(a);
                coordinates.add(p);
            } else {
                i--;
            }
        }
    }

    public Ship getByCoordinates(int i, int j) {
        for (Ship a : ships) {
            if (a.getxPos() == i && a.getyPos() == j) {
                return a;
            }
        }
        return null;
    }

    public void removeShip(int i, int j) {
        this.ships.remove(getByCoordinates(i, j));
    }

    public boolean isEmpty() {
        return this.ships.size() == 0;
    }
}
