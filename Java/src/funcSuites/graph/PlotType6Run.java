package funcSuites.graph;

import org.jfree.ui.RefineryUtilities;

public class PlotType6Run {
    public static void main(final String[] args) {

        final PlotType6 demo = new PlotType6("CombinedDomainXYPlot Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
    }
}