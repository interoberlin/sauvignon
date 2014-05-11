package de.interoberlin.sauvignon.model.util;

public class Vector2
{
	private float	x;
	private float	y;

	public Vector2(Vector2 v)
	{
		this.setX(v.getX());
		this.setY(v.getY());
	}

	public Vector2(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}

	public Vector2()
	{
		this.setX(0.0f);
		this.setY(0.0f);
	}
	
	public void set(Vector2 v)
	{
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public void add(Vector2 v)
	{
		this.x += v.getX();
		this.y += v.getY();
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
