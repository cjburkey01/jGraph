package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.display.graphics.AwtGraphics2D;
import com.cjburkey.jgraph.graph.GraphComponent;
import com.cjburkey.jgraph.graph.GraphComponentGrid;
import com.cjburkey.jgraph.prop.Property;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("WeakerAccess")
public class JGraphCanvas extends JPanel {

    private final AwtGraphics2D graphics2D = new AwtGraphics2D(null);
    public final GraphView graph = new GraphView(graphics2D, this::repaint);
    private final ArrayList<GraphComponent> components = new ArrayList<>();

    public final Property<Integer> mouseX = new Property<>();
    public final Property<Integer> mouseY = new Property<>();
    public final Property<Integer> lastMouseX = new Property<>();
    public final Property<Integer> lastMouseY = new Property<>();

    public JGraphCanvas() {
        addListeners();

        // All graphs have a grid component
        components.add(new GraphComponentGrid());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics2D.updateGraphics((Graphics2D) g);
        graph.clear();

        for (GraphComponent component : components) {
            if (component.shown) {
                component.draw(graph);
            }
        }
    }

    private void addListeners() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                // Update the graph size
                // The `getBounds()` method is updated before `getWidth()` and
                // `getHeight()` which update after this event is fired.
                final Rectangle BOUNDS = getBounds();
                graph.updateSize((int) BOUNDS.getWidth(), (int) BOUNDS.getHeight());

                // Update the graph zoom to keep the aspect ratio
                // Change the Y min/max but keep the X min/max the same
                double invAspect = 1.0d / graph.getAspect();
                graph.minY.set(invAspect * graph.minX.get());
                graph.maxY.set(invAspect * graph.maxX.get());
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {
            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {
            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updatePos(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updatePos(e.getX(), e.getY());

                // If the mouse doesn't have the required position data, skip.
                if (!mouseX.has()
                        || !mouseY.has()
                        || !lastMouseX.has()
                        || !lastMouseY.has()) return;

                // Calculate the transformed delta position
                double xDiff = graph.invTransformW(mouseX.get() - lastMouseX.get());
                double yDiff = graph.invTransformH(mouseY.get() - lastMouseY.get());

                // Update the graph bounds
                graph.minX.map(minX -> minX - xDiff);
                graph.maxX.map(maxX -> maxX - xDiff);
                graph.minY.map(minY -> minY - yDiff);
                graph.maxY.map(maxY -> maxY - yDiff);
            }

            private void updatePos(int x, int y) {
                // Update mouse position cache
                lastMouseX.set(mouseX.get());
                lastMouseY.set(mouseY.get());
                mouseX.set(x);
                mouseY.set(y);
            }
        });
        addMouseWheelListener(e -> {
            double scrollAmountHalf = e.getWheelRotation() / 2.0d;
            double aspect = 1.0d / graph.getAspect();

            graph.minX.map(minX -> minX - scrollAmountHalf);
            graph.maxX.map(maxX -> maxX + scrollAmountHalf);
            graph.minY.map(minY -> minY - aspect * scrollAmountHalf);
            graph.maxY.map(maxY -> maxY + aspect * scrollAmountHalf);
        });
    }

}
