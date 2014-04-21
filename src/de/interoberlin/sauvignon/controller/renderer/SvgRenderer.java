package de.interoberlin.sauvignon.controller.renderer;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.Circle;
import de.interoberlin.sauvignon.model.svg.elements.Line;
import de.interoberlin.sauvignon.model.svg.elements.Path;
import de.interoberlin.sauvignon.model.svg.elements.Rect;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgRenderer
{
	public static Canvas renderToCanvas(Canvas canvas, SVG svg)
	{
		List<AElement> all = svg.getAllSubElements();

		for (AElement e : all)
		{
			switch (e.getType())
			{
				case RECT:
				{
					Rect r = (Rect) e;
					float x = r.getX();
					float y = r.getY();
					float width = r.getWidth();
					float height = r.getHeight();
					float rx = r.getRx();
					float ry = r.getRy();

					Paint paint = new Paint(r.getFill());

					canvas.drawRoundRect(new RectF(x, y, x + width, y + height), rx, ry, paint);
					break;
				}
				case CIRCLE:
				{
					Circle c = (Circle) e;
					float cx = c.getCx();
					float cy = c.getCy();
					float r = c.getR();

					Paint p = new Paint(c.getFill());

					canvas.drawCircle(cx, cy, r, p);
					break;
				}
				case LINE:
				{
					Line l = (Line) e;
					Paint stroke = l.getStroke();
					
					stroke.setStrokeWidth(l.getStrokeWidth());
					
					canvas.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2(), stroke);		
					break;
				}
				case PATH:
				{
					Path p = (Path) e;

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
