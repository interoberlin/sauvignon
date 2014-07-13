package de.interoberlin.sauvignon.model.svg.elements.polyline;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

public class SVGPolyline extends AGeometric
{
	public static final EElement	type	= EElement.POLYLINE;
	public List<Vector2>			points	= new ArrayList<Vector2>();

	// -------------------------
	// Constructors
	// -------------------------

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public SVGPolyline applyCTM(Matrix ctm)
	{
		SVGPolyline p = new SVGPolyline();

		List<Vector2> newPoints = new ArrayList<Vector2>();

		for (Vector2 point : points)
		{
			Vector2 newPoint = point.applyCTM(ctm);
			newPoints.add(newPoint);
		}

		p.setPoints(newPoints);

		return p;
	}

	public SVGPolyline applyCTM()
	{
		return applyCTM(getCTM());
	}

	public BoundingRect getBoundingRect()
	{
		Float left = null;
		Float top = null;
		Float right = null;
		Float bottom = null;

		for (Vector2 p : points)
		{
			if (left == null || p.getX() < left)
				left = p.getX();
			if (top == null || p.getY() < top)
				top = p.getY();
			if (right == null || p.getX() > right)
				right = p.getX();
			if (bottom == null || p.getY() > bottom)
				bottom = p.getY();
		}

		Vector2 upperLeft = new Vector2(left, top);
		Vector2 upperRight = new Vector2(right, top);
		Vector2 lowerLeft = new Vector2(left, bottom);
		Vector2 lowerRight = new Vector2(right, bottom);

		return new BoundingRect(upperLeft, upperRight, lowerLeft, lowerRight);
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public List<Vector2> getPoints()
	{
		return points;
	}

	public void setPoints(List<Vector2> points)
	{
		this.points = points;
	}
}