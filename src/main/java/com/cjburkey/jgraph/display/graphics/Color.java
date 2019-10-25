package com.cjburkey.jgraph.display.graphics;

import java.util.Objects;

public class Color {

    public static final Color WHITE = new Color(0xFFFFFF);
    public static final Color BLACK = new Color(0x000000);

    public final int rgb;

    // Takes 0xRRGGBB
    public Color(int hex) {
        rgb = hex & 0xFFFFFF;
    }

    public Color(int r, int g, int b) {
        this(((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF));
    }

    public int r() {
        return rgb >> 16;
    }

    public int g() {
        return (rgb >> 8) & 0xFF;
    }

    public int b() {
        return rgb & 0xFF;
    }

    @Override
    public String toString() {
        return String.format("Color RGB: (%s, %s, %s)", r(), g(), b());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return rgb == color.rgb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rgb);
    }

}
