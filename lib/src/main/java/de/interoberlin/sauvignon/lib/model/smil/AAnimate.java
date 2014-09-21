package de.interoberlin.sauvignon.lib.model.smil;

public abstract class AAnimate
{
	private String	from		= "";
	private String	to			= "";
	private String	begin		= "";
	private String	dur			= "";
	private String	repeatCount	= "";

	// -------------------------
	// Getters / Setter
	// -------------------------

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
