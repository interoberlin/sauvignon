package de.interoberlin.sauvignon.model.svg.elements.path;

import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SvgPathLineto extends SVGPathSegment
{
	public SvgPathLineto()
	{
		setSegmentType(ESVGPathSegmentType.LINETO);
	}
	
	public void makeAbsolute(Vector2 cursor)
	{
		if (getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
		{
			setXY(false, getXY(false).add(cursor));
			setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
		}
		cursor.set(getXY(false));
	}

	public void applyCTM(Matrix CTM)
	{
		setXY(true, getXY(false).applyCTM(CTM));
	}
	
	public float getX(boolean transformed)
	{
		return getNumber(transformed ? 2 : 0);
	}
	
	public float getY(boolean transformed)
	{
		return getNumber(transformed ? 3 : 1);
	}
	
	public Vector2 getXY(boolean transformed)
	{
		return new Vector2(getX(transformed), getY(transformed));
	}
	
	public void setXY(boolean transformed, Vector2 newXY)
	{
		setNumber(transformed ? 2 : 0, newXY.getX());
		setNumber(transformed ? 3 : 1, newXY.getY());
	}
}
