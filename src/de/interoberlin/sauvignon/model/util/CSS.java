package de.interoberlin.sauvignon.model.util;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * All geometric elements have style attributes, some are defined explicitly,
 * e.g. <rect fill="blue"/> some are defined implicitly, e.g. <rect
 * style="fill:blue;"/>
 */
public class CSS
{
	private Paint		stroke;
	private Paint		fill;
	private float		strokeWidth	= 1.0f;

	private AGeometric	parentElement;

	public Paint getStroke()
	{
		return stroke;
	}

	public void setStroke(Paint stroke)
	{
		this.stroke = stroke;
	}

	public Paint getFill()
	{
		return fill;
	}

	public int getFillA()
	{
		return fill.getAlpha();
	}

	public void setFillA(int a)
	{
		fill.setAlpha(a);
	}

	public int getFillR()
	{
		return Color.red(fill.getColor());
	}

	public void setFillR(int r)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), (r % 255), getFillG(), getFillB());

		setFill(p);
	}

	public int getFillG()
	{
		return Color.green(fill.getColor());
	}

	public void setFillG(int g)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), getFillR(), (g % 255), getFillB());

		setFill(p);
	}

	public int getFillB()
	{
		return Color.blue(fill.getColor());
	}

	public void setFillB(int b)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), getFillR(), getFillG(), (b % 255));

		setFill(p);
	}

	public void setFill(Paint fill)
	{
		this.fill = fill;
	}

	public float getStrokeWidth()
	{
		return strokeWidth;
	}

	public void setStrokeWidth(float f)
	{
		if (f >= 0)
		{
			this.strokeWidth = f;
		}
	}

	public AGeometric getParentElement()
	{
		return parentElement;
	}

	public void setParentElement(AGeometric parentElement)
	{
		this.parentElement = parentElement;
	}
}
