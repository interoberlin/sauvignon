package de.interoberlin.sauvignon.lib.model.svg.elements;


import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class BoundingRect
{
	Vector2 upperLeft;
	Vector2	upperRight;
	Vector2	lowerLeft;
	Vector2	lowerRight;
	Vector2	center;

	public BoundingRect()
	{
	}

	public BoundingRect(Vector2 upperLeft, Vector2 upperRight, Vector2 lowerLeft, Vector2 lowerRight)
	{
		this.upperLeft = upperLeft;
		this.upperRight = upperRight;
		this.lowerLeft = lowerLeft;
		this.lowerRight = lowerRight;

		float centerX = (upperLeft.getX() + upperRight.getX() + lowerLeft.getX() + lowerRight.getX()) / 4;
		float centerY = (upperLeft.getY() + upperRight.getY() + lowerLeft.getY() + lowerRight.getY()) / 4;
		this.center = new Vector2(centerX, centerY);
	}

	public BoundingRect applyMatrix(Matrix m)
	{
		BoundingRect n = new BoundingRect();

		n.setUpperLeft(getUpperLeft().applyCTM(m));
		n.setUpperRight(getUpperRight().applyCTM(m));
		n.setLowerLeft(getLowerLeft().applyCTM(m));
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

	public Vector2 getCenter()
	{
		return center;
	}

	public void setCenter(Vector2 center)
	{
		this.center = center;
	}
}
