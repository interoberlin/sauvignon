package de.interoberlin.sauvignon.controller.renderer;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.SVGCircle;
import de.interoberlin.sauvignon.model.svg.elements.SVGEllipse;
import de.interoberlin.sauvignon.model.svg.elements.SVGLine;
import de.interoberlin.sauvignon.model.svg.elements.SVGRect;
import de.interoberlin.sauvignon.model.svg.elements.path.ESVGPathSegmentCoordinateType;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPathSegment;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgRenderer
{
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		List<AElement> all = svg.getAllSubElements();

		float scaleX = svg.getScaleX();
		float scaleY = svg.getScaleY();

		for (AElement element : all)
		{
			switch (element.getType())
			{
				case RECT:
				{
					SVGRect r = (SVGRect) element;

					Paint fill = r.getFill();
					fill.setStyle(Style.FILL);

					Paint stroke = r.getStroke();
					stroke.setStrokeWidth(r.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					float x = r.getX() * scaleX;
					float y = r.getY() * scaleY;
					float width = r.getWidth() * scaleX;
					float height = r.getHeight() * scaleY;
					// float rx = r.getRx() * scaleX;
					// float ry = r.getRy() * scaleY;

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

					// canvas.drawRoundRect(new RectF(x, y, x + width, y +
					// height), rx, ry, fill);
					// canvas.drawRoundRect(new RectF(x, y, x + width, y +
					// height), rx, ry, stroke);
					break;
				}
				case CIRCLE:
				{
					SVGCircle c = (SVGCircle) element;

					Paint fill = c.getFill();
					fill.setStyle(Style.FILL);

					Paint stroke = c.getStroke();
					stroke.setStrokeWidth(c.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					float cx = c.getCx() * scaleX;
					float cy = c.getCy() * scaleY;
					float r = c.getR() * scaleX;

					canvas.drawCircle(cx, cy, r, fill);
					canvas.drawCircle(cx, cy, r, stroke);
					break;
				}
				case ELLIPSE:
				{
					SVGEllipse e = (SVGEllipse) element;

					Paint fill = e.getFill();
					fill.setStyle(Style.FILL);

					Paint stroke = e.getStroke();
					stroke.setStrokeWidth(e.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					float cx = e.getCx() * scaleX;
					float cy = e.getCy() * scaleY;
					float rx = e.getRx() * scaleX;
					float ry = e.getRy() * scaleY;

					canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), fill);
					canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), stroke);
					break;
				}
				case LINE:
				{
					SVGLine l = (SVGLine) element;

					Paint stroke = l.getStroke();
					stroke.setStyle(Style.STROKE);
					stroke.setStrokeWidth(l.getStrokeWidth());

					canvas.drawLine(l.getX1() * scaleX, l.getY1() * scaleY, l.getX2() * scaleX, l.getY2() * scaleY, stroke);
					break;
				}
				case PATH:
				{
					SVGPath p = (SVGPath) element;

					Paint fill = p.getFill();
					fill.setStyle(Style.FILL);

					Paint stroke = p.getStroke();
					stroke.setStrokeWidth(p.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					Vector2 cursor = new Vector2();

					Path path = new Path();

					for (SVGPathSegment s : p.getD())
					{
						switch (s.getSegmentType())
						{
							case MOVETO:
							{
								// Read
								Vector2 moveto = new Vector2(s.getNumbers().get(0), s.getNumbers().get(1));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									moveto.add(cursor);
								}

								// Append to path
								path.moveTo(moveto.getX() * scaleX, moveto.getY() * scaleY);

								// Set cursor
								cursor.set(moveto);

								break;
							}
							case LINETO:
							{
								// Read
								Vector2 lineto = new Vector2(s.getNumbers().get(0), s.getNumbers().get(1));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(cursor);
								}

								// Append to path
								path.lineTo(lineto.getX() * scaleX, lineto.getY() * scaleY);

								// Set cursor
								cursor.set(lineto);

								break;
							}
							case LINETO_HORIZONTAL:
							{
								// Read
								Vector2 lineto = new Vector2(s.getNumbers().get(0), 0.0f);

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(new Vector2(cursor.getX(), 0.0f));
								}

								// Append to path
								path.lineTo(lineto.getX() * scaleX, lineto.getY() * scaleY);

								// Set cursor
								cursor.set(lineto);

								break;
							}
							case LINETO_VERTICAL:
							{
								Vector2 lineto = new Vector2(0.0f, s.getNumbers().get(0));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(new Vector2(0.0f, cursor.getY()));
								}

								// Append to path
								path.lineTo(lineto.getX() * scaleX, lineto.getY() * scaleY);

								// Set cursor
								cursor.set(lineto);
								break;
							}
							case CLOSEPATH:
							{
								// Append to path
								path.close();

								break;
							}
							case CURVETO_CUBIC:
							{
								// Read
								Vector2 c1 = new Vector2(s.getNumbers().get(0), s.getNumbers().get(1));
								Vector2 c2 = new Vector2(s.getNumbers().get(2), s.getNumbers().get(3));
								Vector2 end = new Vector2(s.getNumbers().get(4), s.getNumbers().get(5));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									c1.add(cursor);
									c2.add(cursor);
									end.add(cursor);
								}

								// Append to path
								path.cubicTo(c1.getX() * scaleX, c1.getY() * scaleY, c2.getX() * scaleX, c2.getY() * scaleY, end.getX() * scaleX, end.getY() * scaleY);

								// Set cursor
								cursor.set(end);

								break;
							}
							case CURVETO_CUBIC_SMOOTH:
							{
								break;
							}
							case CURVETO_QUADRATIC:
							{
								// Read
								Vector2 c = new Vector2(s.getNumbers().get(0), s.getNumbers().get(1));
								Vector2 end = new Vector2(s.getNumbers().get(2), s.getNumbers().get(3));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									c.add(cursor);
									end.add(cursor);
								}

								// Append to path
								path.quadTo(c.getX() * scaleX, c.getY() * scaleY, end.getX() * scaleX, end.getY() * scaleY);

								// Set cursor
								cursor.set(end);
								break;
							}
							case CURVETO_QUADRATIC_SMOOTH:
							{
								break;
							}
							case ARC:
							{
								// float cx = cursor.getX();
								// float cy = cursor.getY();
								// float rx = s.getNumbers().get(0);
								// float ry = s.getNumbers().get(1);
								//
								// RectF oval = new RectF((cx - rx)* scaleX, (cy
								// - ry)* scaleY, (cx + rx)* scaleX, (cy + ry)*
								// scaleY);

								// Append to path
								// path.arcTo(oval, startAngle, sweepAngle)
								break;
							}
						}

						// Draw path
						canvas.drawPath(path, fill);
						canvas.drawPath(path, stroke);
					}

					break;
				}
				default:
				{
					break;
				}
			}
		}

		return canvas;
	}
}