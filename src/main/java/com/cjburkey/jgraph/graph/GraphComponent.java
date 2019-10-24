package com.cjburkey.jgraph.graph;

import com.cjburkey.jgraph.display.Graph;

public abstract class GraphComponent {

    public boolean shown = true;

    public abstract void draw(Graph graph);

}
