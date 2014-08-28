package de.interoberlin.sauvignon.model.svg.elements.rect;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGRect extends AGeometric
{
	public static final EElement	type	= EElement.RECT;

	private float					width	= 0;
	private float					height	= 0;
	private float					x		= 0;
	private float					y		= 0;

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public SVGRect clone()
	{
		SVGRect clone = new SVGRect();

		clone.setWidth(this.getWidth());
		clone.setHeight(this.getHeight());
		clone.setX(this.getX());
		clone.setY(this.getY());

		return clone;
	}

	public BoundingRect getBoundingRect()
	{
		Vector2 upperLeft = new Vector2(x, y);
		Vector2 upperRight = new Vector2(x + width, y);
		Vector2 lowerLeft = new Vector2(x, y + height);
		Vector2 lowerRight = new Vector2(x + width, y + height);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
	}

	public SVGRect applyMatrix(Matrix ctm)
	{
		SVGRect n = new SVGRect();

		Vector2 upperLeft = (new Vector2(x, y)).applyCTM(ctm);
		n.setX(upperLeft.getX());
		n.setY(upperLeft.getY());

		Vector2 lowerRight = (new Vector2(x + width, y + height)).applyCTM(ctm);
		n.setWidth(lowerRight.getX() - upperLeft.getX());
		n.setHeight(lowerRight.getY() - upperLeft.getY());

		return n;
	}

	public SVGRect applyCTM()
	{
		return applyMatrix(getCTM());
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		if (width >= 0)
		{
			this.width = width;
		}
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		if (height >= 0)
		{
			this.height = height;
		}
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}
}
