package de.interoberlin.sauvignon.model.svg.elements;

import java.util.List;

import de.interoberlin.sauvignon.model.util.Vector2;


public class SVGPath extends AGeometric
{
	private static final String	name	= "path";
	private final EElement		type	= EElement.PATH;

	private int					sides;
	private float				cx;
	private float				cy;
	private float				r1;
	private float				r2;
	private float				arg1;
	private float				arg2;
	private boolean				flatsided;
	private int					randomized;
	private SVGPathData			d;
	private float				transformCenterX;
	private float				transformCenterY;

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}

	public int getSides()
	{
		return sides;
	}

	public void setSides(int sides)
	{
		this.sides = sides;
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

	public float getR1()
	{
		return r1;
	}

	public void setR1(float r1)
	{
		this.r1 = r1;
	}

	public float getR2()
	{
		return r2;
	}

	public void setR2(float r2)
	{
		this.r2 = r2;
	}

	public float getArg1()
	{
		return arg1;
	}

	public void setArg1(float arg1)
	{
		this.arg1 = arg1;
	}

	public float getArg2()
	{
		return arg2;
	}

	public void setArg2(float arg2)
	{
		this.arg2 = arg2;
	}

	public boolean isFlatsided()
	{
		return flatsided;
	}

	public void setFlatsided(boolean flatsided)
	{
		this.flatsided = flatsided;
	}

	public int getRandomized()
	{
		return randomized;
	}

	public void setRandomized(int randomized)
	{
		this.randomized = randomized;
	}

	public void setD(SVGPathData d)
	{
		this.d = d;
	}

	public SVGPathData getD()
	{
		return d;
	}

	public void importData(String d)
	{
		this.d.importData(d);
	}
	
	public String exportData()
	{
		return this.d.exportData();
	}
	
	public float getTransformCenterX()
	{
		return transformCenterX;
	}

	public void setTransformCenterX(float transformCenterX)
	{
		this.transformCenterX = transformCenterX;
	}

	public float getTransformCenterY()
	{
		return transformCenterY;
	}

	public void setTransformCenterY(float transformCenterY)
	{
		this.transformCenterY = transformCenterY;
	}

}
