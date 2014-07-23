package de.interoberlin.sauvignon.model.smil;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.transform.ATransformOperator;

/**
 * Color transformation over time
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateColorElement
 */
public class AnimateColor implements IAnimatable
{
	private String			attributeName	= "";

	private String			from			= "";
	private String			to				= "";
	private String			begin			= "";
	private String			dur				= "";
	private String			repeatCount		= "";
	private EFill			fill;
	private List<String>	values			= new ArrayList<String>();

	// -------------------------
	// Methods
	// -------------------------

	public ATransformOperator getTransformOperator(long millisSinceStart)
	{
		return null;
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

	public EFill getFill()
	{
		return fill;
	}

	public void setFill(EFill fill)
	{
		this.fill = fill;
	}

	public List<String> getValues()
	{
		return values;
	}

	public void setValues(List<String> values)
	{
		this.values = values;
	}

}
