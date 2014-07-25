package de.interoberlin.sauvignon.model.smil;

import de.interoberlin.sauvignon.model.svg.transform.geometric.ATransformOperator;
import de.interoberlin.sauvignon.model.svg.transform.geometric.SVGTransformRotate;

/**
 * Animate transform attribute
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateTransformElement
 */
public class AnimateTransform implements IAnimatable
{
	private String					attributeName	= "";
	private EAnimateTransformType	type;

	private String					from			= "";
	private String					to				= "";
	private String					begin			= "";
	private String					dur				= "";
	private String					repeatCount		= "";

	// -------------------------
	// Methods
	// -------------------------

	public ATransformOperator getTransformOperator(long millisSinceStart)
	{
		long millisBegin = Long.parseLong(this.begin.replaceAll("[^\\d.]", "")) * 1000;
		long millisDur = Long.parseLong(this.dur.replaceAll("[^\\d.]", "")) * 1000;
		long repeatCount = this.repeatCount.equals("indefinite") ? -1 : Long.parseLong(this.repeatCount);

		long from = Long.parseLong(this.from);
		long to = Long.parseLong(this.to);

		// Case #1 not yet started
		if (millisSinceStart < millisBegin)
			return null;

		// Case #2 already finished
		if (!this.repeatCount.equals("indefinite") && millisSinceStart > millisBegin + (millisDur * repeatCount) || (repeatCount <= 0 && repeatCount != -1))
			return null;

		// Case #3 rendering
		float millisSinceLoopStart = (millisSinceStart - millisBegin) % millisDur;
		float m = (to - from) / (float) millisDur;
		float value = m * millisSinceLoopStart + from;

		switch (type)
		{
			case ROTATE:
			{
				return new SVGTransformRotate(value);
			}
			default:
			{
				return null;
			}
		}
	}

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
}
