package de.interoberlin.sauvignon.controller.renderer;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.SVGCircle;
import de.interoberlin.sauvignon.model.svg.elements.SVGLine;
import de.interoberlin.sauvignon.model.svg.elements.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.SVGRect;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgRenderer
{
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		List<AElement> all = svg.getAllSubElements();

		float scaleX = svg.getScaleX();
		float scaleY = svg.getScaleY();

		for (AElement e : all)
		{
			switch (e.getType())
			{
				case RECT:
				{
					SVGRect r = (SVGRect) e;
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
					SVGCircle c = (SVGCircle) e;
					float cx = c.getCx() * scaleX;
					float cy = c.getCy() * scaleY;
					float r = c.getR() * scaleX;

					Paint p = new Paint(c.getFill());

					canvas.drawCircle(cx, cy, r, p);
					break;
				}
				case LINE:
				{
					SVGLine l = (SVGLine) e;
					Paint stroke = l.getStroke();

					stroke.setStrokeWidth(l.getStrokeWidth());

					canvas.drawLine(l.getX1() * scaleX, l.getY1() * scaleY, l.getX2() * scaleX, l.getY2() * scaleY, stroke);
					break;
				}
				case PATH:
				{
					SVGPath p = (SVGPath) e;

					Paint paint = new Paint(p.getStroke());
					List<Vector2> l = p.getD();

					for (int i = 0; i < p.getD().size(); i++)
					{
						Vector2 start = p.getD().get(i);
						Vector2 stop = p.getD().get((i + 1) % p.getD().size());

						canvas.drawLine(start.getX(), start.getY(), stop.getX(), stop.getY(), paint);
					}
					break;
				}
			}
		}

		return canvas;
	}
}
