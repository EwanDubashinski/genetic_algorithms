package ru.chufeng.guap.ga.tsp;

import java.util.ArrayList;
import java.util.Collections;

public class TspGA {
    private static final int POPULATION_COUNT = 1000000;
    private static final int ITERATIONS_COUNT = 100;

    static ArrayList<City> cities = new ArrayList<>();
    static {
        loadData();
    }
    public static void main(String[] args) {
        ArrayList<ChromosomeRoute> genePool = new ArrayList<>();

        for (int i = 0; i < POPULATION_COUNT; i++) {
            genePool.add(new ChromosomeRoute(cities.size()));
        }
        // Fitness function : chromosome.getRouteLength()

        for (int i = 0; i < ITERATIONS_COUNT; i++) {
            // Tournament selection
            tournament(genePool);
            // Heuristic crossover
            crossingOver(genePool);
        }


        ChromosomeRoute minRoute = getMinRoute(genePool);
        System.out.println(minRoute);

        Draw draw = new Draw(minRoute);

    }

    private static ChromosomeRoute getMinRoute(ArrayList<ChromosomeRoute> genePool) {
        ChromosomeRoute min = genePool.get(0);
        for (ChromosomeRoute chromosome : genePool) {
            double current = chromosome.getRouteLength();
            min = (current < min.getRouteLength()) ? chromosome : min;
        }
        return min;
    }

    private static void crossingOver(ArrayList<ChromosomeRoute> genePool) {

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genePool.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < genePool.size(); i += 2) {
            int firstIndex = indexes.get(i);
            int secondIndex = indexes.get(i + 1);
            ChromosomeRoute child = crossPair(genePool.get(firstIndex), genePool.get(secondIndex));
            if (isGoodChild(child)) {
                genePool.get(firstIndex).copyFrom(child);
            }
        }
    }

    private static boolean isGoodChild(ChromosomeRoute child) {
        return child != null && !child.nullExists();
    }

    private static int getRandom(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private static ChromosomeRoute crossPair(ChromosomeRoute chromosome1, ChromosomeRoute chromosome2) {
        Integer city = getRandom(0, cities.size() - 1);

        ChromosomeRoute child1 = new ChromosomeRoute();
        child1.initRouteRepresentation(cities.size());

        ArrayList<Integer> possibleCities = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            possibleCities.add(i);
        }

        for (int i = 0; i < cities.size(); i++) {
            City from = cities.get(city);
            City to1 = cities.get(chromosome1.getDestFrom(city));
            City to2 = cities.get(chromosome2.getDestFrom(city));

            double edge1Length = ChromosomeRoute.calcLength(from.x, from.y, to1.x, to1.y);
            double edge2Length = ChromosomeRoute.calcLength(from.x, from.y, to2.x, to2.y);

            Integer destination;

            if (edge1Length < edge2Length) {
                destination = chromosome1.getDestFrom(city);
            } else {
                destination = chromosome2.getDestFrom(city);
            }

            if (possibleCities.contains(destination)) {
                if (!child1.setRouteEdge(city, destination)) {
                    System.out.println("already set 1");
                } else {
                    possibleCities.remove(destination);
                }
            } else {
                if (!child1.setRouteEdge(city, possibleCities.get(0))) {
                    //System.out.println("already set 2");
                    Integer freeIndex = child1.getFreeIndex();
                    if (freeIndex > -1) {
                        child1.setRouteEdge(freeIndex, possibleCities.get(0));
                        possibleCities.remove(possibleCities.get(0));
                    }
                } else {
                    possibleCities.remove(possibleCities.get(0));
                }
            }

            city = destination;
        }

        child1.calcRoute();
        return child1;
    }

    private static void tournament(ArrayList<ChromosomeRoute> genePool) {

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genePool.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < genePool.size(); i += 2) {
            int firstIndex = indexes.get(i);
            int secondIndex = indexes.get(i+1);
            battle(genePool.get(firstIndex), genePool.get(secondIndex));
        }
    }

    private static void battle(ChromosomeRoute chromosome1, ChromosomeRoute chromosome2) {
        //System.out.println("battle     " + chromosome1.toString() + " : " +chromosome2.toString());
        if (chromosome1.getRouteLength() < chromosome2.getRouteLength()) {
            chromosome2.copyFrom(chromosome1);
        } else {
            chromosome1.copyFrom(chromosome2);
        }
        //System.out.println("battle res " + chromosome1.toString() + " : " +chromosome2.toString());
    }

    private static void loadData(){
        cities.add(new City(20833.3333, 17100.0000));
        cities.add(new City(20900.0000, 17066.6667));
        cities.add(new City(21300.0000, 13016.6667));
        cities.add(new City(21600.0000, 14150.0000));
        cities.add(new City(21600.0000, 14966.6667));
        cities.add(new City(21600.0000, 16500.0000));
        cities.add(new City(22183.3333, 13133.3333));
        cities.add(new City(22583.3333, 14300.0000));
        cities.add(new City(22683.3333, 12716.6667));
        cities.add(new City(23616.6667, 15866.6667));
        cities.add(new City(23700.0000, 15933.3333));
        cities.add(new City(23883.3333, 14533.3333));
        cities.add(new City(24166.6667, 13250.0000));
        cities.add(new City(25149.1667, 12365.8333));
        cities.add(new City(26133.3333, 14500.0000));
        cities.add(new City(26150.0000, 10550.0000));
        cities.add(new City(26283.3333, 12766.6667));
        cities.add(new City(26433.3333, 13433.3333));
        cities.add(new City(26550.0000, 13850.0000));
        cities.add(new City(26733.3333, 11683.3333));
        cities.add(new City(27026.1111, 13051.9444));
        cities.add(new City(27096.1111, 13415.8333));
        cities.add(new City(27153.6111, 13203.3333));
        cities.add(new City(27166.6667, 9833.3333));
        cities.add(new City(27233.3333, 10450.0000));
        cities.add(new City(27233.3333, 11783.3333));
        cities.add(new City(27266.6667, 10383.3333));
        cities.add(new City(27433.3333, 12400.0000));
        cities.add(new City(27462.5000, 12992.2222));
    }

}
