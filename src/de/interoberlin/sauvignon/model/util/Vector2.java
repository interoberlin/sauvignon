package de.interoberlin.sauvignon.model.util;

public class Vector2
{
	private Double	x;
	private Double	y;

	public Vector2(Vector2 v)
	{
		this.setX(v.getX());
		this.setY(v.getY());
	}

	public Vector2(Double x, Double y)
	{
		this.setX(x);
		this.setY(y);
	}

	public Vector2()
	{
		this.setX(0.0d);
		this.setY(0.0d);
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

	public Double getX()
	{
		return x;
	}

	public void setX(Double x)
	{
		this.x = x;
	}

	public Double getY()
	{
		return y;
	}

	public void setY(Double y)
	{
		this.y = y;
	}
}
