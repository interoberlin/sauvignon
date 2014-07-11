package de.interoberlin.sauvignon.model.util;

public class Vector2
{
	private Float	x;
	private Float	y;

	public Vector2(Vector2 v)
	{
		this.setX(v.getX());
		this.setY(v.getY());
	}

	public Vector2(Float x, Float y)
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

	public Vector2 add(Vector2 v)
	{
		this.x += v.getX();
		this.y += v.getY();
		return this;
	}

	public Float getX()
	{
		return x;
	}

	public void setX(Float x)
	{
		this.x = x;
	}

	public Float getY()
	{
		return y;
	}

	public void setY(Float y)
	{
		this.y = y;
	}
	
	public float getLength()
	{
		return (float) Math.sqrt(Math.pow(getX(),2) + Math.pow(getY(),2));
	}
	
	public Vector2 minus(Vector2 v)
	{
		float dx = getX() - v.getX();
		float dy = getY() - v.getY();
		return new Vector2(dx, dy);
	}
	
	public Vector2 scale(Float factor)
	{
		return new Vector2(x*factor, y*factor);
	}
	
	public Vector2 scale(Float factorX, Float factorY)
	{
		return new Vector2(x*factorX, y*factorY);
	}
	
	public Vector2 applyCTM(Matrix CTM)
	{
		return CTM.multiply(this);
	}
}
