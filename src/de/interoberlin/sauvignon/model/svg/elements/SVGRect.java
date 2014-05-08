package de.interoberlin.sauvignon.model.svg.elements;


public class SVGRect extends AGeometric
{
	private static final String	name	= "rect";
	private final EElement		type	= EElement.RECT;

	private float				width	= 0;
	private float				height	= 0;
	private float				x		= 0;
	private float				y		= 0;
	private float				rx		= 0;
	private float				ry		= 0;

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		if (width >= 0)
		{
			this.width = width;
		}
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		if (height >= 0)
		{
			this.height = height;
		}
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		if (x >= 0)
		{
			this.x = x;
		}
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		if (y >= 0)
		{
			this.y = y;
		}
	}

	public float getRx()
	{
		return rx;
	}

	public void setRx(float rx)
	{
		if (rx > 0)
		{
			this.rx = rx;
		}
	}

	public float getRy()
	{
		return ry;
	}

	public void setRy(float ry)
	{
		if (ry > 0)
		{
			this.ry = ry;
		}
	}
}