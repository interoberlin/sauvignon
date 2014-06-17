package de.interoberlin.sauvignon.model.svg.elements;

import de.interoberlin.sauvignon.model.util.Vector2;

public class BoundingRect
{
	float	left;
	float	top;
	float	right;
	float	bottom;
	Vector2	center;

	public BoundingRect()
	{
		this(0, 0, 0, 0);
	}

	public BoundingRect(float left, float top, float right, float bottom)
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.center = new Vector2((left + right) / 2, (top + bottom) / 2);
	}

	public float getLeft()
	{
		return left;
	}

	public void setLeft(float left)
	{
		this.left = left;
	}

	public float getTop()
	{
		return top;
	}

	public void setTop(float top)
	{
		this.top = top;
	}

	public float getRight()
	{
		return right;
	}

	public void setRight(float right)
	{
		this.right = right;
	}

	public float getBottom()
	{
		return bottom;
	}

	public void setBottom(float bottom)
	{
		this.bottom = bottom;
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
