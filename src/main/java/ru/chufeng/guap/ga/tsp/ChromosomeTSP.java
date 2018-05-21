package ru.chufeng.guap.ga.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChromosomeTSP {
    //    Adjacency representation
    private ArrayList<Integer> route;
    private ArrayList<Integer> routeRepresentation;
    private double routeLength;

    public double getRouteLength() {
        return routeLength;
    }

    ChromosomeTSP(int size) {
        route = getNewRoute(size);
        routeRepresentation = getRouteRepresentation(route);
        routeLength = calcRouteLength();
    }

    ChromosomeTSP(ArrayList<Integer> route) {
        this.route = route;
        this.routeRepresentation = getRouteRepresentation(route);
        routeLength = calcRouteLength();
    }

    private ArrayList<Integer> getNewRoute(int size) {
        ArrayList<Integer> path = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        return path;
    }
    private ArrayList<Integer> getRouteRepresentation(ArrayList<Integer> route) {
        Integer reprArray[] = new Integer[route.size()];
        for (int i = 1; i <= route.size(); i++) {
            if (i == route.size()) {
                reprArray[route.get(i - 1)] = 0;
            } else {
                reprArray[route.get(i - 1)] = route.get(i);
            }
        }
        ArrayList<Integer> repr = new ArrayList<>();
        repr.addAll(Arrays.asList(reprArray));
        //repr.forEach(System.out::println);
        return repr;
    }

    double calcLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    double calcRouteLength() {
        double length = 0;
        for (int i = 1; i < route.size(); i++) {
            City city1 = TspGA.cities.get(route.get(i - 1));
            City city2 = TspGA.cities.get(route.get(i));
            length += calcLength(city1.x, city1.y, city2.x, city2.y);
        }
        return length;
    }
}
