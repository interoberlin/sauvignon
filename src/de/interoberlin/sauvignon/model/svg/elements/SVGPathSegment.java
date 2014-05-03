package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.util.Vector2;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */
enum SVGPathSegmentTypes {
	PATHSEG_MOVETO,
	PATHSEG_LINETO,
	PATHSEG_LINETO_HORIZONTAL,
	PATHSEG_LINETO_VERTICAL,
	PATHSEG_CLOSEPATH,
	PATHSEG_CURVETO_CUBIC,
	PATHSEG_CURVETO_CUBIC_SMOOTH,
	PATHSEG_CURVETO_QUADRATIC,
	PATHSEG_CURVETO_QUADRATIC_SMOOTH,
	PATHSEG_ARC
}

enum SVGPathSegmentCoordinateTypes {
	ABS,
	REL
}

public class SVGPathSegment
{
	private char				segmentType;
	private char				coordinateType;
	private List<Vector2>		numbers = new ArrayList<Vector2>();
}
