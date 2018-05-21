package ru.chufeng.guap.ga.tsp;

import java.util.ArrayList;
import java.util.Collections;

public class TspGA {
    private static final int POPULATION_COUNT = 40;
    private static final int ITERATIONS_COUNT = 10;

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
            //genePool.forEach(System.out::println);
            // Tournament selection
            tournament(genePool);
        }
        //System.out.println("Finish!");
        //genePool.forEach(System.out::println);

        // Heuristic crossover
        // Impl mutation as switch two random points...
        // Graphic representation

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
//        cities.add(new City(20833.3333, 17100.0000));
//        cities.add(new City(20900.0000, 17066.6667));
//        cities.add(new City(21300.0000, 13016.6667));
//        cities.add(new City(21600.0000, 14150.0000));
//        cities.add(new City(21600.0000, 14966.6667));
//        cities.add(new City(21600.0000, 16500.0000));
//        cities.add(new City(22183.3333, 13133.3333));
//        cities.add(new City(22583.3333, 14300.0000));
//        cities.add(new City(22683.3333, 12716.6667));
//        cities.add(new City(23616.6667, 15866.6667));
//        cities.add(new City(23700.0000, 15933.3333));
//        cities.add(new City(23883.3333, 14533.3333));
//        cities.add(new City(24166.6667, 13250.0000));
//        cities.add(new City(25149.1667, 12365.8333));
//        cities.add(new City(26133.3333, 14500.0000));
//        cities.add(new City(26150.0000, 10550.0000));
//        cities.add(new City(26283.3333, 12766.6667));
//        cities.add(new City(26433.3333, 13433.3333));
//        cities.add(new City(26550.0000, 13850.0000));
//        cities.add(new City(26733.3333, 11683.3333));
//        cities.add(new City(27026.1111, 13051.9444));
//        cities.add(new City(27096.1111, 13415.8333));
//        cities.add(new City(27153.6111, 13203.3333));
//        cities.add(new City(27166.6667, 9833.3333));
//        cities.add(new City(27233.3333, 10450.0000));
//        cities.add(new City(27233.3333, 11783.3333));
//        cities.add(new City(27266.6667, 10383.3333));
//        cities.add(new City(27433.3333, 12400.0000));
//        cities.add(new City(27462.5000, 12992.2222));
        cities.add(new City(0, 0));
        cities.add(new City(0, 1));
        cities.add(new City(0, 2));
    }

}
