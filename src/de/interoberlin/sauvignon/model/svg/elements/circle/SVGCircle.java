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
	
	public void applyCTM()
	{
		Matrix CTM = getCTM();
		setCTM(new Matrix());
		
		Vector2 newCenter = new Vector2(cx, cy).applyCTM(CTM);
		cx = newCenter.getX();
		cy = newCenter.getY();
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

	public float getR()
	{
		return r;
	}

	public void setR(float r)
	{
		this.r = r;
	}
}