package de.interoberlin.sauvignon.controller.renderer;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.circle.SVGCircle;
import de.interoberlin.sauvignon.model.svg.elements.ellipse.SVGEllipse;
import de.interoberlin.sauvignon.model.svg.elements.line.SVGLine;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPathSegment;
import de.interoberlin.sauvignon.model.svg.elements.path.SvgPathCurvetoCubic;
import de.interoberlin.sauvignon.model.svg.elements.path.SvgPathCurvetoQuadratic;
import de.interoberlin.sauvignon.model.svg.elements.path.SvgPathLineto;
import de.interoberlin.sauvignon.model.svg.elements.path.SvgPathMoveto;
import de.interoberlin.sauvignon.model.svg.elements.rect.SVGRect;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgRenderer
{
	private static void renderRect(SVGRect r, Canvas canvas)
	{
		r.applyCTM();
		
		Paint fill = r.getStyle().getFill();
		fill.setStyle(Style.FILL);

		Paint stroke = r.getStyle().getStroke();
		stroke.setStrokeWidth(r.getStyle().getStrokeWidth());
		stroke.setStyle(Style.STROKE);

		float x = r.getX();
		float y = r.getY();
		float width = r.getWidth();
		float height = r.getHeight();

		Vector2 ul = new Vector2(x, y);
		Vector2 ur = new Vector2(x + width, y);
		Vector2 ll = new Vector2(x, y + height);
		Vector2 lr = new Vector2(x + width, y + height);

		Path p = new Path();
		p.moveTo(ul.getX(), ul.getY());
		p.lineTo(ur.getX(), ur.getY());
		p.lineTo(lr.getX(), lr.getY());
		p.lineTo(ll.getX(), ll.getY());
		p.close();

		canvas.drawPath(p, fill);
		canvas.drawPath(p, stroke);
	}
	
	private static void renderCircle(SVGCircle c, Canvas canvas)
	{
		c.applyCTM();
		
		Paint fill = c.getStyle().getFill();
		fill.setStyle(Style.FILL);

		Paint stroke = c.getStyle().getStroke();
		stroke.setStrokeWidth(c.getStyle().getStrokeWidth());
		stroke.setStyle(Style.STROKE);

		float cx = c.getCx();
		float cy = c.getCy();
		float r = c.getR();

		canvas.drawCircle(cx, cy, r, fill);
		canvas.drawCircle(cx, cy, r, stroke);
	}
	
	private static void renderEllipse(SVGEllipse e, Canvas canvas)
	{
		e.applyCTM();
		
		Paint fill = e.getStyle().getFill();
		fill.setStyle(Style.FILL);

		Paint stroke = e.getStyle().getStroke();
		stroke.setStrokeWidth(e.getStyle().getStrokeWidth());
		stroke.setStyle(Style.STROKE);

		float cx = e.getCx();
		float cy = e.getCy();
		float rx = e.getRx();
		float ry = e.getRy();

		canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), fill);
		canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), stroke);
	}
	
	private static void renderLine(SVGLine l, Canvas canvas)
	{
		l.applyCTM();
		
		Paint stroke = l.getStyle().getStroke();
		stroke.setStyle(Style.STROKE);
		stroke.setStrokeWidth(l.getStyle().getStrokeWidth());

		canvas.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2(), stroke);
	}
	
	private static void renderPath(SVGPath elementPath, Canvas canvas)
	{
		elementPath.makeAllSegmentsAbsolute();
		elementPath.applyCTM();

		Paint fill = elementPath.getStyle().getFill();
		fill.setStyle(Style.FILL);

		Paint stroke = elementPath.getStyle().getStroke();
		stroke.setStrokeWidth(elementPath.getStyle().getStrokeWidth());
		stroke.setStyle(Style.STROKE);

		Path androidPath = new Path();
		
		// TODO: Cursor needs to be removed
		Vector2 cursor = new Vector2();

		for (SVGPathSegment segment : elementPath.getD())
		{
			switch (segment.getSegmentType())
			{
				case MOVETO:
				{
					SvgPathMoveto moveto = (SvgPathMoveto) segment;
					androidPath.moveTo(moveto.getX(), moveto.getY());
					break;
				}
				case LINETO:
				{
					SvgPathLineto lineto = (SvgPathLineto) segment;
					androidPath.lineTo(lineto.getX(), lineto.getY());
					break;
				}
				case LINETO_HORIZONTAL:
				{
					break;
				}
				case LINETO_VERTICAL:
				{
					break;
				}
				case CLOSEPATH:
				{
					androidPath.close();
					break;
				}
				case CURVETO_CUBIC:
				{
					SvgPathCurvetoCubic cubicto = (SvgPathCurvetoCubic) segment;
					androidPath.cubicTo(
										cubicto.getC1X(), cubicto.getC1Y(),
										cubicto.getC2X(), cubicto.getC2Y(),
										cubicto.getEndX(), cubicto.getEndY()
										);
					break;
				}
				case CURVETO_CUBIC_SMOOTH:
				{
					break;
				}
				case CURVETO_QUADRATIC:
				{
					SvgPathCurvetoQuadratic quadto = (SvgPathCurvetoQuadratic) segment; 
					androidPath.quadTo(
										quadto.getCX(), quadto.getCY(),
										quadto.getEndX(), quadto.getEndY()
										);
					break;
				}
				case CURVETO_QUADRATIC_SMOOTH:
				{
					break;
				}
				case ARC:
				{
					float rx = segment.getNumbers().get(0);
					float ry = segment.getNumbers().get(1);
					float xAxisRotation = segment.getNumbers().get(2);
					boolean largeArcFlag = segment.getNumbers().get(3) == 1;
					boolean sweepFlag = segment.getNumbers().get(4) == 1;
					float x = segment.getNumbers().get(5);
					float y = segment.getNumbers().get(6);

					// Check whether cursor and end point (x,y) are
					// identical
					if (cursor.getX() == x && cursor.getY() == y)
					{
						break;
					}

					// Handle degenerate case (behaviour specified
					// by the spec)
					if (rx == 0 || ry == 0)
					{
						androidPath.lineTo(x, y);
						break;
					}

					// Sign of the radii is ignored (behaviour
					// specified by the spec)
					rx = Math.abs(rx);
					ry = Math.abs(ry);

					// Convert angle from degrees to radians
					float angleRad = (float) Math.toRadians(xAxisRotation % 360.0);
					double cosAngle = Math.cos(angleRad);
					double sinAngle = Math.sin(angleRad);

					// We simplify the calculations by transforming
					// the arc so that the origin is at the
					// midpoint calculated above followed by a
					// rotation to line up the coordinate axes
					// with the axes of the ellipse.

					// Compute the midpoint of the line between the
					// current and the end point
					double dx2 = (cursor.getX() - x) / 2.0;
					double dy2 = (cursor.getY() - y) / 2.0;

					// Step 1 : Compute (x1', y1') - the transformed
					// start point
					double x1 = (cosAngle * dx2 + sinAngle * dy2);
					double y1 = (-sinAngle * dx2 + cosAngle * dy2);

					double rx_sq = Math.pow(rx, 2);
					double ry_sq = ry * ry;
					double x1_sq = x1 * x1;
					double y1_sq = y1 * y1;

					// Check that radii are large enough.
					// If they are not, the spec says to scale them
					// up so they are.
					// This is to compensate for potential rounding
					// errors/differences between SVG
					// implementations.
					double radiiCheck = x1_sq / rx_sq + y1_sq / ry_sq;
					if (radiiCheck > 1)
					{
						rx = (float) Math.sqrt(radiiCheck) * rx;
						ry = (float) Math.sqrt(radiiCheck) * ry;
						rx_sq = rx * rx;
						ry_sq = ry * ry;
					}

					// Step 2 : Compute (cx1, cy1) - the transformed
					// centre point
					double sign = (largeArcFlag == sweepFlag) ? -1 : 1;
					double sq = ((rx_sq * ry_sq) - (rx_sq * y1_sq) - (ry_sq * x1_sq)) / ((rx_sq * y1_sq) + (ry_sq * x1_sq));
					sq = (sq < 0) ? 0 : sq;
					double coef = (sign * Math.sqrt(sq));
					double cx1 = coef * ((rx * y1) / ry);
					double cy1 = coef * -((ry * x1) / rx);

					// Step 3 : Compute (cx, cy) from (cx1, cy1)
					double sx2 = (cursor.getX() + x) / 2.0;
					double sy2 = (cursor.getY() + y) / 2.0;
					double cx = sx2 + (cosAngle * cx1 - sinAngle * cy1);
					double cy = sy2 + (sinAngle * cx1 + cosAngle * cy1);

					// Step 4 : Compute the angleStart (angle1) and
					// the angleExtent (dangle)
					double ux = (x1 - cx1) / rx;
					double uy = (y1 - cy1) / ry;
					double vx = (-x1 - cx1) / rx;
					double vy = (-y1 - cy1) / ry;
					double p2, n;

					// Compute the angle start
					n = Math.sqrt((ux * ux) + (uy * uy));
					p2 = ux; // (1 * ux) + (0 * uy)
					sign = (uy < 0) ? -1.0 : 1.0;
					double angleStart = Math.toDegrees(sign * Math.acos(p2 / n));

					// Compute the angle extent
					n = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
					p2 = ux * vx + uy * vy;
					sign = (ux * vy - uy * vx < 0) ? -1.0 : 1.0;
					double angleExtent = Math.toDegrees(sign * Math.acos(p2 / n));
					if (!sweepFlag && angleExtent > 0)
					{
						angleExtent -= 360f;
					} else if (sweepFlag && angleExtent < 0)
					{
						angleExtent += 360f;
					}
					angleExtent %= 360f;
					angleStart %= 360f;

					// Many elliptical arc implementations including
					// the Java2D and Android ones, only
					// support arcs that are axis aligned. Therefore
					// we need to substitute the arc
					// with bezier curves. The following method call
					// will generate the beziers for
					// a unit circle that covers the arc angles we
					// want.
					float[] bezierPoints = arcToBeziers(angleStart, angleExtent);

					// Calculate a transformation matrix that will
					// move and scale these bezier points to the
					// correct location.
					android.graphics.Matrix m = new android.graphics.Matrix();
					m.postScale(rx, ry);
					m.postRotate(xAxisRotation);
					m.postTranslate((float) cx, (float) cy);
					m.mapPoints(bezierPoints);

					// The last point in the bezier set should match
					// exactly the last coord pair in the arc (ie:
					// x,y). But
					// considering all the mathematical manipulation
					// we have been doing, it is bound to be off by
					// a tiny
					// fraction. Experiments show that it can be up
					// to around 0.00002. So why don't we just set
					// it to
					// exactly what it ought to be.
					bezierPoints[bezierPoints.length - 2] = x;
					bezierPoints[bezierPoints.length - 1] = y;

					// Final step is to add the bezier curves to the
					// path
					for (int i = 0; i < bezierPoints.length; i += 6)
					{
						androidPath.cubicTo(bezierPoints[i], bezierPoints[i + 1], bezierPoints[i + 2], bezierPoints[i + 3], bezierPoints[i + 4], bezierPoints[i + 5]);
					}

					break;
				}
			}

		}

		// Draw path
		canvas.drawPath(androidPath, fill);
		canvas.drawPath(androidPath, stroke);
	}
	
	private static void renderElement(AElement element, Canvas canvas)
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
				default:
				{
					break;
				}
			}
			element.wasRedrawn();
		}
	}
	
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		for (AElement element : svg.getAllSubElements())
		{
			renderElement(element, canvas);
		}
		svg.wasRedrawn();
		return canvas;
	}

	public static Canvas renderDebugToCanvas(Canvas canvas, SVG svg)
	{
		List<AElement> all = svg.getAllSubElements();

		Paint boundingRectColor = new Paint();
		boundingRectColor.setARGB(150, 255, 0, 255);
		boundingRectColor.setStyle(Style.STROKE);

		for (AElement element : all)
		{
			// Render bounding rect
			BoundingRect br = element.getBoundingRect();

			Path p = new Path();
			p.moveTo(br.getLeft(), br.getTop());
			p.lineTo(br.getRight(), br.getTop());
			p.lineTo(br.getRight(), br.getBottom());
			p.lineTo(br.getLeft(), br.getBottom());
			p.close();

			canvas.drawPath(p, boundingRectColor);
		}

		return canvas;
	}

	private static float[] arcToBeziers(double angleStart, double angleExtent)
	{
		int numSegments = (int) Math.ceil(Math.abs(angleExtent) / 90.0);

		angleStart = Math.toRadians(angleStart);
		angleExtent = Math.toRadians(angleExtent);
		float angleIncrement = (float) (angleExtent / numSegments);

		// The length of each control point vector is given by the following
		// formula.
		double controlLength = 4.0 / 3.0 * Math.sin(angleIncrement / 2.0) / (1.0 + Math.cos(angleIncrement / 2.0));

		float[] coords = new float[numSegments * 6];
		int pos = 0;

		for (int i = 0; i < numSegments; i++)
		{
			double angle = angleStart + i * angleIncrement;
			// Calculate the control vector at this angle
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			// First control point
			coords[pos++] = (float) (dx - controlLength * dy);
			coords[pos++] = (float) (dy + controlLength * dx);
			// Second control point
			angle += angleIncrement;
			dx = Math.cos(angle);
			dy = Math.sin(angle);
			coords[pos++] = (float) (dx + controlLength * dy);
			coords[pos++] = (float) (dy - controlLength * dx);
			// Endpoint of bezier
			coords[pos++] = (float) dx;
			coords[pos++] = (float) dy;
		}
		return coords;
	}
}