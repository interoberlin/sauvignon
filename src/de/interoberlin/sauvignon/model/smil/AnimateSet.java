package de.interoberlin.sauvignon.model.smil;

import de.interoberlin.sauvignon.model.svg.transform.set.SetOperator;

/**
 * Set attribute value for specified duration
 * 
 * http://www.w3.org/TR/SVG/animate.html#SetElement
 */
public class AnimateSet extends AAnimate
{
	private EAttributeName	attributeName;

	private String			to			= "";
	private String			begin		= "";
	private String			dur			= "";
	private String			repeatDur	= "";
	private EFill			fill;

	// -------------------------
	// Methods
	// -------------------------

	public SetOperator getColorOperator(long millisSinceStart)
	{
		long millisBegin = Long.parseLong(getBegin().replaceAll("[^\\d.]", "")) * 1000;
		long millisDur = Long.parseLong(getDur().replaceAll("[^\\d.]", "")) * 1000;
		long repeatCount = super.getRepeatCount().equals("indefinite") ? -1 : Long.parseLong(getRepeatCount());

		float from = Float.parseFloat(getFrom());
		float to = Float.parseFloat(getTo());

		// Case #1 not yet started
		if (millisSinceStart < millisBegin)
			return null;

		// Case #2 already finished
		if (!getRepeatCount().equals("indefinite") && millisSinceStart > millisBegin + (millisDur * repeatCount) || (repeatCount <= 0 && repeatCount != -1))
			return null;

		// Case #3 rendering
		float millisSinceLoopStart = (millisSinceStart - millisBegin) % millisDur;

		float m = (to - from) / (float) millisDur;
		float value = m * millisSinceLoopStart + from;

		return new SetOperator(attributeName, value);
	}

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

	public String getRepeatDur()
	{
		return repeatDur;
	}

	public void setRepeatDur(String repeatDur)
	{
		this.repeatDur = repeatDur;
	}

	public EFill getFill()
	{
		return fill;
	}

	public void setFill(EFill fill)
	{
		this.fill = fill;
	}
}
