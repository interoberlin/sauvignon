package de.interoberlin.sauvignon.model.svg.elements;

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
		this.setId(id);
		this.setCx(c.getCx());
		this.setCy(c.getCy());
		this.setR(c.getR());

		this.setFill(c.getFill());
		this.setStroke(c.getStroke());
		this.setStrokeWidth(c.getStrokeWidth());
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
}
