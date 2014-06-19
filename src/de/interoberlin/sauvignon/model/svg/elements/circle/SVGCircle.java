package de.interoberlin.sauvignon.model.svg.elements.circle;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGCircle extends AGeometric
{
	public static final EElement		type	= EElement.CIRCLE;

	private float	cx;
	private float	cy;
	private float	r;

	public EElement getType()
	{
		return type;
	}
	
	public SVGCircle applyCTM()
	{
		SVGCircle n = new SVGCircle();
		
		Vector2 newCenter = new Vector2(cx, cy).applyCTM(getCTM());
		n.setCenter(newCenter);
		
		Vector2 newRadii = new Vector2(r, r).applyCTM(getCTM());
		n.setRadius((newRadii.getX()+newRadii.getY())/2);
		
		return n;
	}

	public BoundingRect getBoundingRect()
	{
		float left = (cx - r);
		float top = (cy - r);
		float right = (cx + r);
		float bottom = (cy + r);

		return new BoundingRect(left, top, right, bottom);
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

	public void setCenter(Vector2 newC)
	{
		cx = newC.getX();
		cy = newC.getY(); 
	}
	
	public float getRadius()
	{
		return r;
	}

	public void setRadius(float r)
	{
		this.r = r;
	}
}