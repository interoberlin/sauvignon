package de.interoberlin.sauvignon.model.svg.elements.path;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Vector2;

/**
 * http://www.w3.org/TR/SVG11/paths.html
 */
public class SVGPath extends AGeometric
{
	public static final EElement	type		= EElement.PATH;

	private List<SVGPathSegment>	d			= new ArrayList<SVGPathSegment>();
	private boolean					absolute	= false;

	public EElement getType()
	{
		return type;
	}
	
	public BoundingRect getBoundingRect()
	{
		float left = 0;
		float top = 0;
		float right = 0;
		float bottom = 0;

		// for (SVGPathSegment s : d)
		// {
		// BoundingRect br = s.getBoundingRect();
		//
		// if (br.getLeft() < left)
		// left = br.getLeft();
		// if (br.getTop() < top)
		// top = br.getTop();
		// if (br.getRight() > right)
		// right = br.getRight();
		// if (br.getBottom() > bottom)
		// bottom = br.getBottom();
		// }

		return new BoundingRect(left, top, right, bottom);
	}

	public void addAbsoluteMoveTo(Vector2 v)
	{
		SvgPathMoveto seg = new SvgPathMoveto();
		seg.setXY(false, v);
		d.add(seg);
	}

	public void addAbsoluteLineTo(Vector2 v)
	{
		SvgPathLineto seg = new SvgPathLineto();
		seg.setXY(false, v);
		d.add(seg);
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
	
	public boolean isAbsolute()
	{
		return absolute;
	}

	public void makeAbsolute()
	{
		if (isAbsolute())
			return;
		
		Vector2 cursor = new Vector2();
		for (SVGPathSegment segment : d)
		{
			switch (segment.getSegmentType())
			{
				case MOVETO:
					((SvgPathMoveto) segment).makeAbsolute(cursor);
					break;
				case LINETO:
					((SvgPathLineto) segment).makeAbsolute(cursor);
					break;
				case CURVETO_CUBIC:
					((SvgPathCurvetoCubic) segment).makeAbsolute(cursor);
					break;
				case CURVETO_QUADRATIC:
					((SvgPathCurvetoQuadratic) segment).makeAbsolute(cursor);
					break;
				default:
					break;
			}
		}
		absolute = true;
	}

	public void applyCTM()
	{
		for (SVGPathSegment segment : d)
		{
			switch (segment.getSegmentType())
			{
				case MOVETO:
					((SvgPathMoveto) segment).applyCTM(getCTM());
					break;
				case LINETO:
					((SvgPathLineto) segment).applyCTM(getCTM());
					break;
				case CURVETO_CUBIC:
					((SvgPathCurvetoCubic) segment).applyCTM(getCTM());
					break;
				case CURVETO_QUADRATIC:
					((SvgPathCurvetoQuadratic) segment).applyCTM(getCTM());
					break;
				default:
					break;
			}

		}
	}
}