package de.interoberlin.sauvignon.controller.renderer;

import java.util.ArrayList;
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
import de.interoberlin.sauvignon.model.svg.elements.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.SVGPathSegment;
import de.interoberlin.sauvignon.model.svg.elements.ESVGPathSegmentCoordinateType;
import de.interoberlin.sauvignon.model.svg.elements.SVGRect;
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
					float x = r.getX() * scaleX;
					float y = r.getY() * scaleY;
					float width = r.getWidth() * scaleX;
					float height = r.getHeight() * scaleY;
					float rx = r.getRx() * scaleX;
					float ry = r.getRy() * scaleY;

					Paint paint = new Paint(r.getFill());

					canvas.drawRoundRect(new RectF(x, y, x + width, y + height), rx, ry, paint);
					break;
				}
				case CIRCLE:
				{
					SVGCircle c = (SVGCircle) element;
					float cx = c.getCx() * scaleX;
					float cy = c.getCy() * scaleY;
					float r = c.getR() * scaleX;

					Paint p = new Paint(c.getFill());

					canvas.drawCircle(cx, cy, r, p);
					break;
				}
				case ELLIPSE:
				{
					SVGEllipse e = (SVGEllipse) element;
					float cx = e.getCx() * scaleX;
					float cy = e.getCy() * scaleY;
					float rx = e.getRx() * scaleX;
					float ry = e.getRy() * scaleY;

					Paint p = new Paint(e.getFill());

					canvas.drawOval(new RectF(cx - rx, cy - ry, cx + rx, cy + ry), p);
					break;
				}
				case LINE:
				{
					SVGLine l = (SVGLine) element;
					Paint stroke = l.getStroke();

					stroke.setStrokeWidth(l.getStrokeWidth());

					canvas.drawLine(l.getX1() * scaleX, l.getY1() * scaleY, l.getX2() * scaleX, l.getY2() * scaleY, stroke);
					break;
				}
				case PATH:
				{
					SVGPath p = (SVGPath) element;

					Paint stroke = p.getStroke();
					stroke.setStrokeWidth(p.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					Vector2 cursor = new Vector2();

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

								// Draw line
								float startX = cursor.getX();
								float startY = cursor.getY();
								float endX = lineto.getX();
								float endY = lineto.getY();

								canvas.drawLine(startX * scaleX, startY * scaleY, endX * scaleX, endY * scaleY, stroke);

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

								// Draw line
								float startX = cursor.getX();
								float startY = cursor.getY();
								float endX = lineto.getX();
								float endY = lineto.getY();

								canvas.drawLine(startX * scaleX, startY * scaleY, endX * scaleX, endY * scaleY, stroke);

								// Set cursor
								cursor.set(lineto);
								break;
							}
							case LINETO_VERTICAL:
							{
								// Read
								Vector2 lineto = new Vector2(0.0f, s.getNumbers().get(0));

								if (s.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(new Vector2(0.0f, cursor.getY()));
								}

								// Draw line
								float startX = cursor.getX();
								float startY = cursor.getY();
								float endX = lineto.getX();
								float endY = lineto.getY();

								canvas.drawLine(startX * scaleX, startY * scaleY, endX * scaleX, endY * scaleY, stroke);

								// Set cursor
								cursor.set(lineto);
								break;
							}
							case CLOSEPATH:
							{
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

								// Draw curve
								Path path = new Path();
								path.moveTo(cursor.getX() * scaleX, cursor.getY() * scaleY);
								path.cubicTo(c1.getX() * scaleX, c1.getY() * scaleY, c2.getX() * scaleX, c2.getY() * scaleY, end.getX() * scaleX, end.getY() * scaleY);
								canvas.drawPath(path, stroke);

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