package com.cjburkey.jgraph.graph;

import com.cjburkey.jgraph.display.Graph;
import java.awt.Color;

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
    public void draw(Graph graph) {
        // Background
        graph.rect(backgroundColor,
                graph.minX, graph.minY,
                graph.maxX - graph.minX, graph.maxY - graph.minY);

        drawAxes(midLineColor, graph, xSpacing / (midYLines + 1), ySpacing / (midYLines + 1));
        drawAxes(mainLineColor, graph, xSpacing, ySpacing);

        // X-Axis
        graph.line(axisLineColor,
                graph.minX, 0.0,
                graph.maxX, 0.0);
        // Y-Axis
        graph.line(axisLineColor,
                0.0, graph.minY,
                0.0, graph.maxY);
    }

    private void drawAxes(Color color, Graph graph, double xSpacing, double ySpacing) {
        for (double x = 0.0d; x <= graph.maxX; x += xSpacing) {
            graph.line(color,
                    x, graph.minY,
                    x, graph.maxY);
        }
        for (double x = 0.0d; x >= graph.minX; x -= xSpacing) {
            graph.line(color,
                    x, graph.minY,
                    x, graph.maxY);
        }
        for (double y = 0.0d; y <= graph.maxY; y += ySpacing) {
            graph.line(color,
                    graph.minX, y,
                    graph.maxX, y);
        }
        for (double y = 0.0d; y >= graph.minY; y -= ySpacing) {
            graph.line(color,
                    graph.minX, y,
                    graph.maxX, y);
        }
    }

}
