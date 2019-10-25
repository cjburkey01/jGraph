package com.cjburkey.jgraph.display;

import com.cjburkey.jgraph.display.graphics.Color;
import com.cjburkey.jgraph.display.graphics.IGraphics;
import com.cjburkey.jgraph.prop.Property;

@SuppressWarnings("WeakerAccess")
public class GraphView {

    // Graph view
    public final Property<Double> minX = new Property<>(0.0d);
    public final Property<Double> minY = new Property<>(0.0d);
    public final Property<Double> maxX = new Property<>(0.0d);
    public final Property<Double> maxY = new Property<>(0.0d);

    // Transforming values
    private final Property<Double> zoomX = new Property<>(0.0d);
    private final Property<Double> zoomY = new Property<>(0.0d);
    private final Property<Double> translateX = new Property<>(0.0d);
    private final Property<Double> translateY = new Property<>(0.0d);

    // Graphics handling
    private IGraphics g;
    private final Property<Integer> width = new Property<>(0);
    private final Property<Integer> height = new Property<>(0);
    private final Property<Double> aspect = new Property<>(0.0d);

    public GraphView(IGraphics graphics, Runnable repaint) {
        // When the graph's bounds change, update all the transforms
        minX.listen((o, n) -> updateTransforms());
        minY.listen((o, n) -> updateTransforms());
        maxX.listen((o, n) -> updateTransforms());
        maxY.listen((o, n) -> updateTransforms());
        width.listen((o, n) -> updateTransforms());
        height.listen((o, n) -> updateTransforms());

        // Update the aspect ratio
        width.listen((o, width) -> aspect.set((double) width / height.get()));
        height.listen((o, height) -> aspect.set((double) width.get() / height));

        // When any transforms change, redraw the graph if a redraw function
        // is provided.
        if (repaint != null) {
            Property.listenAll((o, n) -> repaint.run(), zoomX, zoomY, translateX, translateY);
        }

        // Default values that will propogate through all the listeners that
        // were just created.
        minX.set(-5.0d);
        maxX.set(5.0d);
        minY.set(-5.0d);
        maxY.set(5.0d);

        g = graphics;
    }

    private void updateTransforms() {
        zoomX.set(width.get() / (maxX.get() - minX.get()));
        zoomY.set(height.get() / (maxY.get() - minY.get()));
        translateX.set((maxX.get() + minX.get()) / 2.0d);
        translateY.set((maxY.get() + minY.get()) / 2.0d);
    }

    public void updateSize(int width, int height) {
        this.width.set(width);
        this.height.set(height);
    }

    public double getAspect() {
        return aspect.get();
    }

    public void clear() {
        if (g != null) g.clearRect(0, 0, width.get(), height.get());
    }

    public void rect(Color color, double x, double y, double w, double h) {
        if (g == null) return;

        g.setColor(color);
        g.fillRect(transformX(x),
                transformY(y),
                transformW(w),
                transformH(h));
    }

    public void line(Color color, double x1, double y1, double x2, double y2) {
        if (g == null) return;

        g.setColor(color);
        g.drawLine(transformX(x1),
                transformY(y1),
                transformX(x2),
                transformY(y2));
    }

    private static int transform(double zoom, double loc, double localTranslation, int size) {
        return ((int) (zoom * (loc - localTranslation))) + size / 2;
    }

    private static double invTransform(double zoom, double loc, double localTranslation, double size) {
        return (loc - size / 2) / zoom + localTranslation;
    }

    public int transformX(double x) {
        return transform(zoomX.get(), x, translateX.get(), width.get());
    }

    public int transformY(double y) {
        return transform(zoomY.get(), y, translateY.get(), height.get());
    }

    public int transformW(double w) {
        return transform(zoomX.get(), w, 0, 0);
    }

    public int transformH(double h) {
        return transform(zoomY.get(), h, 0, 0);
    }

    @SuppressWarnings("unused")
    public double invTransformX(double x) {
        return invTransform(zoomX.get(), x, translateX.get(), width.get());
    }

    @SuppressWarnings("unused")
    public double invTransformY(double y) {
        return invTransform(zoomY.get(), y, translateY.get(), height.get());
    }

    public double invTransformW(double w) {
        return invTransform(zoomX.get(), w, 0, 0);
    }

    public double invTransformH(double h) {
        return invTransform(zoomY.get(), h, 0, 0);
    }

}
