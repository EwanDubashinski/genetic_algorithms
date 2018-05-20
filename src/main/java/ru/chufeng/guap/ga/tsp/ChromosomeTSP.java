package ru.chufeng.guap.ga.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChromosomeTSP {
    //    Adjacency representation
    private ArrayList<Integer> route;
    private ArrayList<Integer> routeRepresentation;

    ChromosomeTSP(int size) {
        route = getNewRoute(size);
    }

    ChromosomeTSP(ArrayList<Integer> route) {
        this.route = route;
        this.routeRepresentation = getRouteRepresentation(route);
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
        repr.forEach(System.out::println);
        return repr;
    }
}
