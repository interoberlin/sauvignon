package de.interoberlin.sauvignon.model.smil;

import de.interoberlin.sauvignon.model.svg.transform.ATransformOperator;

public class Animate implements IAnimatable
{
	private String					attributeName	= "";
	private EAnimateTransformType	type;

	private String					from			= "";
	private String					to				= "";
	private String					begin			= "";
	private String					dur				= "";
	private String					repeatCount		= "";

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

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getBegin()
	{
		return begin;
	}

	public void setBegin(String begin)
	{
		this.begin = begin;
	}

	public String getDur()
	{
		return dur;
	}

	public void setDur(String dur)
	{
		this.dur = dur;
	}

	public String getRepeatCount()
	{
		return repeatCount;
	}

	public void setRepeatCount(String repeatCount)
	{
		this.repeatCount = repeatCount;
	}

	@Override
	public ATransformOperator getTransformOperator(long millisSinceStart)
	{
		// TODO Auto-generated method stub
		return null;
	}
}