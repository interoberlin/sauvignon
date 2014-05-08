package de.interoberlin.sauvignon.model.svg.elements;

public class SVGEllipse extends AGeometric
{
	public static final String	name	= "ellipse";
	public final EElement		type	= EElement.ELLIPSE;

	private float				cx;
	private float				cy;
	private float				rx;
	private float				ry;

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

	public float getRx()
	{
		return rx;
	}

	public void setRx(float rx)
	{
		this.rx = rx;
	}

	public float getRy()
	{
		return ry;
	}

	public void setRy(float ry)
	{
		this.ry = ry;
	}
}
