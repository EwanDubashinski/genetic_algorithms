package ru.chufeng.guap.ga.tsp;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

public class Draw extends JPanel
{

    private ChromosomeRoute route;

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        ArrayList<Integer> routeCities = route.getRoute();
        for (int i = 1; i < TspGA.cities.size(); i++) {
            City city1 = TspGA.cities.get(routeCities.get(i - 1));
            City city2 = TspGA.cities.get(routeCities.get(i));
            Shape l = new Line2D.Double(city1.x/50, city1.y/50, city2.x/50, city2.y/50);
            g2.draw(l);
            g2.drawString("Length = " + route.getRouteLength(), 50, 50);
        }
        City city1 = TspGA.cities.get(routeCities.get(0));
        City city2 = TspGA.cities.get(routeCities.get(TspGA.cities.size() - 1));
        //g2.drawLine(city1.x, 40, 80, 100);
        Shape l = new Line2D.Double(city1.x/50, city1.y/50, city2.x/50, city2.y/50);
        g2.draw(l);
    }

    Draw(ChromosomeRoute route) {
        this.route = route;

        JFrame frame = new JFrame();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(this);

        frame.setVisible(true);
        frame.invalidate();
    }

}