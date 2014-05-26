package de.interoberlin.sauvignon.model.svg.elements.ellipse;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGEllipse extends AGeometric
{
	public static final String	name	= "ellipse";
	public final EElement		type	= EElement.ELLIPSE;

	private float				cx;
	private float				cy;
	private float				rx;
	private float				ry;

	public SVGEllipse()
	{
	}

	public SVGEllipse(Vector2 center, Vector2 radii)
	{
		cx = center.getX();
		cy = center.getY();
		rx = radii.getX();
		ry = radii.getY();
	}

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

	public SVGEllipse applyCTM()
	{
		this.cx = (new Vector2(cx, cy)).applyCTM(getCTM()).getX();
		this.cy = (new Vector2(cx, cy)).applyCTM(getCTM()).getY();
		this.rx = (new Vector2(rx, ry)).applyCTM(getCTM()).getX();
		this.ry = (new Vector2(rx, ry)).applyCTM(getCTM()).getY();

		return this;
	}
}
