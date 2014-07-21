package de.interoberlin.sauvignon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import de.interoberlin.sauvignon.controller.renderer.SvgRenderer;
import de.interoberlin.sauvignon.model.svg.EScaleMode;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGPanel extends SurfaceView
{
	private Thread			renderingThread	= null;
	private Thread			animationTread	= null;
	SurfaceHolder			surfaceHolder;
	private float			fpsDesired		= 30;
	private float			fpsCurrent;
	private Vector2			touch;
	private static boolean	running			= false;

	private boolean			boundingRects;
	private boolean			raster;

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

				// Set dimensions to fullscreen
				Canvas c = surfaceHolder.lockCanvas();

				int canvasWidth = c.getWidth();
				int canvasHeight = c.getHeight();

				surfaceHolder.unlockCanvasAndPost(c);

				// Set scale mode
				svg.setCanvasScaleMode(EScaleMode.FIT);
				svg.scaleTo(canvasWidth, canvasHeight);

				while (running)
				{
					if (surfaceHolder.getSurface().isValid())
					{
						// Lock canvas
						Canvas canvas = surfaceHolder.lockCanvas();

						/**
						 * Clear canvas
						 */

						canvas.drawRGB(255, 255, 255);

						/**
						 * Actual drawing
						 */

						// Load elements

						// Render raster
						if (raster)
							canvas = SvgRenderer.renderRasterToCanvas(canvas, svg);

						// Render SVG
						canvas = SvgRenderer.renderToCanvas(canvas, svg);

						if (boundingRects)
							canvas = SvgRenderer.renderBoundingRectsToCanvas(canvas, svg);

						// Render bounding rects
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		});

		animationTread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long millisBefore = 0;
				long millisAfter = 0;
				long millisFrame = (long) (1000 / fpsDesired);

				// wait for svg

				if (svg == null)

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

				if (svg != null)
				{
					synchronized (svg)
					{
						// F I N D E L E M E N T S

						// P E R F O R M

						while (running)
						{
							if (millisAfter - millisBefore != 0)
							{
								fpsCurrent = ((float) (1000 / (millisAfter - millisBefore)));
							}

							// A N I M A T I O N

							millisBefore = System.currentTimeMillis();

							if (svg != null)
							{
								synchronized (svg)
								{

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

		renderingThread.start();
		animationTread.start();
	}

	public void pause()
	{
		boolean retry = true;
		running = false;

		while (retry)
		{
			try
			{
				renderingThread.join();
				animationTread.join();
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