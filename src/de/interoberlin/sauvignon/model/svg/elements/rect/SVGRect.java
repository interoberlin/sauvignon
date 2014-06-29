package de.interoberlin.sauvignon.model.svg.elements.rect;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGRect extends AGeometric
{
	public static final EElement	type	= EElement.RECT;

	private float					width	= 0;
	private float					height	= 0;
	private float					x		= 0;
	private float					y		= 0;

	public EElement getType()
	{
		return type;
	}

	public BoundingRect getBoundingRect()
	{
		Vector2 topLeft = new Vector2(x, y);
		Vector2 lowerRight = new Vector2(x + width, y + height);

		return new BoundingRect(topLeft, lowerRight);
	}

	public SVGRect applyCTM(Matrix ctm)
	{
		SVGRect n = new SVGRect();

		Vector2 xy = (new Vector2(x, y)).applyCTM(ctm);
		n.setX( xy.getX() );
		n.setY( xy.getY() );

		Vector2 wh = (new Vector2(width, height)).applyCTM(ctm);
		n.setWidth( wh.getX() );
		n.setHeight( wh.getY() );
		
		return n;
	}

	public SVGRect applyCTM()
	{
		return applyCTM(getCTM());
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
}
