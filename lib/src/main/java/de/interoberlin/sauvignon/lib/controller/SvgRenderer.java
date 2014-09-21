package de.interoberlin.sauvignon.lib.controller;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RectF;

import java.util.List;

import de.interoberlin.sauvignon.lib.model.svg.SVG;
import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.svg.elements.circle.SVGCircle;
import de.interoberlin.sauvignon.lib.model.svg.elements.ellipse.SVGEllipse;
import de.interoberlin.sauvignon.lib.model.svg.elements.line.SVGLine;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.SVGPathSegment;
import de.interoberlin.sauvignon.lib.model.svg.elements.polygon.SVGPolygon;
import de.interoberlin.sauvignon.lib.model.svg.elements.polyline.SVGPolyline;
import de.interoberlin.sauvignon.lib.model.svg.elements.rect.SVGRect;
import de.interoberlin.sauvignon.lib.model.svg.transform.color.EColorOperatorType;
import de.interoberlin.sauvignon.lib.model.svg.transform.set.SetOperator;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.SVGPaint;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SvgRenderer
{
	/**
	 * Renders svg object to canvas
	 * 
	 * @param canvas
	 * @param svg
	 * @return
	 */
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		for (AGeometric element : svg.getAllSubElements())
		{
			if (element != null && canvas != null)
			{
				switch (element.getType())
				{
					case RECT:
					{
						renderRect((SVGRect) element, canvas);
						break;
					}
					case CIRCLE:
					{
						renderCircle((SVGCircle) element, canvas);
						break;
					}
					case ELLIPSE:
					{
						renderEllipse((SVGEllipse) element, canvas);
						break;
					}
					case LINE:
					{
						renderLine((SVGLine) element, canvas);
						break;
					}
					case PATH:
					{
						renderPath((SVGPath) element, canvas);
						break;
					}
					case POLYLINE:
					{
						renderPolyline((SVGPolyline) element, canvas);
						break;
					}
					case POLYGON:
					{
						renderPolygon((SVGPolygon) element, canvas);
						break;
					}
					default:
					{
						break;
					}
				}
			}
		}
		return canvas;
	}

	/**
	 * Renders a raster to canvas
	 * 
	 * @param canvas
	 * @param svg
	 * @return
	 */
	public static Canvas renderRasterToCanvas(Canvas canvas, SVG svg)
	{
		SVGPaint mainLine = new SVGPaint();
		mainLine.setARGB(150, 150, 150, 150);
		mainLine.setStyle(Style.STROKE);
		mainLine.setStrokeWidth(4);

		SVGPaint subLine = new SVGPaint();
		subLine.setARGB(150, 150, 150, 150);
		subLine.setStyle(Style.STROKE);
		subLine.setStrokeWidth(2);
		subLine.setPathEffect(new DashPathEffect(new float[]
		{ 10, 10 }, 0));

		// Calculate distances between raster lines
		float smallest = svg.getHeight() < svg.getWidth() ? svg.getHeight() : svg.getWidth();

		int digits = (int) (Math.log10(smallest) + 1);
		float distance = (float) (Math.pow(10, (digits - 1)) / 2);

		// Draw vertical lines
		for (int i = 0; i < svg.getWidth() / distance; i++)
		{
			SVGLine l = new SVGLine();
			Vector2 one = new Vector2(i * distance, 0f);
			Vector2 two = new Vector2(i * distance, svg.getHeight());

			// one = one.applyCTM(svg.getCTM());
			// two = two.applyCTM(svg.getCTM());

			l.setX1(one.getX());
			l.setY1(one.getY());
			l.setX2(two.getX());
			l.setY2(two.getY());

			if (i % 2 == 1)
			{
				l.getStyle().setStroke(subLine);
				l.getStyle().setStrokeWidth(2);
			} else
			{
				l.getStyle().setStroke(mainLine);
				l.getStyle().setStrokeWidth(4);
			}

			l.setParentSVG(svg);
			renderLine(l, canvas);
		}

		// Draw horizontal lines
		for (int i = 0; i < svg.getHeight() / distance; i++)
		{
			SVGLine l = new SVGLine();
			Vector2 one = new Vector2(0f, i * distance);
			Vector2 two = new Vector2(svg.getHeight(), i * distance);

			l.setX1(one.getX());
			l.setY1(one.getY());
			l.setX2(two.getX());
			l.setY2(two.getY());

			if (i % 2 == 1)
			{
				l.getStyle().setStroke(subLine);
				l.getStyle().setStrokeWidth(2);
			} else
			{
				l.getStyle().setStroke(mainLine);
				l.getStyle().setStrokeWidth(4);
			}

			l.setParentSVG(svg);
			renderLine(l, canvas);
		}

		return canvas;
	}

	/**
	 * Renders bounding rects information of svg to canvas
	 * 
	 * @param canvas
	 * @param svg
	 * @return
	 */
	public static Canvas renderBoundingRectsToCanvas(Canvas canvas, SVG svg, boolean parallelToAxes)
	{
		List<AGeometric> all = svg.getAllSubElements();

		Paint boundingRectColor = new Paint();
		boundingRectColor.setARGB(150, 255, 0, 255);
		boundingRectColor.setStyle(Style.STROKE);
		boundingRectColor.setStrokeWidth(5);

		for (AGeometric element : all)
		{
			if (element.getType() != EElement.G)
			{
				// Render bounding rect
				BoundingRect br = element.getBoundingRect();
				Matrix ctm = element.getCTM();

				if (parallelToAxes)
				{
					ctm = element.getParentSVG().getCTM().multiply(ctm);
					br = br.applyMatrix(ctm);
				} else
				{
					ctm = element.getParentSVG().getCTM().multiply(ctm);

					br.setUpperLeft(br.getUpperLeft().applyCTM(ctm));
					br.setUpperRight(br.getUpperRight().applyCTM(ctm));
					br.setLowerLeft(br.getLowerLeft().applyCTM(ctm));
					br.setLowerRight(br.getLowerRight().applyCTM(ctm));
				}

				Path p = new Path();
				p.moveTo(br.getUpperLeft().getX(), br.getUpperLeft().getY());
				p.lineTo(br.getUpperRight().getX(), br.getUpperRight().getY());
				p.lineTo(br.getLowerRight().getX(), br.getLowerRight().getY());
				p.lineTo(br.getLowerLeft().getX(), br.getLowerLeft().getY());
				p.close();

				canvas.drawPath(p, boundingRectColor);
			}
		}

		return canvas;
	}

	private static void renderRect(SVGRect e, Canvas canvas)
	{
		// S T Y L E

		Paint fill = e.getStyle().getFill();
		if (fill != null)
		{
			fill.setStyle(Style.FILL);
		}

		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.STROKE);
		}

		// Animation : fill
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.FILL)
		{
			fill = e.getAnimationColor().getResultingPaint();
			fill.setStyle(Style.FILL);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// G E O M E T R I C S

		// Animation : animateSet
		for (SetOperator a : e.getAnimationSets())
		{
			switch (a.getAttributeName())
			{
				case X:
					e.setX(a.getValue());
					break;
				case Y:
					e.setY(a.getValue());
					break;
				case WIDTH:
					e.setWidth(a.getValue());
					break;
				case HEIGHT:
					e.setHeight(a.getValue());
					break;
				default:
					break;
			}
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyMatrix(ctmScale.multiply(ctmElement));

		// D R A W I N G

		Vector2 ul = e.getUpperLeft();
		Vector2 ur = e.getUpperRight();
		Vector2 ll = e.getLowerLeft();
		Vector2 lr = e.getLowerRight();

		Path p = new Path();
		p.moveTo(ul.getX(), ul.getY());
		p.lineTo(ur.getX(), ur.getY());
		p.lineTo(lr.getX(), lr.getY());
		p.lineTo(ll.getX(), ll.getY());
		p.close();

		if (fill != null)
			canvas.drawPath(p, fill);
		if (stroke != null)
			canvas.drawPath(p, stroke);
	}

	private static void renderCircle(SVGCircle e, Canvas canvas)
	{
		Paint fill = e.getStyle().getFill();
		if (fill != null)
		{
			fill.setStyle(Style.FILL);
		}

		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.STROKE);
		}

		// Animation : fill
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.FILL)
		{
			fill = e.getAnimationColor().getResultingPaint();
			fill.setStyle(Style.FILL);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		float cx = e.getCx();
		float cy = e.getCy();
		float r = e.getRadius();

		if (fill != null)
			canvas.drawCircle(cx, cy, r, fill);
		if (stroke != null)
			canvas.drawCircle(cx, cy, r, stroke);
	}

	private static void renderEllipse(SVGEllipse e, Canvas canvas)
	{
		Paint fill = e.getStyle().getFill();
		if (fill != null)
		{
			fill.setStyle(Style.FILL);
		}

		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.STROKE);
		}

		// Animation : fill
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.FILL)
		{
			fill = e.getAnimationColor().getResultingPaint();
			fill.setStyle(Style.FILL);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		float cx = e.getCx();
		float cy = e.getCy();
		float rx = e.getRx();
		float ry = e.getRy();

		if (fill != null)
			canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), fill);
		if (stroke != null)
			canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), stroke);
	}

	private static void renderLine(SVGLine e, Canvas canvas)
	{
		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStyle(Style.STROKE);
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		if (stroke != null)
			canvas.drawLine(e.getX1(), e.getY1(), e.getX2(), e.getY2(), stroke);
	}

	private static void renderPath(SVGPath e, Canvas canvas)
	{
		Paint fill = e.getStyle().getFill();
		if (fill != null)
		{
			fill.setStyle(Style.FILL);
		}

		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStrokeCap(e.getStyle().getStrokeLinecap());
			stroke.setStyle(Style.STROKE);
		}

		// Animation : fill
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.FILL)
		{
			fill = e.getAnimationColor().getResultingPaint();
			fill.setStyle(Style.FILL);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStrokeCap(e.getStyle().getStrokeLinecap());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		Path path = new Path();

		for (SVGPathSegment segment : e.getD())
		{
			switch (segment.getSegmentType())
			{
				case MOVETO:
				{
					Vector2 moveto = segment.getCoordinates().get(1);
					path.moveTo(moveto.getX(), moveto.getY());
					break;
				}
				case LINETO:
				case LINETO_HORIZONTAL:
				case LINETO_VERTICAL:
				{
					Vector2 lineto = segment.getCoordinates().get(1);
					path.lineTo(lineto.getX(), lineto.getY());
					break;
				}
				case CLOSEPATH:
				{
					path.close();
					break;
				}
				case CURVETO_CUBIC:
				{
					Vector2 c1 = segment.getCoordinates().get(1);
					Vector2 c2 = segment.getCoordinates().get(2);
					Vector2 end = segment.getCoordinates().get(3);

					path.cubicTo(c1.getX(), c1.getY(), c2.getX(), c2.getY(), end.getX(), end.getY());
					break;
				}
				case CURVETO_CUBIC_SMOOTH:
				{
					break;
				}
				case CURVETO_QUADRATIC:
				{
					Vector2 c = segment.getCoordinates().get(1);
					Vector2 end = segment.getCoordinates().get(2);

					path.quadTo(c.getX(), c.getY(), end.getX(), end.getY());
					break;
				}
				case CURVETO_QUADRATIC_SMOOTH:
				{
					break;
				}
				case ARC:
				{
					break;
				}
			}

		}

		// Draw path
		if (fill != null)
			canvas.drawPath(path, fill);
		if (stroke != null)
			canvas.drawPath(path, stroke);
	}

	private static void renderPolyline(SVGPolyline e, Canvas canvas)
	{
		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.STROKE);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		List<Vector2> points = e.getPoints();
		Path path = new Path();
		path.moveTo(points.get(0).getX(), points.get(0).getY());

		for (Vector2 point : points)
		{
			path.lineTo(point.getX(), point.getY());
		}

		if (stroke != null)
			canvas.drawPath(path, stroke);
	}

	private static void renderPolygon(SVGPolygon e, Canvas canvas)
	{
		Paint fill = e.getStyle().getFill();
		if (fill != null)
		{
			fill.setStyle(Style.FILL);
		}

		Paint stroke = e.getStyle().getStroke();
		if (stroke != null)
		{
			stroke.setStyle(Style.STROKE);
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
		}

		FillType ft = null;
		switch (e.getFillRule())
		{
			case EVENODD:
			{
				ft = FillType.EVEN_ODD;
				break;
			}
			case NONZERO:
			{
				ft = FillType.WINDING;
				break;
			}
			default:
			{
				break;
			}
		}

		// Animation : fill
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.FILL)
		{
			fill = e.getAnimationColor().getResultingPaint();
			fill.setStyle(Style.FILL);
		}

		// Animation : stroke
		if (e.getAnimationColor() != null && e.getAnimationColor().getType() == EColorOperatorType.STROKE)
		{
			stroke = e.getAnimationColor().getResultingPaint();
			stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
			stroke.setStyle(Style.FILL);
		}

		// Animation : transformation
		Matrix ctmElement = e.getElementMatrix();
		Matrix ctmScale = e.getScaleMatrix();
		e = e.applyCTM(ctmScale.multiply(ctmElement));

		List<Vector2> points = e.getPoints();
		Path path = new Path();
		path.moveTo(points.get(0).getX(), points.get(0).getY());

		for (Vector2 point : points)
		{
			path.lineTo(point.getX(), point.getY());
		}

		path.close();
		path.setFillType(ft);

		if (fill != null)
			canvas.drawPath(path, fill);
		if (stroke != null)
			canvas.drawPath(path, stroke);
	}
}