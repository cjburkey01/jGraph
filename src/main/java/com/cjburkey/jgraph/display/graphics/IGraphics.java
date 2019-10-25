package com.cjburkey.jgraph.display.graphics;

public interface IGraphics {

    void setColor(Color color);

    void fillRect(int xMin, int yMin, int width, int height);

    void clearRect(int xMin, int yMin, int width, int height);

    void drawLine(int x1, int y1, int x2, int y2);

}
