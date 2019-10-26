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

        // This is basically a lot of work to make sure there isn't any drawing outside of the main graph.
        // This could be useful if the graph is actually just a fraction of a canvas so
        if (axis.get().equals(Axis.X)) {
            // Vertical lines are easy :)
            x1 = intercept.get();
            y1 = graph.maxY.get();
            x2 = intercept.get();
            y2 = graph.minY.get();
        } else {
            // Calculate the relative intersection points.
            double intersectXMinY = slope.get() * graph.minX.get() + intercept.get();
            double intersectXMaxY = slope.get() * graph.maxX.get() + intercept.get();
            double intersectYMinX = (graph.minY.get() - intercept.get()) / slope.get();
            double intersectYMaxX = (graph.maxY.get() - intercept.get()) / slope.get();

            // First intersection point
            if (intersectXMinY > graph.maxY.get()) {
                // If the intersection with the maximum x bound of the graph
                // is above the maximum y bound of the graph, the
                // intersection is above the screen so the actual intersection
                // point is with the maximum y bound of the graph.
                x1 = intersectYMaxX;
                y1 = graph.maxY.get();
            } else if (intersectXMinY < graph.minY.get()) {
                // Same as above except this is when the intersection with the
                // maximum x bound is below the screen.
                x1 = intersectYMinX;
                y1 = graph.minY.get();
            } else {
                // The intersection point is actually on the maximum x bound
                // of the graph.
                x1 = graph.minX.get();
                y1 = intersectXMinY;
            }

            // Second intersection point
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
