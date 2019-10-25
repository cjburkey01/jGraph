package com.cjburkey.jgraph.graph;

import com.cjburkey.jgraph.display.GraphView;
import com.cjburkey.jgraph.display.graphics.Color;

@SuppressWarnings("WeakerAccess")
public class GraphComponentGrid extends GraphComponent {

    public double xSpacing = 1.0d;
    public double ySpacing = 1.0d;
    public int midXLines = 1;
    public int midYLines = 1;

    public Color backgroundColor = Color.WHITE;
    public Color axisLineColor = Color.BLACK;
    public Color mainLineColor = new Color(0xCCCCCC);
    public Color midLineColor = new Color(0xEEEEEE);

    @Override
    public void draw(GraphView graph) {
        // Background
        graph.rect(backgroundColor,
                graph.minX.get(), graph.minY.get(),
                graph.maxX.get() - graph.minX.get(), graph.maxY.get() - graph.minY.get());

        // Inter-line lines
        drawAxes(midLineColor, graph, xSpacing / (midXLines + 1), ySpacing / (midYLines + 1));

        // Lines
        drawAxes(mainLineColor, graph, xSpacing, ySpacing);

        // X-Axis
        graph.line(axisLineColor,
                graph.minX.get(), 0.0,
                graph.maxX.get(), 0.0);
        // Y-Axis
        graph.line(axisLineColor,
                0.0, graph.minY.get(),
                0.0, graph.maxY.get());
    }

    private void drawAxes(Color color, GraphView graph, double xSpacing, double ySpacing) {
        // X lines
        for (double y = 0.0d; y <= graph.maxY.get(); y += ySpacing) {
            graph.line(color,
                    graph.minX.get(), y,
                    graph.maxX.get(), y);
        }
        for (double y = -ySpacing; y >= graph.minY.get(); y -= ySpacing) {
            graph.line(color,
                    graph.minX.get(), y,
                    graph.maxX.get(), y);
        }

        // Y lines
        for (double x = 0.0d; x <= graph.maxX.get(); x += xSpacing) {
            graph.line(color,
                    x, graph.minY.get(),
                    x, graph.maxY.get());
        }
        for (double x = -xSpacing; x >= graph.minX.get(); x -= xSpacing) {
            graph.line(color,
                    x, graph.minY.get(),
                    x, graph.maxY.get());
        }
    }

}
