package de.interoberlin.sauvignon.model.svg.elements.path;

import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgPathCurvetoQuadratic extends SVGPathSegment
{
	public SvgPathCurvetoQuadratic()
	{
		setSegmentType(ESVGPathSegmentType.CURVETO_QUADRATIC);
	}
	
	public void makeAbsolute(Vector2 cursor)
	{
		if (getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
		{
			setC(false, getC(false).add(cursor));
			setEnd(false, getEnd(false).add(cursor));
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set(getEnd(false));
	}
	
	public void applyCTM(Matrix CTM)
	{
		setC(true, getC(false).applyCTM(CTM));
		setEnd(true, getEnd(false).applyCTM(CTM));
	}
	
	public float getCX(boolean transformed)
	{
		return getNumber(transformed ? 4 : 0);
	}
	
	public float getCY(boolean transformed)
	{
		return getNumber(transformed ? 5 : 1);
	}

	public Vector2 getC(boolean transformed)
	{
		return new Vector2(getCX(transformed), getCY(transformed));
	}
	
	public void setC(boolean transformed, Vector2 newC)
	{
		setNumber(transformed ? 4 : 0, newC.getX());
		setNumber(transformed ? 5 : 1, newC.getY());
	}
	
	public float getEndX(boolean transformed)
	{
		return getNumber(transformed ? 6 : 2);
	}
	
	public float getEndY(boolean transformed)
	{
		return getNumber(transformed ? 7 : 3);
	}
	
	public Vector2 getEnd(boolean transformed)
	{
		return new Vector2(getEndX(transformed), getEndY(transformed));
	}
	
	public void setEnd(boolean transformed, Vector2 newEnd)
	{
		setNumber(transformed ? 6 : 2, newEnd.getX());
		setNumber(transformed ? 7 : 3, newEnd.getY());
	}
}
