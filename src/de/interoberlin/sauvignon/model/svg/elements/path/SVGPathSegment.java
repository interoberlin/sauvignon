package de.interoberlin.sauvignon.model.svg.elements.path;

import java.util.ArrayList;
import java.util.List;

/*
 * http://www.w3.org/TR/SVG11/paths.html
 */

/**
 * An SVG Path Segment consists of a command char,
 * of which some are followed by one or more numbers.
 * 
 * The coordinate type and number handling is done by this class,
 * while segment specific methods are defined in the segment subclasses.
 * 
 * SVGPathSegment can not be instantiated as such,
 * since it would be unclear, which applyCTM and makeAbsolute methods would apply.
 */
public abstract class SVGPathSegment
{
	private ESVGPathSegmentType				segmentType;		// will be initialized by constructor of subclass
	private ESVGPathSegmentCoordinateType	coordinateType		= ESVGPathSegmentCoordinateType.ABSOLUTE;
	private List<Float>						numbers				= new ArrayList<Float>();

	
	public ESVGPathSegmentType getSegmentType()
	{
		return segmentType;
	}

	public void setSegmentType(ESVGPathSegmentType segmentType)
	{
		this.segmentType = segmentType;
	}

	public ESVGPathSegmentCoordinateType getCoordinateType()
	{
		return coordinateType;
	}

	public void setCoordinateType(ESVGPathSegmentCoordinateType coordinateType)
	{
		this.coordinateType = coordinateType;
	}

	
	public void addNumber(float number)
	{
		numbers.add(number);
	}

	public boolean isComplete()
	{
		return !(this.segmentType == null || this.coordinateType == null || numbers.size() != segmentType.getParameterCount());
	}

	public boolean hasNumbers()
	{
		return numbers.size() > 0;
	}

	public List<Float> getNumbers()
	{
		return numbers;
	}

	public float getNumber(int index)
	{
		return numbers.get(index);
	}
	
	public void setNumbers(List<Float> numbers)
	{
		this.numbers = numbers;
	}
	
	public void setNumber(int index, float number)
	{
		while (numbers.size() < index+1)
			numbers.add(0f);
		numbers.set(index, number);
	}
}
