package de.interoberlin.sauvignon.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import de.interoberlin.sauvignon.lib.controller.renderer.SvgRenderer;
import de.interoberlin.sauvignon.lib.model.smil.AAnimate;
import de.interoberlin.sauvignon.lib.model.smil.AnimateColor;
import de.interoberlin.sauvignon.lib.model.smil.AnimateTransform;
import de.interoberlin.sauvignon.lib.model.svg.EScaleMode;
import de.interoberlin.sauvignon.lib.model.svg.SVG;
import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SVGImagePanel extends ImageView {
    private Thread renderingThread = null;
    private Thread animationThread = null;
    private float fpsDesired = 30;
    private float fpsCurrent;
    private Vector2 touch;
    private static boolean running = false;

    private boolean raster;
    private boolean boundingRectsParallelToAxes;
    private boolean boundingRectsNotParallelToAxes;

    private long millisStart;
    private SVG svg;
    private int width;
    private int height;

    // -------------------------
    // Constructors
    // -------------------------

    public SVGImagePanel(Context context, SVG svg, int width, int height) {
        super(context);
        this.svg = svg;
        this.width = width;
        this.height = height;

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        // Clear canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Render SVG
        SvgRenderer.renderToCanvas(canvas, svg);

        setImageBitmap(bmp);
    }

    // -------------------------
    // Methods
    // -------------------------

    public void onChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void resume() {
        running = true;
        renderingThread = new Thread(new Runnable() {
            public void run() {
                if (svg != null) {
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bmp);

                    int canvasWidth = canvas.getWidth();
                    int canvasHeight = canvas.getHeight();

                    // Set scale mode
                    synchronized (svg) {
                        svg.setCanvasScaleMode(EScaleMode.FIT);
                        svg.scaleTo(canvasWidth, canvasHeight);
                    }

                    while (running) {
                        // P E R F O R M

                        // Clear canvas
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        /**
                         * Actual drawing
                         */

                        // Render raster
                        if (raster)
                            canvas = SvgRenderer.renderRasterToCanvas(canvas, svg);

                        // Render SVG
                        synchronized (svg) {
                            canvas = SvgRenderer.renderToCanvas(canvas, svg);
                        }

                        // Render bounding rects
                        if (boundingRectsParallelToAxes)
                            canvas = SvgRenderer.renderBoundingRectsToCanvas(canvas, svg, true);
                        if (boundingRectsNotParallelToAxes)
                            canvas = SvgRenderer.renderBoundingRectsToCanvas(canvas, svg, false);

                    }
                }
            }
        });

        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                millisStart = System.currentTimeMillis();

                long millisBefore = 0;
                long millisAfter = 0;
                long millisFrame = (long) (1000 / fpsDesired);

                // Wait for svg
                if (svg == null) {
                    while (running) {
                        try {
                            Thread.sleep(millisFrame);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (svg != null) {
                    while (running) {
                        // P E R F O R M

                        if (millisAfter - millisBefore != 0) {
                            fpsCurrent = ((float) (1000 / (millisAfter - millisBefore)));
                        }

                        // A N I M A T I O N

                        millisBefore = System.currentTimeMillis();

                        synchronized (svg) {
                            for (AGeometric g : svg.getAllSubElements()) {
                                for (AAnimate a : g.getAnimations()) {
                                    if (a instanceof AnimateTransform) {
                                        AnimateTransform at = (AnimateTransform) a;

                                        g.setAnimationTransform(at.getTransformOperator(millisBefore - millisStart));
                                    } else if (a instanceof AnimateColor) {
                                        AnimateColor ac = (AnimateColor) a;

                                        long millisSinceStart = millisBefore - millisStart;

                                        g.setAnimationColor(ac.getColorOperator(millisSinceStart));
                                    }
                                }
                            }

                            millisAfter = System.currentTimeMillis();

                        } // synchronized (svg)

                        // P A U S E

                        if (millisAfter - millisBefore < millisFrame) {
                            try {
                                Thread.sleep(millisFrame - (millisAfter - millisBefore));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        millisAfter = System.currentTimeMillis();
                    }
                }
            }
        });

        animationThread.start();
        renderingThread.start();
    }

    public void pause() {
        boolean retry = true;
        running = false;

        while (retry) {
            try {
                animationThread.join();
                renderingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2 getTouch() {
        return touch;
    }

    public void setTouch(Vector2 v) {
        touch = new Vector2(v);
    }

    // -------------------------
    // Getters / Setter
    // -------------------------

    public void setSVG(SVG svg) {
        this.svg = svg;
    }

    public void setFpsDesired(float fpsDesired) {
        this.fpsDesired = fpsDesired;
    }

    public float getFpsDesired() {
        return fpsDesired;
    }

    public float getFpsCurrent() {
        return fpsCurrent;
    }

    public boolean isRaster() {
        return raster;
    }

    public void setRaster(boolean raster) {
        this.raster = raster;
    }

    public boolean isBoundingRectsParallelToAxes() {
        return boundingRectsParallelToAxes;
    }

    public void setBoundingRectsParallelToAxes(boolean boundingRectsParallelToAxes) {
        this.boundingRectsParallelToAxes = boundingRectsParallelToAxes;
    }

    public boolean isBoundingRectsNotParallelToAxes() {
        return boundingRectsNotParallelToAxes;
    }

    public void setBoundingRectsNotParallelToAxes(boolean boundingRectsNotParallelToAxes) {
        this.boundingRectsNotParallelToAxes = boundingRectsNotParallelToAxes;
    }
}
