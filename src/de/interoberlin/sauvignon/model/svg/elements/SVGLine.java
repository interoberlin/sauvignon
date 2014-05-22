package de.interoberlin.sauvignon.model.svg.elements;

import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGLine extends AGeometric
{
	private static final String	name	= "line";
	private final EElement		type	= EElement.LINE;

	private float				x1		= 0;
	private float				y1		= 0;
	private float				x2		= 0;
	private float				y2		= 0;

	public SVGLine() {}
	
	public SVGLine(Vector2 from, Vector2 to)
	{
		x1 = from.getX();
		y1 = from.getY();
		x2 = to.getX();
		y2 = to.getY();
	}
	
	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
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
	
	public SVGLine applyCTM()
	{
		return new SVGLine(
					(new Vector2(x1,y1)).applyCTM(getCTM()),
					(new Vector2(x2,y2)).applyCTM(getCTM())
					);
	}
}
