package de.interoberlin.sauvignon.model.smil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.Paint;
import de.interoberlin.sauvignon.model.svg.transform.color.ColorOperator;
import de.interoberlin.sauvignon.model.svg.transform.color.EColorOperatorType;
import de.interoberlin.sauvignon.model.util.ColorName;

/**
 * Color transformation over time
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateColorElement
 */
public class AnimateColor extends AAnimate
{
	private String			attributeName	= "";

	private EFill			fill;
	private List<String>	values			= new ArrayList<String>();

	// -------------------------
	// Methods
	// -------------------------

	public ColorOperator getColorOperator(long millisSinceStart)
	{
		long millisBegin = Long.parseLong(getBegin().replaceAll("[^\\d.]", "")) * 1000;
		long millisDur = Long.parseLong(getDur().replaceAll("[^\\d.]", "")) * 1000;
		long repeatCount = super.getRepeatCount().equals("indefinite") ? -1 : Long.parseLong(getRepeatCount());

		SVGColor from = parsePaint(getFrom());
		SVGColor to = parsePaint(getTo());

		// Case #1 not yet started
		if (millisSinceStart < millisBegin)
			return null;

		// Case #2 already finished
		if (!getRepeatCount().equals("indefinite") && millisSinceStart > millisBegin + (millisDur * repeatCount) || (repeatCount <= 0 && repeatCount != -1))
			return null;

		// Case #3 rendering
		float millisSinceLoopStart = (millisSinceStart - millisBegin) % millisDur;

		float redM = (to.getR() - from.getR()) / (float) millisDur;
		float redValue = redM * millisSinceLoopStart + from.getR();

		float greenM = (to.getG() - from.getG()) / (float) millisDur;
		float greenValue = greenM * millisSinceLoopStart + from.getG();

		float blueM = (to.getB() - from.getB()) / (float) millisDur;
		float blueValue = blueM * millisSinceLoopStart + from.getB();

		Paint p = new Paint();
		p.setARGB(255, (int) redValue, (int) greenValue, (int) blueValue);

		EColorOperatorType type = attributeName.equals("fill") ? EColorOperatorType.FILL : EColorOperatorType.STROKE;

		return new ColorOperator(type, p);
	}

	private SVGColor parsePaint(String paintString)
	{
		// Set defaults
		if (paintString == null)
		{
			paintString = "#000000";
		}

		int colorR = 0;
		int colorG = 0;
		int colorB = 0;

		// Check format
		if (paintString.charAt(0) == '#')
		{
			if (paintString.length() == 4)
			{
				paintString = "" + paintString.charAt(0) + paintString.charAt(0) + paintString.charAt(1) + paintString.charAt(1) + paintString.charAt(2) + paintString.charAt(2);

			}

			colorR = Integer.parseInt(paintString.substring(1, 3), 16);
			colorG = Integer.parseInt(paintString.substring(3, 5), 16);
			colorB = Integer.parseInt(paintString.substring(5, 7), 16);
		} else if (paintString.startsWith("rgb(") && paintString.endsWith(")"))
		{
			paintString = paintString.replace("rgb(", "");
			paintString = paintString.replace(")", "");
			paintString = paintString.replace(" ", "");
			String[] pArray = paintString.split(",");
			List<String> p = new ArrayList<String>(Arrays.asList(pArray));

			colorR = Integer.parseInt(p.get(0));
			colorG = Integer.parseInt(p.get(1));
			colorB = Integer.parseInt(p.get(2));
		} else
		{
			paintString = ColorName.getColorByName(paintString);

			if (paintString == null)
			{
				paintString = "#FFFFFF";
			}

			colorR = Integer.parseInt(paintString.substring(1, 3), 16);
			colorG = Integer.parseInt(paintString.substring(3, 5), 16);
			colorB = Integer.parseInt(paintString.substring(5, 7), 16);
		}

		return new SVGColor(colorR, colorG, colorB);
	}

	private class SVGColor
	{
		private int	r;
		private int	g;
		private int	b;

		public SVGColor(int r, int g, int b)
		{
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public int getR()
		{
			return r;
		}

		public int getG()
		{
			return g;
		}

		public int getB()
		{
			return b;
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
