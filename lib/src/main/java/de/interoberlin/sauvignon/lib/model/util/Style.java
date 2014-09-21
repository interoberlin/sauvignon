package de.interoberlin.sauvignon.lib.model.util;

import android.graphics.Paint.Cap;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;

/**
 * All geometric elements have style attributes, some are defined explicitly,
 * e.g. <rect fill="blue"/> some are defined implicitly, e.g. <rect
 * style="fill:blue;"/>
 */
public class Style
{
	private SVGPaint	stroke;
	private SVGPaint	fill;
	private float		strokeWidth		= 1.0f;
	private Cap			strokeLinecap	= Cap.BUTT;

	private AGeometric parentElement;

	// -------------------------
	// Methods
	// -------------------------

	public Style clone()
	{
		Style clone = new Style();

		clone.setStroke(this.getStroke().clone());
		clone.setFill(this.getFill().clone());
		clone.setStrokeWidth(this.getStrokeWidth());
		clone.setStrokeLinecap(this.getStrokeLinecap());
		clone.setParentElement(this.getParentElement().clone());

		return clone;
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public SVGPaint getStroke()
	{
		return stroke;
	}

	public void setStroke(SVGPaint stroke)
	{
		this.stroke = stroke;
	}

	public SVGPaint getFill()
	{
		return fill;
	}

	public void setFill(SVGPaint fill)
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

	public Cap getStrokeLinecap()
	{
		return strokeLinecap;
	}

	public void setStrokeLinecap(Cap strokeLinecap)
	{
		this.strokeLinecap = strokeLinecap;
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
