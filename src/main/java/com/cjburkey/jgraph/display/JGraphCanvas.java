package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.graph.GraphComponent;
import com.cjburkey.jgraph.graph.GraphComponentGrid;
import com.cjburkey.jgraph.prop.Property;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("WeakerAccess")
public class JGraphCanvas extends JPanel {

    public final GraphView graph = new GraphView(this::repaint);
    private ArrayList<GraphComponent> components = new ArrayList<>();

    public final Property<Integer> mouseX = new Property<>();
    public final Property<Integer> mouseY = new Property<>();
    public final Property<Integer> lastMouseX = new Property<>();
    public final Property<Integer> lastMouseY = new Property<>();

    public JGraphCanvas() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                // Update the graph size to keep the aspect ratio
                // Change the Y min/max but keep the X min/max the same
                final Rectangle BOUNDS = getBounds();
                graph.minY.set(BOUNDS.getHeight() / BOUNDS.getWidth() * graph.minX.get());
                graph.maxY.set(BOUNDS.getHeight() / BOUNDS.getWidth() * graph.maxX.get());
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

                if (!mouseX.has()
                        || !mouseY.has()
                        || !lastMouseX.has()
                        || !lastMouseY.has()) return;

                double xDiff = graph.invTransformW(mouseX.get() - lastMouseX.get());
                double yDiff = graph.invTransformH(mouseY.get() - lastMouseY.get());

                graph.minX.map(at -> at - xDiff);
                graph.maxX.map(at -> at - xDiff);
                graph.minY.map(at -> at - yDiff);
                graph.maxY.map(at -> at - yDiff);
            }

            private void updatePos(int x, int y) {
                lastMouseX.set(mouseX.get());
                lastMouseY.set(mouseY.get());
                mouseX.set(x);
                mouseY.set(y);
            }
        });
        components.add(new GraphComponentGrid());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graph.updateGraphics(g, getWidth(), getHeight());
        graph.clear();

        for (GraphComponent component : components) {
            if (component.shown) {
                component.draw(graph);
            }
        }
    }

}
