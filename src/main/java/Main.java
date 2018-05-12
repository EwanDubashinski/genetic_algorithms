import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    private static final double MIN = -10.0;
    private static final double MAX = 10.0;

    public static void main(String[] args) {
        drawGraphics();
    }

    private static void drawGraphics() {
        XYSeries series = new XYSeries("(1.85-х)*cos(3.5x-0.5)");

        for(double i = MIN; i <= MAX; i+=0.1){
            series.add(i, (1.85-i)* Math.cos(3.5*i-0.5));
        }


        XYSeriesCollection xyDataset = new XYSeriesCollection();


        XYSeries series2 = new XYSeries("population");

        series2.add(1,1);

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
