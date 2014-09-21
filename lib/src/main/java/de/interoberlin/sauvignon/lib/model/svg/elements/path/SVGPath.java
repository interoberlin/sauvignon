package de.interoberlin.sauvignon.lib.model.svg.elements.path;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */
public class SVGPath extends AGeometric
{
	public static final EElement	type	= EElement.PATH;
	private List<SVGPathSegment>	d		= new ArrayList<SVGPathSegment>();

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public SVGPath applyCTM(Matrix ctm)
	{
		SVGPath p = new SVGPath();

		List<SVGPathSegment> newD = new ArrayList<SVGPathSegment>();

		for (SVGPathSegment segment : d)
		{
			SVGPathSegment newSegment = segment.applyCTM(ctm);
			newSegment.setCoordinateType(segment.getCoordinateType());
			newSegment.setSegmentType(segment.getSegmentType());

			newD.add(newSegment);
		}

		p.setD(newD);

		return p;
	}

	public SVGPath applyCTM()
	{
		return applyCTM(getCTM());
	}

	public BoundingRect getBoundingRect()
	{
		Float left = null;
		Float top = null;
		Float right = null;
		Float bottom = null;

		for (SVGPathSegment s : d)
		{
			BoundingRect br = s.getBoundingRect();

			if (left == null || br.getUpperLeft().getX() < left)
				left = br.getUpperLeft().getX();
			if (top == null || br.getUpperLeft().getY() < top)
				top = br.getUpperLeft().getY();
			if (right == null || br.getLowerRight().getX() > right)
				right = br.getLowerRight().getX();
			if (bottom == null || br.getLowerRight().getY() > bottom)
				bottom = br.getLowerRight().getY();
		}

		Vector2 upperLeft = new Vector2(left, top);
		Vector2 upperRight = new Vector2(right, top);
		Vector2 lowerLeft = new Vector2(left, bottom);
		Vector2 lowerRight = new Vector2(right, bottom);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
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
}