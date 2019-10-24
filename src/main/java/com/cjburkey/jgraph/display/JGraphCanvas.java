package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.graph.GraphComponent;
import com.cjburkey.jgraph.graph.GraphComponentGrid;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class JGraphCanvas extends JPanel {

    public final GraphView graph = new GraphView();
    private ArrayList<GraphComponent> components = new ArrayList<>();

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
            }

            @Override
            public void mouseDragged(MouseEvent e) {
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
