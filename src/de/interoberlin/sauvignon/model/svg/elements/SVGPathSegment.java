package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.Arrays;
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
	public SVGPathSegmentType			segmentType;
	public SVGPathSegmentCoordinateType	coordinateType;
	public List<Float>					numbers	= new ArrayList<Float>();
}
