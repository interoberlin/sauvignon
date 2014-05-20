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
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgRenderer
{
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		List<AElement> all = svg.getAllSubElements();

		float canvasScaleX = svg.getCanvasScaleX();
		float canvasScaleY = svg.getCanvasScaleY();

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

					float x = r.getX() * canvasScaleX;
					float y = r.getY() * canvasScaleY;
					float width = r.getWidth() * canvasScaleX;
					float height = r.getHeight() * canvasScaleY;
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

					float cx = c.getCx() * canvasScaleX;
					float cy = c.getCy() * canvasScaleY;
					float r = c.getR() * canvasScaleX;

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

					float cx = e.getCx() * canvasScaleX;
					float cy = e.getCy() * canvasScaleY;
					float rx = e.getRx() * canvasScaleX;
					float ry = e.getRy() * canvasScaleY;

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

					canvas.drawLine(l.getX1() * canvasScaleX, l.getY1() * canvasScaleY, l.getX2() * canvasScaleX, l.getY2() * canvasScaleY, stroke);
					break;
				}
				case PATH:
				{
					SVGPath elementPath = (SVGPath) element;
					Matrix CTM = elementPath.getCTM();

					Paint fill = elementPath.getFill();
					fill.setStyle(Style.FILL);

					Paint stroke = elementPath.getStroke();
					stroke.setStrokeWidth(elementPath.getStrokeWidth());
					stroke.setStyle(Style.STROKE);

					Vector2 cursor = new Vector2();

					Path androiPath = new Path();

					for (SVGPathSegment segment : elementPath.getD())
					{
						switch (segment.getSegmentType())
						{
							case MOVETO:
							{
								// Read
								Vector2 moveto = new Vector2(segment.getNumbers().get(0), segment.getNumbers().get(1));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									moveto.add(cursor);
								}

								Vector2 finalVector = moveto.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								
								// Append to path
								androiPath.moveTo(finalVector.getX(), finalVector.getY());

								// Set cursor
								cursor.set(moveto);

								break;
							}
							case LINETO:
							{
								// Read
								Vector2 lineto = new Vector2(segment.getNumbers().get(0), segment.getNumbers().get(1));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(cursor);
								}

								Vector2 finalVector = lineto.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								
								// Append to path
								androiPath.lineTo(finalVector.getX(), finalVector.getY());

								// Set cursor
								cursor.set(lineto);

								break;
							}
							case LINETO_HORIZONTAL:
							{
								// Read
								Vector2 lineto = new Vector2(segment.getNumbers().get(0), 0.0f);

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(new Vector2(cursor.getX(), 0.0f));
								}

								Vector2 finalVector = lineto.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								
								// Append to path
								androiPath.lineTo(finalVector.getX(), finalVector.getY());

								// Set cursor
								cursor.set(lineto);

								break;
							}
							case LINETO_VERTICAL:
							{
								Vector2 lineto = new Vector2(0.0f, segment.getNumbers().get(0));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									lineto.add(new Vector2(0.0f, cursor.getY()));
								}

								Vector2 finalVector = lineto.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								
								// Append to path
								androiPath.lineTo(finalVector.getX(), finalVector.getY());

								// Set cursor
								cursor.set(lineto);

								break;
							}
							case CLOSEPATH:
							{
								// Append to path
								androiPath.close();

								break;
							}
							case CURVETO_CUBIC:
							{
								// Read
								Vector2 c1 = new Vector2(segment.getNumbers().get(0), segment.getNumbers().get(1));
								Vector2 c2 = new Vector2(segment.getNumbers().get(2), segment.getNumbers().get(3));
								Vector2 end = new Vector2(segment.getNumbers().get(4), segment.getNumbers().get(5));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									c1.add(cursor);
									c2.add(cursor);
									end.add(cursor);
								}

								Vector2 finalC1 = c1.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								Vector2 finalC2 = c2.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								Vector2 finalEnd = end.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);

								// Append to path
								androiPath.cubicTo(
											finalC1.getX(), finalC1.getY(),
											finalC2.getX(), finalC2.getY(),
											finalEnd.getX(), finalEnd.getY()
											);

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
								Vector2 c = new Vector2(segment.getNumbers().get(0), segment.getNumbers().get(1));
								Vector2 end = new Vector2(segment.getNumbers().get(2), segment.getNumbers().get(3));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									c.add(cursor);
									end.add(cursor);
								}

								Vector2 finalC = c.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								Vector2 finalEnd = end.scale(canvasScaleX, canvasScaleY).applyCTM(CTM);
								// Append to path
								androiPath.quadTo(finalC.getX(), finalC.getY(), finalEnd.getX(), finalEnd.getY());

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
						canvas.drawPath(androiPath, fill);
						canvas.drawPath(androiPath, stroke);
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