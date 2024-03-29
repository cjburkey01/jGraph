package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.JGraph;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class JGraphWindow extends JFrame {

    private final JGraphCanvas drawPanel;

    public JGraphWindow(JGraph jGraph) {
        drawPanel = new JGraphCanvas();

        setTitle("jGraph 0.1.0");
        setContentPane(drawPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        final Dimension MONITOR_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (MONITOR_SIZE.getWidth() * 2 / 3), (int) (MONITOR_SIZE.getHeight() * 2 / 3));
        setLocationRelativeTo(null);

        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                jGraph.exit();
            }

            @Override
            public void windowOpened(WindowEvent windowEvent) {
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {
            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {
            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {
            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) drawPanel.requestFocus();

        super.setVisible(visible);
    }

    public void exit() {
        setVisible(false);
        dispose();
    }

}
