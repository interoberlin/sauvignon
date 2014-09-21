package de.interoberlin.sauvignon.lib.model.svg.elements.path;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

/*
 * http://www.w3.org/TR/SVG11/paths.html
 */

/**
 * An SVG Path Segment consists of a command char, of which some are followed by
 * one or more numbers.
 * 
 * The coordinate type and number handling is done by this class, while segment
 * specific methods are defined in the segment subclasses.
 * 
 * SVGPathSegment can not be instantiated as such, since it would be unclear,
 * which applyCTM and makeAbsolute methods would apply.
 */
public class SVGPathSegment
{
	// Will be initialized by constructor of subclass
	private ESVGPathSegmentType				segmentType;
	private ESVGPathSegmentCoordinateType	coordinateType	= ESVGPathSegmentCoordinateType.ABSOLUTE;
	private List<Float>						numbers			= new ArrayList<Float>();
	private List<Vector2>					coordinates		= new ArrayList<Vector2>();
	private Vector2							cursor			= new Vector2();

	public SVGPathSegment applyCTM(Matrix CTM)
	{
		SVGPathSegment s = new SVGPathSegment();

		List<Vector2> newCoordinates = new ArrayList<Vector2>();

		for (Vector2 c : coordinates)
		{
			Vector2 newC = c.applyCTM(CTM);
			newCoordinates.add(newC);
		}

		s.setCoordinates(newCoordinates);

		return s;
	}

	public BoundingRect getBoundingRect()
	{
		Float left = null;
		Float top = null;
		Float right = null;
		Float bottom = null;

		for (Vector2 v : getCoordinates())
		{
			if (left == null || v.getX() < left)
				left = v.getX();
			if (top == null || v.getY() < top)
				top = v.getY();
			if (right == null || v.getX() > right)
				right = v.getX();
			if (bottom == null || v.getY() > bottom)
				bottom = v.getY();
		}
		
		Vector2 upperLeft = new Vector2(left, top);
		Vector2 upperRight = new Vector2(right, top);
		Vector2 lowerLeft = new Vector2(left, bottom);
		Vector2 lowerRight = new Vector2(right, bottom);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
	}

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

	public boolean isComplete()
	{
		return !(this.segmentType == null || this.coordinateType == null || numbers.size() != segmentType.getParameterCount());
	}

	public void addNumber(float number)
	{
		numbers.add(number);
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
		while (numbers.size() < index + 1)
			numbers.add(0f);
		numbers.set(index, number);
	}

	public void addCoordinate(Vector2 coordinate)
	{
		coordinates.add(coordinate);
	}

	public boolean hasCoordinates()
	{
		return coordinates.size() > 0;
	}

	public List<Vector2> getCoordinates()
	{
		return coordinates;
	}

	public Vector2 getCoordinate(int index)
	{
		return coordinates.get(index);
	}

	public void setCoordinates(List<Vector2> coordinates)
	{
		this.coordinates = coordinates;
	}

	public void setCoordinate(int index, Vector2 coordinate)
	{
		while (coordinates.size() < index + 1)
			coordinate.add(new Vector2());
		coordinates.set(index, coordinate);
	}

	public Vector2 getLastCoordinate()
	{
		return coordinates.get(coordinates.size() - 1);
	}

	public Vector2 getCursor()
	{
		return cursor;
	}

	public void setCursor(Vector2 cursor)
	{
		this.cursor = cursor;
	}
}
