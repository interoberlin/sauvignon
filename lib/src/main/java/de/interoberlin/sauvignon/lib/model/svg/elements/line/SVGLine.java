package de.interoberlin.sauvignon.lib.model.svg.elements.line;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SVGLine extends AGeometric
{
	public static final EElement	type	= EElement.LINE;

	private float					x1		= 0;
	private float					y1		= 0;
	private float					x2		= 0;
	private float					y2		= 0;

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public SVGLine clone()
	{
		SVGLine clone = new SVGLine();

		clone.setX1(this.getX1());
		clone.setY1(this.getY1());
		clone.setX2(this.getX2());
		clone.setY2(this.getY2());

		return clone;
	}

	public BoundingRect getBoundingRect()
	{
		float left = (x1 < x2) ? x1 : x2;
		float top = (y1 < y2) ? y1 : y2;
		float right = (x1 > x2) ? x1 : x2;
		float bottom = (y1 < y2) ? y1 : y2;

		Vector2 upperLeft = new Vector2(left, top);
		Vector2 upperRight = new Vector2(right, top);
		Vector2 lowerLeft = new Vector2(left, bottom);
		Vector2 lowerRight = new Vector2(right, bottom);

		return new BoundingRect(upperLeft, upperRight, lowerRight, lowerLeft);
	}

	public SVGLine applyCTM(Matrix ctm)
	{
		SVGLine n = new SVGLine();

		Vector2 xy1 = new Vector2(x1, y1).applyCTM(ctm);
		n.setX1(xy1.getX());
		n.setY1(xy1.getY());

		Vector2 xy2 = new Vector2(x2, y2).applyCTM(ctm);
		n.setX2(xy2.getX());
		n.setY2(xy2.getY());

		return n;
	}

	public SVGLine applyCTM()
	{
		return applyCTM(getCTM());
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public float getX1()
	{
		return x1;
	}

	public void setX1(float x1)
	{
		this.x1 = x1;
	}

	public float getY1()
	{
		return y1;
	}

	public void setY1(float y1)
	{
		this.y1 = y1;
	}

	public float getX2()
	{
		return x2;
	}

	public void setX2(float x2)
	{
		this.x2 = x2;
	}

	public float getY2()
	{
		return y2;
	}

	public void setY2(float y2)
	{
		this.y2 = y2;
	}
}
