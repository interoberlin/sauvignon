package de.interoberlin.sauvignon.model.smil;

/**
 * Set attribute value for specified duration
 * 
 * http://www.w3.org/TR/SVG/animate.html#SetElement
 */
public class AnimateSet implements IAnimatable
{
	private String	attributeName	= "";

	private String	to				= "";
	private String	begin			= "";
	private String	dur				= "";
	private String	repeatDur		= "";
	private EFill	fill;

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
