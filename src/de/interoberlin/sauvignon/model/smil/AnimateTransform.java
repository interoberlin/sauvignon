package de.interoberlin.sauvignon.model.smil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.transform.transform.ATransformOperator;
import de.interoberlin.sauvignon.model.svg.transform.transform.SVGTransformRotate;

/**
 * Animate transform attribute
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateTransformElement
 */
public class AnimateTransform extends AAnimate
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

		// Read from
		String[] fromArray = from.split(" ");
		List<String> fromList = new ArrayList<String>(Arrays.asList(fromArray));

		long fromValue = Long.parseLong(fromList.get(0));
		float fromX = 0;
		float fromY = 0;
		if (fromList.size() >= 3)
		{
			fromX = Float.parseFloat(fromList.get(1));
			fromY = Float.parseFloat(fromList.get(2));
		}

		// Read to
		String[] toArray = to.split(" ");
		List<String> toList = new ArrayList<String>(Arrays.asList(toArray));
		long toValue = Long.parseLong(toList.get(0));
		float toX = 0;
		float toY = 0;
		if (toList.size() >= 3)
		{
			toX = Float.parseFloat(toList.get(1));
			toY = Float.parseFloat(toList.get(2));
		}

		// Case #1 not yet started
		if (millisSinceStart < millisBegin)
			return null;

		// Case #2 already finished
		if (!this.repeatCount.equals("indefinite") && millisSinceStart > millisBegin + (millisDur * repeatCount) || (repeatCount <= 0 && repeatCount != -1))
			return null;

		// Case #3 rendering
		float millisSinceLoopStart = (millisSinceStart - millisBegin) % millisDur;

		float valueM = (toValue - fromValue) / (float) millisDur;
		float value = valueM * millisSinceLoopStart + fromValue;

		float xM = (toX - fromX) / (float) millisDur;
		float x = xM * millisSinceLoopStart + fromX;

		float yM = (toY - fromY) / (float) millisDur;
		float y = yM * millisSinceLoopStart + fromY;

		switch (type)
		{
			case ROTATE:
			{
				return new SVGTransformRotate(value, x, y);
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
