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
			setC( getC().add(cursor) );
			setEnd( getEnd().add(cursor) );
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set(getEnd());
	}
	
	public void applyCTM(Matrix CTM)
	{
		setC( getC().applyCTM(CTM) );
		setEnd( getEnd().applyCTM(CTM) );
	}
	
	public float getCX()
	{
		return getNumber(0);
	}
	
	public float getCY()
	{
		return getNumber(1);
	}
	
	public Vector2 getC()
	{
		return new Vector2(getCX(), getCY());
	}
	
	public void setC(Vector2 newC)
	{
		setNumber(0, newC.getX());
		setNumber(1, newC.getY());
	}
	
	public float getEndX()
	{
		return getNumber(2);
	}
	
	public float getEndY()
	{
		return getNumber(3);
	}
	
	public Vector2 getEnd()
	{
		return new Vector2(getEndX(), getEndY());
	}
	
	public void setEnd(Vector2 newEnd)
	{
		setNumber(2, newEnd.getX());
		setNumber(3, newEnd.getY());
	}
}
