package util;

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
        int length1 = arr1.length;
        for(int i = 0; i < length1; i++) {
            signal.add(i,arr1[i]);
        }
        dataset.addSeries(signal);
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
