package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.graph.GraphComponent;
import com.cjburkey.jgraph.graph.GraphComponentGrid;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javax.swing.JPanel;

public class JGraphCanvas extends JPanel {

    public final Graph graph = new Graph();
    private ArrayList<GraphComponent> components = new ArrayList<>();
    private boolean initialized = false;

    public JGraphCanvas() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                final int START_CELL_SIZE = 100;
                if (!initialized) {
                    initialized = true;

                    final int s = 3;

                    Rectangle b = ((Component) componentEvent.getSource()).getBounds();
                    graph.minX = -s;
                    graph.maxX = s;
                    graph.minY = b.getHeight() / b.getWidth() * -s;
                    graph.maxY = b.getHeight() / b.getWidth() * s;
                }
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
