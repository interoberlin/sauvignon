package de.interoberlin.sauvignon.model.svg.elements.circle;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGCircle extends AGeometric implements Cloneable
{
	public static final String	name	= "circle";
	public final EElement		type	= EElement.CIRCLE;

	private float				cx;
	private float				cy;
	private float				r;

	// ---------------------------
	// Methods
	// ---------------------------

	public SVGCircle()
	{

	}

	public SVGCircle(SVGCircle c, String id)
	{
		this.setCx(c.getCx());
		this.setCy(c.getCy());
		this.setR(c.getR());

		this.setFill(c.getFill());
		this.setStroke(c.getStroke());
		this.setStrokeWidth(c.getStrokeWidth());

		this.setId(id);
	}

	public SVGCircle(Vector2 center, float radius)
	{
		cx = center.getX();
		cy = center.getY();
		r = radius;
	}

	// public void move(Vector2 dest, float step)
	// {
	// Vector2 current = new Vector2(cx, cy);
	// }

	// ---------------------------
	// Getters / Setters
	// ---------------------------

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

	public float getR()
	{
		return r;
	}

	public void setR(float r)
	{
		this.r = r;
	}

	public SVGCircle applyCTM()
	{
		this.cx = new Vector2(this.cx, this.cy).applyCTM(getCTM()).getX();
		this.cy = new Vector2(this.cx, this.cy).applyCTM(getCTM()).getY();

		return this;
	}
}
