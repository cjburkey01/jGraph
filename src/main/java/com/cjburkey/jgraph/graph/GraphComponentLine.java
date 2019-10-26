package com.cjburkey.jgraph.graph;

import com.cjburkey.jgraph.display.GraphView;
import com.cjburkey.jgraph.display.graphics.Color;
import com.cjburkey.jgraph.prop.Property;

public class GraphComponentLine extends GraphComponent {

    public final Property<Color> lineColor = new Property<>(new Color(0xFF0000));
    public final Property<Axis> axis = new Property<>(Axis.Y);
    public final Property<Double> slope = new Property<>(0.0d);
    public final Property<Double> intercept = new Property<>(0.0d);

    /**
     * Create a vertical line.
     *
     * @param x The x-value of the vertical line.
     */
    public GraphComponentLine(double x) {
        this.axis.set(Axis.X);
        this.intercept.set(x);
    }

    /**
     * Create a line from an equation of the form {@code y=(slope)x+intercept}.
     *
     * @param slope     The slope of the line (rise/run)
     * @param intercept The
     */
    public GraphComponentLine(double slope, double intercept) {
        this.slope.set(slope);
        this.intercept.set(intercept);
    }

    @Override
    public void draw(GraphView graph) {
        final double x1, x2;
        final double y1, y2;

        if (axis.get().equals(Axis.X)) {
            x1 = intercept.get();
            y1 = graph.maxY.get();
            x2 = intercept.get();
            y2 = graph.minY.get();
        } else {
            double intersectXMinY = slope.get() * graph.minX.get() + intercept.get();
            double intersectXMaxY = slope.get() * graph.maxX.get() + intercept.get();
            double intersectYMinX = (graph.minY.get() - intercept.get()) / slope.get();
            double intersectYMaxX = (graph.maxY.get() - intercept.get()) / slope.get();
            if (intersectXMinY > graph.maxY.get()) {
                x1 = intersectYMaxX;
                y1 = graph.maxY.get();
            } else if (intersectXMinY < graph.minY.get()) {
                x1 = intersectYMinX;
                y1 = graph.minY.get();
            } else {
                x1 = graph.minX.get();
                y1 = intersectXMinY;
            }
            if (intersectXMaxY > graph.maxY.get()) {
                x2 = intersectYMaxX;
                y2 = graph.maxY.get();
            } else if (intersectXMaxY < graph.minY.get()) {
                x2 = intersectYMinX;
                y2 = graph.minY.get();
            } else {
                x2 = graph.maxX.get();
                y2 = intersectXMaxY;
            }
        }

        graph.line(lineColor.get(), x1, y1, x2, y2);
    }

    public enum Axis {
        X,
        Y,
    }

}
