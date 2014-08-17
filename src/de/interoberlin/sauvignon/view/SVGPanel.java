package de.interoberlin.sauvignon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import de.interoberlin.sauvignon.controller.renderer.SvgRenderer;
import de.interoberlin.sauvignon.model.smil.AAnimate;
import de.interoberlin.sauvignon.model.smil.AnimateColor;
import de.interoberlin.sauvignon.model.smil.AnimateTransform;
import de.interoberlin.sauvignon.model.svg.EScaleMode;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGPanel extends SurfaceView
{
	private Thread			renderingThread	= null;
	private Thread			animationThread	= null;
	SurfaceHolder			surfaceHolder;
	private float			fpsDesired		= 30;
	private float			fpsCurrent;
	private Vector2			touch;
	private static boolean	running			= false;

	private boolean			boundingRects;
	private boolean			raster;

	private long			millisStart;
	private SVG				svg;

	// -------------------------
	// Constructors
	// -------------------------

	public SVGPanel(Context context)
	{
		super(context);
		surfaceHolder = getHolder();
	}

	// -------------------------
	// Methods
	// -------------------------

	public void onChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
	}

	public void resume()
	{
		running = true;
		renderingThread = new Thread(new Runnable()
		{
			public void run()
			{
				while (!surfaceHolder.getSurface().isValid())
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				}

				if (svg != null)
				{
					while (running)
					{
						synchronized (svg)
						{
							// P E R F O R M

							// Set dimensions to fullscreen
							Canvas c = surfaceHolder.lockCanvas();

							int canvasWidth = c.getWidth();
							int canvasHeight = c.getHeight();

							surfaceHolder.unlockCanvasAndPost(c);

							// Set scale mode
							svg.setCanvasScaleMode(EScaleMode.FIT);
							svg.scaleTo(canvasWidth, canvasHeight);

							if (surfaceHolder.getSurface().isValid())
							{
								// Lock canvas
								Canvas canvas = surfaceHolder.lockCanvas();

								/**
								 * Clear canvas
								 */

								canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

								/**
								 * Actual drawing
								 */

								// Render raster
								if (raster)
									canvas = SvgRenderer.renderRasterToCanvas(canvas, svg);

								// Render SVG
								canvas = SvgRenderer.renderToCanvas(canvas, svg);

								// Render bounding rects
								if (boundingRects)
									canvas = SvgRenderer.renderBoundingRectsToCanvas(canvas, svg);

								surfaceHolder.unlockCanvasAndPost(canvas);
							}
						}
					}
				}
			}
		});

		animationThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				millisStart = System.currentTimeMillis();

				long millisBefore = 0;
				long millisAfter = 0;
				long millisFrame = (long) (1000 / fpsDesired);

				// Wait for svg
				if (svg == null)
				{
					while (running)
					{
						try
						{
							Thread.sleep(millisFrame);
						}

						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}

				if (svg != null)
				{
					while (running)
					{
						synchronized (svg)
						{
							// P E R F O R M

							if (millisAfter - millisBefore != 0)
							{
								fpsCurrent = ((float) (1000 / (millisAfter - millisBefore)));
							}

							// A N I M A T I O N

							millisBefore = System.currentTimeMillis();

							for (AGeometric g : svg.getAllSubElements())
							{
								for (AAnimate a : g.getAnimations())
								{
									if (a instanceof AnimateTransform)
									{
										AnimateTransform at = (AnimateTransform) a;

										g.setAnimationTransform(at.getTransformOperator(millisBefore - millisStart));
									} else if (a instanceof AnimateColor)
									{
										AnimateColor ac = (AnimateColor) a;

										long millisSinceStart = millisBefore - millisStart;

										g.setAnimationColor(ac.getColorOperator(millisSinceStart));
									}
								}
							}

							millisAfter = System.currentTimeMillis();

							// P A U S E

							if (millisAfter - millisBefore < millisFrame)
							{
								try
								{
									Thread.sleep(millisFrame - (millisAfter - millisBefore));
								} catch (InterruptedException e)
								{
									e.printStackTrace();
								}
							}
							millisAfter = System.currentTimeMillis();
						}
					}
				}
			}
		});

//		animationThread.start();
		renderingThread.start();
	}

	public void pause()
	{
		boolean retry = true;
		running = false;

		while (retry)
		{
			try
			{
				animationThread.join();
				renderingThread.join();
				retry = false;
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning()
	{
		return running;
	}

	public Vector2 getTouch()
	{
		return touch;
	}

	public void setTouch(Vector2 v)
	{
		touch = new Vector2(v);
	}

	// -------------------------
	// Getters / Setter
	// -------------------------

	public void setSVG(SVG svg)
	{
		this.svg = svg;
	}

	public float getFpsCurrent()
	{
		return fpsCurrent;
	}

	public boolean isBoundingRects()
	{
		return boundingRects;
	}

	public void setBoundingRects(boolean boundingRects)
	{
		this.boundingRects = boundingRects;
	}

	public boolean isRaster()
	{
		return raster;
	}

	public void setRaster(boolean raster)
	{
		this.raster = raster;
	}
}