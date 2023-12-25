package CurvesPlot.org;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.awt.*;

/**
 * This class is used to display the convergence curve
 */
public class ConvergenceCurvePlot {
    private final XYChart plot;

    /**
     * This instructor is used to construct the plot object which will be used to display the chart
     * @param avgFitnessScores The vector of mean fitness scores as one for an iteration
     */
    public ConvergenceCurvePlot(double[] avgFitnessScores) {
        this.plot = new XYChartBuilder().
                title("Convergence Curve").
                width(400).
                height(400).
                yAxisTitle("AVG fitness").
                xAxisTitle("Iteration").
                theme(Styler.ChartTheme.Matlab).
                build();

        plot.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        plot.getStyler().setMarkerSize(0);
        plot.addSeries("GeneticAlgorithm", avgFitnessScores).setLineColor(Color.BLACK);
    }

    /**
     * This method is used to display the plot
     */
    public void ShowPlot() {
        new SwingWrapper<>(plot).displayChart();
    }
}
