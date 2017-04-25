package bsu.diplom.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class GraphsWorker {
    final XYSeriesCollection dataset = new XYSeriesCollection();

    public void addToDataset(Double[] arr1) {
        final XYSeries signal = new XYSeries( "Simple Signal" );

        int length1 = arr1.length / 2;

        for(int i = 0; i < length1; i++) {
            signal.add(i,arr1[i]);
        }

        dataset.addSeries(signal);
    }

    public void addToDataset(Double[] arr1, Double[] arr2) {
        final XYSeries signal1 = new XYSeries( "Simple Signal" );
        final XYSeries signal2 = new XYSeries( "Out Signal" );

        for(int i = 0; i < 500; i++) {
            signal1.add(i,arr1[i]);
        }

        for(int i = 0; i < 500; i++) {
            signal2.add(i,arr2[i]);
        }

        dataset.addSeries(signal1);
        dataset.addSeries(signal2);
    }

    public void plotSignal(String effectName) {
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                effectName,
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        JFrame frame = new JFrame("Test");
        frame.setContentPane(new ChartPanel(chart1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
