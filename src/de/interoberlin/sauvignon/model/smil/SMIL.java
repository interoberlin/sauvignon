package de.interoberlin.sauvignon.model.smil;

/**
 * http://www.w3.org/TR/SMIL/
 * http://www.w3.org/TR/SVG/animate.html
 */
public class SMIL
{
	private String attributeType = "XML"; // or CSS
	private String xmlns = "http://www.w3.org/2000/svg";

	// Timing
	private float begin = 0, end = 0, dur = 0;
	private int repeatCount = 0; // disabled
	private boolean repeatIndefinite = false;
	private boolean running = false;
	
	// Attribute to animate
	private String attributeName = ""; // e.g. transform
	private String type = ""; // e.g. matrix, rotate, scale,skewx, skewy, translate
	private String from = "", by = "", to = "";
	
	public void start()
	{
		running = true;
		// animator.register(this);
	}
	
	public void stop()
	{
		running = false;
		// animator.unregister(this);
	}

	public boolean isRunning()
	{
		return running;
	}

	/**
	 * @param dt Time relative to start of animation
	 */
	public void update(float dt)
	{
		if (running) {
			// reflection ...
			if (attributeName == "transform")
			{
				if (attributeType == "rotate")
				{
					
				}
			}
		}
	}
	
	public String getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(String attributeType)
	{
		this.attributeType = attributeType;
	}

	public String getXmlNs()
	{
		return xmlns;
	}

	public void setXmlNs(String xmlns)
	{
		this.xmlns = xmlns;
	}

	public float getBegin()
	{
		return begin;
	}

	public void setBegin(float begin)
	{
		this.begin = begin;
	}

	public float getEnd()
	{
		return end;
	}

	public void setEnd(float end)
	{
		this.end = end;
		dur = end-begin;
	}

	public float getDur()
	{
		return dur;
	}

	public void setDur(float dur)
	{
		this.dur = dur;
		end = begin+dur;
	}

	public String getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
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

	public String getBy()
	{
		return by;
	}

	public void setBy(String by)
	{
		this.by = by;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public int getRepeatCount()
	{
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount)
	{
		this.repeatCount = repeatCount;
		if (repeatCount != -1)
			setRepeatIndefinite(false);
	}

	public boolean isRepeatIndefinite()
	{
		return repeatIndefinite;
	}

	public void setRepeatIndefinite(boolean repeatIndefinite)
	{
		this.repeatIndefinite = repeatIndefinite;
		if (repeatIndefinite)
			repeatCount = -1;
		else
			repeatCount = 0;
	}
}
