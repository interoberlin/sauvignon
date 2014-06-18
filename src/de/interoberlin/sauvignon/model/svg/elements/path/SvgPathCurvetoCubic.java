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
			setC1( getC1().add(cursor) );
			setC2( getC2().add(cursor) );
			setEnd( getEnd().add(cursor) );
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set(getEnd());
	}
	
	public void applyCTM(Matrix CTM)
	{
		setC1( getC1().applyCTM(CTM) );
		setC2( getC2().applyCTM(CTM) );
		setEnd( getEnd().applyCTM(CTM) );
	}
	
	public float getC1X()
	{
		return getNumber(0);
	}
	
	public float getC1Y()
	{
		return getNumber(1);
	}

	public Vector2 getC1()
	{
		return new Vector2(getC1X(), getC1Y());
	}
	
	public void setC1(Vector2 newC1)
	{
		setNumber(0, newC1.getX());
		setNumber(1, newC1.getY());
	}
	
	public float getC2X()
	{
		return getNumber(2);
	}
	
	public float getC2Y()
	{
		return getNumber(3);
	}

	public Vector2 getC2()
	{
		return new Vector2(getC2X(), getC2Y());
	}
	
	public void setC2(Vector2 newC2)
	{
		setNumber(2, newC2.getX());
		setNumber(3, newC2.getY());
	}
	
	public float getEndX()
	{
		return getNumber(4);
	}
	
	public float getEndY()
	{
		return getNumber(5);
	}
	
	public Vector2 getEnd()
	{
		return new Vector2(getEndX(), getEndY());
	}
	
	public void setEnd(Vector2 newEnd)
	{
		setNumber(4, newEnd.getX());
		setNumber(5, newEnd.getY());
	}
}
