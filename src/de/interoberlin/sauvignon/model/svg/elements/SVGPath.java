package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */

public class SVGPath extends AGeometric
{
	private static final String	name	= "path";
	private final EElement		type	= EElement.PATH;
	private List<SVGPathSegment>	d	= new ArrayList<SVGPathSegment>();
	
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