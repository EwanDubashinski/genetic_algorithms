import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

public class Main {
    private static final int POPULATION_SIZE = 10;

    public static void main(String[] args) {



        ArrayList<Chromosome> genePool = new ArrayList<>();
        ArrayList<Chromosome> newGenePool = null;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            genePool.add(new Chromosome(Chromosome.CHROMOSOME_SIZE));
        }

        //genePool.forEach(System.out::println);
        for (int i = 0; i < 100; i++) {
            newGenePool = reproduction(genePool);
        }

        drawGraphics(newGenePool);
    }

    private static ArrayList<Chromosome> reproduction(ArrayList<Chromosome> genePool) {
        for (Chromosome chromosome : genePool) {
            chromosome.setFuncValue(mainFuncResult(chromosome.getRealValue()));
        }

        double populationMin = 0;

        for (Chromosome chromosome : genePool) {
            //populationMiddle += chromosome.getFuncValue();
            populationMin = chromosome.getFuncValue() < populationMin ? chromosome.getFuncValue() : populationMin;
        }
        System.out.println("min=" + populationMin);
        double delta = Math.abs(populationMin) + 1;
        double populationMiddle = 0;
        for (Chromosome chromosome : genePool) {
            chromosome.setPositiveValue(chromosome.getFuncValue() + delta);
            System.out.println("chromosome.getPositiveValue()=" + chromosome.getPositiveValue());
            populationMiddle += chromosome.getPositiveValue();
        }
        populationMiddle /= POPULATION_SIZE;

        for (Chromosome chromosome : genePool) {
            chromosome.setRatio(chromosome.getPositiveValue()/populationMiddle);
        }

        ArrayList<Chromosome> newGenePool = new ArrayList<>();

        for (Chromosome chromosome : genePool) {
            long chromosomeCount = Math.round(chromosome.getRatio());
            System.out.println("chromosomeCount=" + chromosomeCount + " chromosome.getResult()"+chromosome.getRatio());
            for (int i = 0; i < chromosomeCount; i++) {
                newGenePool.add(new Chromosome(chromosome));
            }
        }
        return newGenePool;
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
}
