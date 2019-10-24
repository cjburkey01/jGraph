package com.cjburkey.jgraph;

import com.cjburkey.jgraph.display.JGraphWindow;

@SuppressWarnings("WeakerAccess")
public class JGraph {

    private final JGraphWindow window;

    public JGraph() {
        window = new JGraphWindow(this);
    }

    public void start() {
        window.setVisible(true);
    }

    public void exit() {
        window.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        new JGraph().start();
    }

}
