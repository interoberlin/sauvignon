package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.interoberlin.sauvignon.model.util.Vector2;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */
enum SVGPathSegmentType {
	MOVETO,
	LINETO,
	LINETO_HORIZONTAL,
	LINETO_VERTICAL,
	CLOSEPATH,
	CURVETO_CUBIC,
	CURVETO_CUBIC_SMOOTH,
	CURVETO_QUADRATIC,
	CURVETO_QUADRATIC_SMOOTH,
	ARC
}

enum SVGPathSegmentCoordinateType {
	ABSOLUTE,
	RELATIVE
}

/**
 * An SVG Path Segment consists of a command char,
 * of which some are followed by one or more numbers
 */
public class SVGPathSegment
{
	public SVGPathSegmentType				segmentType;
	public SVGPathSegmentCoordinateType		coordinateType;
	public List<Float>						numbers = new ArrayList<Float>();
	
	/**
	 * Parse a string containing one command char and (optionally) space separated numbers
	 */
	public void importSegment(String segment)
	{
		this.segmentType = SVGPathSegmentType.MOVETO;
		this.coordinateType = SVGPathSegmentCoordinateType.ABSOLUTE;
		this.numbers = Arrays.asList(100f, 150f);
	}
	
	/**
	 * Export this path segment to string
	 * @return
	 */
	public String exportSegment()
	{
		return "M 100 150";
	}
}
