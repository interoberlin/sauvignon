package de.interoberlin.sauvignon.model.svg.transform.set;

import de.interoberlin.sauvignon.model.smil.EAttributeName;

public class SetOperator
{
	private EAttributeName	attributeName;
	private float			value;

	public SetOperator(EAttributeName attributeName, float value)
	{
		this.setAttributeName(attributeName);
		this.setValue(value);
	}

	public EAttributeName getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(EAttributeName attributeName)
	{
		this.attributeName = attributeName;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

}