package de.interoberlin.sauvignon.lib.model.svg.elements.path;

public enum ESVGPathSegmentType
{
	MOVETO(2),
	LINETO(2),
	LINETO_HORIZONTAL(1),
	LINETO_VERTICAL(1),
	CLOSEPATH(0),
	CURVETO_CUBIC(6),
	CURVETO_CUBIC_SMOOTH(4),
	CURVETO_QUADRATIC(4),
	CURVETO_QUADRATIC_SMOOTH(2),
	ARC(7);

	private int	parameterCount;

	ESVGPathSegmentType(int parameterCount)
	{
		this.setParameterCount(parameterCount);
	}

	public int getParameterCount()
	{
		return parameterCount;
	}

	public void setParameterCount(int parameterCount)
	{
		this.parameterCount = parameterCount;
	}
}
