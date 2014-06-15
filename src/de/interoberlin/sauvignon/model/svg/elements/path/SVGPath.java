package de.interoberlin.sauvignon.model.svg.elements.path;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Vector2;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */
public class SVGPath extends AGeometric
{
	private static final String		name	= "path";
	private final EElement			type	= EElement.PATH;
	private List<SVGPathSegment>	d		= new ArrayList<SVGPathSegment>();
	
	public void addAbsoluteMoveTo(Vector2 v)
	{
		d.add(new SVGPathSegment(ESVGPathSegmentType.MOVETO, ESVGPathSegmentCoordinateType.ABSOLUTE, v.getX(), v.getY()));
	}

	public void addAbsoluteLineTo(Vector2 v)
	{
		d.add(new SVGPathSegment(ESVGPathSegmentType.LINETO, ESVGPathSegmentCoordinateType.ABSOLUTE, v.getX(), v.getY()));
	}

	public void addD(SVGPathSegment s)
	{
		d.add(s);
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public List<SVGPathSegment> getD()
	{
		return d;
	}

	public void setD(List<SVGPathSegment> d)
	{
		this.d = d;
	}

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}
}