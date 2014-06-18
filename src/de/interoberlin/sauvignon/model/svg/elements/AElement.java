package de.interoberlin.sauvignon.model.svg.elements;

public abstract class AElement
{
	public static EElement	type	= EElement.NONE;

	private String			id;
	private int				zIndex;

	public EElement getType()
	{
		return type;
	}

	public BoundingRect getBoundingRect()
	{
		return new BoundingRect();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getzIndex()
	{
		return zIndex;
	}

	public void setzIndex(int zIndex)
	{
		this.zIndex = zIndex;
	}

	public void mustUpdateCTM()
	{
		/*
		 * In case, this element is a geometric element (AGeometric),
		 * AGeometric's mustUpdateCTM will be used, otherwise do nothing.
		 */
	}
	
	public void wasRedrawn()
	{
		// like mustUpdateCTM
	}
}
