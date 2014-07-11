package de.interoberlin.sauvignon.model.svg.elements.circle;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGCircle extends AGeometric
{
	public static final EElement	type	= EElement.CIRCLE;

	private float					cx;
	private float					cy;
	private float					r;

	public EElement getType()
	{
		return type;
	}
	
	public SVGCircle applyCTM(Matrix ctm)
	{
		SVGCircle n = new SVGCircle();

		Vector2 newCenter = new Vector2(cx, cy).applyCTM(ctm);
		n.setCenter(newCenter);

		Vector2 newOrigin = new Vector2().applyCTM(ctm);
		Vector2 newTarget = new Vector2(r,0f).applyCTM(ctm);
		Vector2 differenceVector = newTarget.minus(newOrigin); 
		n.setRadius(differenceVector.getLength());

		return n;
	}

	public SVGCircle applyCTM()
	{
		return applyCTM(getCTM());
	}

	public BoundingRect getBoundingRect()
	{
		Vector2 upperLeft = new Vector2(cx - r, cy - r);
		Vector2 upperRight = new Vector2(cx + r, cy - r);
		Vector2 lowerLeft = new Vector2(cx - r, cy + r);
		Vector2 lowerRight = new Vector2(cx + r, cy + r);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
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