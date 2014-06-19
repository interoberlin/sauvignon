package de.interoberlin.sauvignon.model.svg.elements.path;

import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgPathCurvetoCubic extends SVGPathSegment
{
	public SvgPathCurvetoCubic()
	{
		setSegmentType(ESVGPathSegmentType.CURVETO_CUBIC);
	}
	
	public void makeAbsolute(Vector2 cursor)
	{
		if (getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
		{
			setC1(false, getC1(false).add(cursor));
			setC2(false, getC2(false).add(cursor));
			setEnd(false, getEnd(false).add(cursor));
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set(getEnd(false));
	}
	
	public void applyCTM(Matrix CTM)
	{
		setC1(true, getC1(false).applyCTM(CTM));
		setC2(true, getC2(false).applyCTM(CTM));
		setEnd(true, getEnd(false).applyCTM(CTM));
	}
	
	public float getC1X(boolean transformed)
	{
		return getNumber(transformed ? 6 : 0);
	}
	
	public float getC1Y(boolean transformed)
	{
		return getNumber(transformed ? 7 : 1);
	}

	public Vector2 getC1(boolean transformed)
	{
		return new Vector2(getC1X(transformed), getC1Y(transformed));
	}
	
	public void setC1(boolean transformed, Vector2 newC1)
	{
		setNumber(transformed ? 6 : 0, newC1.getX());
		setNumber(transformed ? 7 : 1, newC1.getY());
	}
	
	public float getC2X(boolean transformed)
	{
		return getNumber(transformed ? 8 : 2);
	}
	
	public float getC2Y(boolean transformed)
	{
		return getNumber(transformed ? 9 : 3);
	}

	public Vector2 getC2(boolean transformed)
	{
		return new Vector2(getC2X(transformed), getC2Y(transformed));
	}
	
	public void setC2(boolean transformed, Vector2 newC2)
	{
		setNumber(transformed ? 8 : 2, newC2.getX());
		setNumber(transformed ? 9 : 3, newC2.getY());
	}
	
	public float getEndX(boolean transformed)
	{
		return getNumber(transformed ? 10 : 4);
	}
	
	public float getEndY(boolean transformed)
	{
		return getNumber(transformed ? 11 : 5);
	}
	
	public Vector2 getEnd(boolean transformed)
	{
		return new Vector2(getEndX(transformed), getEndY(transformed));
	}
	
	public void setEnd(boolean transformed, Vector2 newEnd)
	{
		setNumber(transformed ? 10 : 4, newEnd.getX());
		setNumber(transformed ? 11 : 5, newEnd.getY());
	}
}
