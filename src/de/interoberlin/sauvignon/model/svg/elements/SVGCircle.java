package de.interoberlin.sauvignon.model.svg.elements;


public class SVGCircle extends AGeometric
{
	public static final String	name	= "circle";
	public final EElement		type	= EElement.CIRCLE;

	private float					cx;
	private float					cy;
	private float					r;

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}

	public float getCx()
	{
		return cx;
	}

	public void setCx(float cx)
	{
		this.cx = cx;
	}

	public float getCy()
	{
		return cy;
	}

	public void setCy(float cy)
	{
		this.cy = cy;
	}

	public float getR()
	{
		return r;
	}

	public void setR(float r)
	{
		this.r = r;
	}

}
