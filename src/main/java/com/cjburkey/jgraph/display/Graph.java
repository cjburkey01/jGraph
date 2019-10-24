package com.cjburkey.jgraph.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("WeakerAccess")
public class Graph {

    public double minX = -5.0d;
    public double minY = -5.0d;
    public double maxX = 5.0d;
    public double maxY = 5.0d;

    private double zoomX = 0.0d;
    private double zoomY = 0.0d;
    private double translateX = 0.0d;
    private double translateY = 0.0d;

    private Graphics2D g;
    private int width;
    private int height;

    public void updateGraphics(Graphics g, int width, int height) {
        this.g = (Graphics2D) g;
        this.width = width;
        this.height = height;
    }

    public void clear() {
        if (g != null) g.clearRect(0, 0, width, height);
    }

    public void rect(Color color, double x, double y, double w, double h) {
        updateTransform();

        g.setColor(color);
        g.fillRect(ddati(zoomX, x, translateX, width),
                ddati(zoomY, y, translateY, height),
                ddati(zoomX, w, 0, 0),
                ddati(zoomY, h, 0, 0));
    }

    public void line(Color color, double x1, double y1, double x2, double y2) {
        updateTransform();

        g.setColor(color);
        g.drawLine(ddati(zoomX, x1, translateX, width),
                ddati(zoomY, y1, translateY, height),
                ddati(zoomX, x2, translateX, width),
                ddati(zoomY, y2, translateY, height));
    }

    private void updateTransform() {
        zoomX = width / (maxX - minX);
        zoomY = height / (maxY - minY);
        translateX = (maxX + minX) / 2.0d;
        translateY = (maxY + minY) / 2.0d;
    }

    private int ddati(double a, double b, double xy, int wh) {
        return ((int) (a * (b - xy))) + wh / 2;
    }

}
