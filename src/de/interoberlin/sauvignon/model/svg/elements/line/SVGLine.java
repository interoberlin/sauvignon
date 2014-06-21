package de.interoberlin.sauvignon.model.svg.elements.line;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGLine extends AGeometric
{
	public static final EElement	type	= EElement.LINE;

	private float					x1		= 0;
	private float					y1		= 0;
	private float					x2		= 0;
	private float					y2		= 0;

	public EElement getType()
	{
		return type;
	}

	public BoundingRect getBoundingRect()
	{
		float left = (x1 < x2) ? x1 : x2;
		float top = (y1 < y2) ? y1 : y2;
		float right = (x1 > x2) ? x1 : x2;
		float bottom = (y1 < y2) ? y1 : y2;

		Vector2 upperLeft = new Vector2(left, top);
		Vector2 lowerRight = new Vector2(right, bottom);

		return new BoundingRect(upperLeft, lowerRight);
	}

	public void applyMatrixOnSelf(Matrix m)
	{
		Vector2 xy1 = new Vector2(x1, y1).applyCTM(m);
		this.setX1(xy1.getX());
		this.setY1(xy1.getY());

		Vector2 xy2 = new Vector2(x2, y2).applyCTM(m);
		this.setX2(xy2.getX());
		this.setY2(xy2.getY());
	}
	
	public SVGLine applyMatrix(Matrix m)
	{
		SVGLine n = new SVGLine();

		Vector2 xy1 = new Vector2(x1, y1).applyCTM(m);
		n.setX1(xy1.getX());
		n.setY1(xy1.getY());

		Vector2 xy2 = new Vector2(x2, y2).applyCTM(m);
		n.setX2(xy2.getX());
		n.setY2(xy2.getY());

		return n;
	}

	public SVGLine applyCTM()
	{
		return applyMatrix(getCTM());
	}

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
