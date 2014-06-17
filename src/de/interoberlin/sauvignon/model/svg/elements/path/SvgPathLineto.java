package de.interoberlin.sauvignon.model.svg.elements.path;

import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgPathLineto extends SVGPathSegment
{
	public void makeAbsolute(Vector2 cursor)
	{
		if (getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
		{
			setXY( getXY().add(cursor) );
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set( getXY() );
	}

	public void applyCTM(Matrix CTM)
	{
		setXY( getXY().applyCTM(CTM) );
	}
	
	public float getX()
	{
		return getNumber(0);
	}
	
	public float getY()
	{
		return getNumber(1);
	}
	
	public Vector2 getXY()
	{
		return new Vector2(getX(), getY());
	}
	
	public void setXY(Vector2 newXY)
	{
		setNumber(0, newXY.getX());
		setNumber(1, newXY.getY());
	}
}
