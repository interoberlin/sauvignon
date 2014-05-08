package de.interoberlin.sauvignon.model.util;

public class Vector2
{
	private float	x;
	private float	y;

	public Vector2(float x, float y)
	{
		this.setX(x);
		this.setY(y);
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
