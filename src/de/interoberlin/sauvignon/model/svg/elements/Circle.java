package de.interoberlin.sauvignon.model.svg.elements;


public class Circle extends AGeometric
{
	public static final String	name	= "circle";
	public final EElement		type	= EElement.CIRCLE;

	private int					cx;
	private int					cy;
	private int					r;

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}

	public int getCx()
	{
		return cx;
	}

	public void setCx(int cx)
	{
		this.cx = cx;
	}

	public int getCy()
	{
		return cy;
	}

	public void setCy(int cy)
	{
		this.cy = cy;
	}

	public int getR()
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

}
