package ru.chufeng.guap.ga.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChromosomeRoute {
    //    Adjacency representation
    private ArrayList<Integer> route;
    private ArrayList<Integer> routeRepresentation;
    private double routeLength;

    public ArrayList<Integer> getRoute() {
        return route;
    }

    double getRouteLength() {
        return routeLength;
    }

    boolean setRouteEdge(int from, int to) {
        if (routeRepresentation.get(from) == null && from != to) {
            this.routeRepresentation.set(from, to);
            return true;
        } else {
            return false;
        }
    }

    boolean nullExists() {
        for (Integer point : routeRepresentation) {
            if (point == null) return true;
        }
        return false;
    }

    int getFreeIndex() {
        for (int i = 0; i < routeRepresentation.size(); i++) {
            if (routeRepresentation.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder routeStr = new StringBuilder();
        for (Integer city : routeRepresentation) {
            //String cityStr = (city == null) ? "null" : city.toString();
            routeStr.append(city).append(" ");
        }
        return routeStr.toString() + Double.toString(routeLength);
    }

    ChromosomeRoute() {
    }

    void initRouteRepresentation(int size) {
        routeRepresentation = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            routeRepresentation.add(null);
        }
    }

    ChromosomeRoute(int size) {
        route = getNewRoute(size);
        routeRepresentation = getRouteRepresentation(route);
        routeLength = calcRouteLength();
    }

    ChromosomeRoute(ChromosomeRoute chromosome) {
        this.route = chromosome.route;
        this.routeRepresentation = chromosome.routeRepresentation;
        this.routeLength = chromosome.routeLength;
    }

    void copyFrom(ChromosomeRoute chromosome) {
        this.route = chromosome.route;
        this.routeRepresentation = chromosome.routeRepresentation;
        this.routeLength = chromosome.routeLength;
    }

    ChromosomeRoute(ArrayList<Integer> route) {
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
                reprArray[route.get(i - 1)] = route.get(0);
            } else {
                reprArray[route.get(i - 1)] = route.get(i);
            }
        }
        ArrayList<Integer> repr = new ArrayList<>();
        repr.addAll(Arrays.asList(reprArray));
        //repr.forEach(System.out::println);
        return repr;
    }

    static double calcLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    int getDestFrom(int from) {
        return routeRepresentation.get(from);
    }

    double calcRouteLength() {
        double length = 0;
        for (int i = 1; i < route.size(); i++) {
            City city1 = TspGA.cities.get(route.get(i - 1));
            City city2 = TspGA.cities.get(route.get(i));
            length += calcLength(city1.x, city1.y, city2.x, city2.y);
        }
        City city1 = TspGA.cities.get(route.get(0));
        City city2 = TspGA.cities.get(route.get(route.size() - 1));
        length += calcLength(city1.x, city1.y, city2.x, city2.y);
        return length;
    }

    void calcRoute() {
        Integer routeArray[] = new Integer[routeRepresentation.size()];
        routeArray[0] = 0;
        for (int i = 1; i < routeRepresentation.size(); i++) {
            if (routeArray[i - 1] != null) {
                routeArray[i] = routeRepresentation.get(routeArray[i - 1]);
            }
        }
        route = new ArrayList<>();
        route.addAll(Arrays.asList(routeArray));
        if (!nullExists()) {
            routeLength = calcRouteLength();
        }
    }
}
