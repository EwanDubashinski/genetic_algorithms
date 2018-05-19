import javax.swing.*;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Main {

    private static final int POPULATION_SIZE = 300;

    public static void main(String[] args) {

        ArrayList<Chromosome> genePool;
        ArrayList<Chromosome> newGenePool = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            newGenePool.add(new Chromosome(Chromosome.CHROMOSOME_SIZE));
        }

        //genePool.forEach(System.out::println);

        for (int i = 0; i < 250; i++) {
            genePool = newGenePool;
            newGenePool = reproduction(genePool);
            newGenePool = crossingOver(newGenePool);
            mutation(newGenePool, i);
        }

        drawGraphics(newGenePool);
    }

    private static ArrayList<Chromosome> crossingOver(ArrayList<Chromosome> genePool) {
        ArrayList<Chromosome> newGenePool = new ArrayList<>(genePool);

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < newGenePool.size(); i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < newGenePool.size(); i += 2) {
            int firstIndex = indexes.get(i);
            int secondIndex = indexes.get(i+1);
            crossPair(newGenePool.get(firstIndex), newGenePool.get(secondIndex));
        }
        return newGenePool;
    }

    private static void crossPair(Chromosome chromosomeA, Chromosome chromosomeB) {
        //System.out.println("chromosomeA.getBinaryValue() = " + chromosomeA.getBinaryValue());
        int cutPosition = getRandom(1, Chromosome.CHROMOSOME_SIZE - 2);
        //System.out.println("cutp " + cutPosition);

        String newChromosomeA = chromosomeA.getBinaryValue().substring(0, cutPosition).concat(chromosomeB.getBinaryValue().substring(cutPosition));
        String newChromosomeB = chromosomeB.getBinaryValue().substring(0, cutPosition).concat(chromosomeA.getBinaryValue().substring(cutPosition));

//        System.out.println("newChromosomeA="+newChromosomeA.length());
//        System.out.println("newChromosomeB="+newChromosomeB.length());
        chromosomeA.setBinaryValue(newChromosomeA);
        chromosomeB.setBinaryValue(newChromosomeB);
        chromosomeA.setFuncValue(mainFuncResult(chromosomeA.getRealValue()));
        chromosomeB.setFuncValue(mainFuncResult(chromosomeB.getRealValue()));
    }

    private static int getRandom(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private static ArrayList<Chromosome> reproduction(ArrayList<Chromosome> genePool) {
        for (Chromosome chromosome : genePool) {
            chromosome.setFuncValue(mainFuncResult(chromosome.getRealValue()));
        }

        double populationMin = 0;

        for (Chromosome chromosome : genePool) {
            //populationSum += chromosome.getFuncValue();
            populationMin = chromosome.getFuncValue() < populationMin ? chromosome.getFuncValue() : populationMin;
        }
        double delta = Math.abs(populationMin) + 1;
        double populationSum = 0;
        for (Chromosome chromosome : genePool) {
            chromosome.setPositiveValue(chromosome.getFuncValue() + delta);
            populationSum += chromosome.getPositiveValue();
        }

        for (Chromosome chromosome : genePool) {
            chromosome.setRatio((chromosome.getPositiveValue()/populationSum) * POPULATION_SIZE);
        }

        return roulette(genePool);
    }

    private static double mainFuncResult(double value) {
        return (1.85 - value) * Math.cos(3.5 * value-0.5);
    }

    private static void drawGraphics(ArrayList<Chromosome> genePool) {
        XYSeries series = new XYSeries("(1.85-х)*cos(3.5x-0.5)");

        for(double i = Chromosome.MIN; i <= Chromosome.MAX; i+=0.1){
            series.add(i, mainFuncResult(i));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();

        XYSeries series2 = new XYSeries("population: " + genePool.size());

        for (Chromosome chromosome : genePool) {
            series2.add(chromosome.getRealValue(), chromosome.getFuncValue());
        }

        xyDataset.addSeries(series);
        xyDataset.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart("y = (1.85-х)*cos(3.5x-0.5)", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true)
                ;
        final XYPlot plot1 = chart.getXYPlot();
        XYLineAndShapeRenderer lr = new XYLineAndShapeRenderer();

        lr.setSeriesShapesVisible(0, false);
        lr.setSeriesLinesVisible(0, true);
        lr.setSeriesShapesVisible(1, true);
        lr.setSeriesLinesVisible(1, false);

        plot1.setRenderer(lr);

        JFrame frame =
                new JFrame("Генетический алгоритм - ГУАП 2018 лаб 1");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static ArrayList<Chromosome> roulette(ArrayList<Chromosome> genePool) {
        double randomNumbers[] = new double[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            randomNumbers[i] = i + 0.5;//Math.random() * 10;
//            System.out.println("randomNumber " + randomNumbers[i]);
        }
        ArrayList<Chromosome> newGenePool = new ArrayList<>();

        double offset = 0;
//        double previous = 0;
        int counter = 0;
        int counter2 = 0;
        for (Chromosome chromosome : genePool) {
            //System.out.println("chr " + ++counter2);
            for (int i = 0; i < POPULATION_SIZE; i++) {
                //System.out.println(offset + " " + randomNumbers[i] + " " + ( offset + chromosome.getRatio()));
                if (randomNumbers[i] > (offset == 0 ? -1 : offset) && randomNumbers[i] <= (offset + chromosome.getRatio())) {
                //if (chromosome.getRatio() > offset && chromosome.getRatio() < randomNumbers[i]) {
                    newGenePool.add(new Chromosome(chromosome));
                    //System.out.println("Here! " + ++counter);
                    //offset = randomNumbers[i];
                }
            }

            offset += chromosome.getRatio();
        }
        //System.out.println("newgenepool roulette = " + newGenePool.size());
        return newGenePool;
    }

    private static void mutation (ArrayList<Chromosome> genePool, int iteration) {
        for (Chromosome chromosome : genePool) {
            if (Math.random() > 0.01 * iteration) {
                int k = getRandom(0, Chromosome.CHROMOSOME_SIZE - 1);
                char chromosomeChars[] = chromosome.getBinaryValue().toCharArray();
                String newChromosome = "";
//                System.out.println("before " + chromosome.getBinaryValue());
                for (int i = 0; i < Chromosome.CHROMOSOME_SIZE; i++) {
                    if (i == k) {
                        newChromosome += chromosomeChars[i] == '0' ? '1' : '0';
                    } else {
                        newChromosome += chromosomeChars[i];
                    }
                }
//                System.out.println("after  " + newChromosome);
                chromosome.setBinaryValue(newChromosome);
                chromosome.setFuncValue(mainFuncResult(chromosome.getRealValue()));
            }
        }


    }
}
