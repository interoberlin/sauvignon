package de.interoberlin.sauvignon.model.svg.elements;

/**
 * Superclass of all tags in an svg file
 * @author Florian
 *
 */
public abstract class AElement
{
	public static EElement	type	= EElement.NONE;
	private String			id;

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
}
