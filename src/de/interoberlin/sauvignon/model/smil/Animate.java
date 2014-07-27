package de.interoberlin.sauvignon.model.smil;

public class Animate extends AAnimate
{
	private String					attributeName	= "";
	private EAnimateTransformType	type;

	// -------------------------
	// Getters / Setter
	// -------------------------

	public String getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	public EAnimateTransformType getType()
	{
		return type;
	}

	public void setType(EAnimateTransformType type)
	{
		this.type = type;
	}
}