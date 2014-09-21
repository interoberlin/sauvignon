package de.interoberlin.sauvignon.lib.model.svg.elements.rect;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SVGRect extends AGeometric
{
	public static final EElement	type		= EElement.RECT;

	private float					x			= 0;
	private float					y			= 0;
	private float					width		= 0;
	private float					height		= 0;

	private Vector2 upperLeft	= new Vector2();
	private Vector2					upperRight	= new Vector2();
	private Vector2					lowerLeft	= new Vector2();
	private Vector2					lowerRight	= new Vector2();

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

		clone.setUpperLeft(this.getUpperLeft().clone());
		clone.setUpperRight(this.getUpperRight().clone());
		clone.setLowerLeft(this.getLowerLeft().clone());
		clone.setLowerRight(this.getLowerRight().clone());

		return clone;
	}

	public BoundingRect getBoundingRect()
	{
		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
	}

	public SVGRect applyMatrix(Matrix ctm)
	{
		SVGRect n = new SVGRect();

		n.setUpperLeft(this.getUpperLeft().applyCTM(ctm));
		n.setUpperRight(this.getUpperRight().applyCTM(ctm));
		n.setLowerLeft(this.getLowerLeft().applyCTM(ctm));
		n.setLowerRight(this.getLowerRight().applyCTM(ctm));

		return n;
	}

	public SVGRect applyCTM()
	{
		return applyMatrix(getCTM());
	}

	public void updateCorners()
	{
		setUpperLeft(new Vector2(x, y));
		setUpperRight(new Vector2(x + width, y));
		setLowerLeft(new Vector2(x, y + height));
		setLowerRight(new Vector2(x + width, y + height));
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
		updateCorners();
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
		updateCorners();
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
		updateCorners();
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
		updateCorners();
	}

	public Vector2 getUpperLeft()
	{
		return upperLeft;
	}

	public void setUpperLeft(Vector2 upperLeft)
	{
		this.upperLeft = upperLeft;
	}

	public Vector2 getUpperRight()
	{
		return upperRight;
	}

	public void setUpperRight(Vector2 upperRight)
	{
		this.upperRight = upperRight;
	}

	public Vector2 getLowerLeft()
	{
		return lowerLeft;
	}

	public void setLowerLeft(Vector2 lowerLeft)
	{
		this.lowerLeft = lowerLeft;
	}

	public Vector2 getLowerRight()
	{
		return lowerRight;
	}

	public void setLowerRight(Vector2 lowerRight)
	{
		this.lowerRight = lowerRight;
	}
}
