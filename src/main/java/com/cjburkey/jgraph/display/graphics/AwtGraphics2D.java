package com.cjburkey.jgraph.display.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class AwtGraphics2D implements IGraphics {

    private Graphics2D graphics2D;

    public AwtGraphics2D(Graphics2D graphics2D) {
        updateGraphics(graphics2D);
    }

    public void updateGraphics(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public void setColor(com.cjburkey.jgraph.display.graphics.Color color) {
        if (graphics2D != null) graphics2D.setColor(new Color(color.r(), color.g(), color.b(), 0xFF));
    }

    @Override
    public void fillRect(int xMin, int yMin, int width, int height) {
        if (graphics2D != null) graphics2D.fillRect(xMin, yMin, width, height);
    }

    @Override
    public void clearRect(int xMin, int yMin, int width, int height) {
        if (graphics2D != null) graphics2D.clearRect(xMin, yMin, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        if (graphics2D != null) graphics2D.drawLine(x1, y1, x2, y2);
    }

}
