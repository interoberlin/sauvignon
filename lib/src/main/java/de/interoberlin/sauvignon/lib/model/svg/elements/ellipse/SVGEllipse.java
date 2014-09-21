package de.interoberlin.sauvignon.lib.model.svg.elements.ellipse;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SVGEllipse extends AGeometric
{
	public static final EElement	type	= EElement.ELLIPSE;

	private float					cx		= 0f;
	private float					cy		= 0f;
	private float					rx		= 1f;
	private float					ry		= 1f;

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public BoundingRect getBoundingRect()
	{
		Vector2 upperLeft = new Vector2(cx - rx, cy - ry);
		Vector2 upperRight = new Vector2(cx + rx, cy - ry);
		Vector2 lowerLeft = new Vector2(cx - rx, cy + ry);
		Vector2 lowerRight = new Vector2(cx + rx, cy + ry);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
	}

	public SVGEllipse applyCTM(Matrix ctm)
	{
		SVGEllipse n = new SVGEllipse();

		Vector2 newCenter = new Vector2(cx, cy).applyCTM(ctm);
		n.setCenter(newCenter);

		Vector2 newRadii = new Vector2(rx, ry).applyCTM(ctm);
		n.setRadii(newRadii);

		return n;
	}

	public SVGEllipse applyCTM()
	{
		return applyCTM(getCTM());
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

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

	public void setCenter(Vector2 c)
	{
		cx = c.getX();
		cy = c.getY();
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

	public void setRadii(Vector2 r)
	{
		rx = r.getX();
		ry = r.getY();
	}
}
