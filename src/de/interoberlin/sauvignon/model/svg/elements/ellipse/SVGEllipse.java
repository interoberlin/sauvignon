package de.interoberlin.sauvignon.model.svg.elements.ellipse;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGEllipse extends AGeometric
{
	public static final String	name	= "ellipse";
	public final EElement		type	= EElement.ELLIPSE;

	private float	cx = 0f;
	private float	cy = 0f;
	private float	rx = 1f;
	private float	ry = 1f;
	

	public void applyCTM()
	{
		Matrix CTM = getCTM();
		setCTM(new Matrix());
		
		Vector2 newCenter = new Vector2(cx,cy).applyCTM(CTM);
		cx = newCenter.getX();
		cy = newCenter.getY();
		
		Vector2 newRadii = new Vector2(rx,ry).applyCTM(CTM); 
		rx = newRadii.getX();
		ry = newRadii.getY();
	}
	
	public BoundingRect getBoundingRect()
	{
		float left = (cx - rx);
		float top = (cy - ry);
		float right = (cx + rx);
		float bottom = (cy + ry);

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
