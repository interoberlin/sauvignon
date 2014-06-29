package de.interoberlin.sauvignon.model.svg.elements;

import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class BoundingRect
{
	Vector2	upperLeft;
	Vector2	lowerRight;
	Vector2	center;

	public BoundingRect()
	{
	}

	public BoundingRect(Vector2 upperLeft, Vector2 lowerRight)
	{
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
		this.center = new Vector2((upperLeft.getX() + lowerRight.getX()) / 2, (upperLeft.getY() + lowerRight.getY()) / 2);
	}

	public BoundingRect applyMatrix(Matrix m)
	{
		BoundingRect n  = new BoundingRect();
		
		n.setUpperLeft(getUpperLeft().applyCTM(m));
		n.setLowerRight(getLowerRight().applyCTM(m));
		n.setCenter(getCenter().applyCTM(m));
		
		return n;
	}

	public Vector2 getUpperLeft()
	{
		return upperLeft;
	}

	public void setUpperLeft(Vector2 upperLeft)
	{
		this.upperLeft = upperLeft;
	}

	public Vector2 getLowerRight()
	{
		return lowerRight;
	}

	public void setLowerRight(Vector2 lowerRight)
	{
		this.lowerRight = lowerRight;
	}

	public Vector2 getBottomRight()
	{
		return new Vector2(right, bottom);
	}
	
	public Vector2 getCenter()
	{
		return center;
	}

	public void setCenter(Vector2 center)
	{
		this.center = center;
	}
}
