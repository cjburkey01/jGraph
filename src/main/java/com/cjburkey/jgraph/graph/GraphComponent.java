package com.cjburkey.jgraph.graph;

import com.cjburkey.jgraph.display.GraphView;

public abstract class GraphComponent {

    public boolean shown = true;

    public abstract void draw(GraphView graph);

}
