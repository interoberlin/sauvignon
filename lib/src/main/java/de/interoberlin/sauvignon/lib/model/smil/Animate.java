package de.interoberlin.sauvignon.lib.model.smil;

public class Animate extends AAnimate
{
	private EAttributeName attributeName;
	private EAnimateTransformType type;

	// -------------------------
	// Getters / Setter
	// -------------------------

	public EAttributeName getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(EAttributeName attributeName)
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