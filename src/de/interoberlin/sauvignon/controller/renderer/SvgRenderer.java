package de.interoberlin.sauvignon.controller.renderer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.webkit.WebChromeClient.CustomViewCallback;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.SVGCircle;
import de.interoberlin.sauvignon.model.svg.elements.SVGEllipse;
import de.interoberlin.sauvignon.model.svg.elements.SVGLine;
import de.interoberlin.sauvignon.model.svg.elements.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.SVGPathSegment;
import de.interoberlin.sauvignon.model.svg.elements.SVGPathSegmentCoordinateType;
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
					Vector2 cursor = new Vector2();
					float cursorX;
					float cursorY;
					List<Vector2> coords = new ArrayList<Vector2>();

					SVGPath p = (SVGPath) element;

					for (SVGPathSegment s : p.getD())
					{
						coords.clear();
						cursorX = cursor.getX();
						cursorY = cursor.getY();

						switch (s.getSegmentType())
						{
							case MOVETO:
								// Read
								float moveX = s.getNumbers().get(0);
								float moveY = s.getNumbers().get(1); 

								if (s.getCoordinateType() == SVGPathSegmentCoordinateType.ABSOLUTE)
								{
									coords.add(new Vector2(moveX, moveY));
								} else
								{
									coords.add(new Vector2(moveX + cursorX, moveY + cursorY));
								}
								break;
							case LINETO:
								// Read
								float lineX = s.getNumbers().get(0);
								float lineY = s.getNumbers().get(1);

								if (s.getCoordinateType() == SVGPathSegmentCoordinateType.ABSOLUTE)
								{
									coords.add(new Vector2(lineX, lineY));
								} else
								{
									coords.add(new Vector2(lineX + cursorX, lineY + cursorY));
								}

								// Draw line
								float startX = cursor.getX();
								float startY = cursor.getY();
								float endX = coords.get(0).getX();
								float endY = coords.get(0).getY();

								canvas.drawLine(startX * scaleX, startY * scaleY, endX * scaleX, endY * scaleY, p.getStroke());
								break;
							case LINETO_HORIZONTAL:
								// Read
								coords.add(new Vector2(s.getNumbers().get(0), 0.0f));
								// Draw line
								canvas.drawLine(cursor.getX(), cursor.getY(), coords.get(0).getX(), coords.get(0).getY(), p.getStroke());
								break;
							case LINETO_VERTICAL:
								// Read
								coords.add(new Vector2(0.0f, s.getNumbers().get(0)));
								// Draw line
								canvas.drawLine(cursor.getX(), cursor.getY(), coords.get(0).getX(), coords.get(0).getY(), p.getStroke());
								break;
							case CLOSEPATH:
								break;
							case CURVETO_CUBIC:
								break;
							case CURVETO_CUBIC_SMOOTH:
								break;
							case CURVETO_QUADRATIC:
								break;
							case CURVETO_QUADRATIC_SMOOTH:
								break;
							case ARC:
								break;
						}

						// Set cursor to last known coordinate
						if (!coords.isEmpty())
							cursor = new Vector2(coords.get(coords.size() - 1));
					}

					break;
				}
			}
		}

		return canvas;
	}
}