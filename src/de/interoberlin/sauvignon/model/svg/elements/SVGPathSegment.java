package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

/*
 * http://www.w3.org/TR/SVG11/paths.html
 */

/**
 * An SVG Path Segment consists of a command char, of which some are followed by
 * one or more numbers
 */
public class SVGPathSegment
{
	private SVGPathSegmentType				segmentType;
	private SVGPathSegmentCoordinateType	coordinateType;
	private List<Float>						numbers	= new ArrayList<Float>();

	public SVGPathSegmentType getSegmentType()
	{
		return segmentType;
	}

	public void setSegmentType(SVGPathSegmentType segmentType)
	{
		this.segmentType = segmentType;
	}

	public SVGPathSegmentCoordinateType getCoordinateType()
	{
		return coordinateType;
	}

	public void setCoordinateType(SVGPathSegmentCoordinateType coordinateType)
	{
		this.coordinateType = coordinateType;
	}

	public List<Float> getNumbers()
	{
		return numbers;
	}

	public void setNumbers(List<Float> numbers)
	{
		this.numbers = numbers;
	}
	
	public void addNumber(float number)
	{
		numbers.add(number);
	}
}
