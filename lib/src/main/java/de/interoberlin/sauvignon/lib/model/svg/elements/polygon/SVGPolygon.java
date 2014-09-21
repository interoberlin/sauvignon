package de.interoberlin.sauvignon.lib.model.svg.elements.polygon;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.BoundingRect;
import de.interoberlin.sauvignon.lib.model.svg.elements.EElement;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

public class SVGPolygon extends AGeometric
{
	public static final EElement	type	= EElement.POLYGON;
	private List<Vector2>			points	= new ArrayList<Vector2>();
	private EFillRule				fillRule;

	// -------------------------
	// Methods
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public SVGPolygon clone()
	{
		SVGPolygon clone = new SVGPolygon();

		List<Vector2> points = new ArrayList<Vector2>();
		for (Vector2 p : this.getPoints())
		{
			points.add(p.clone());
		}

		clone.setPoints(points);
		clone.setFillRule(this.getFillRule());

		return clone;
	}

	public SVGPolygon applyCTM(Matrix ctm)
	{
		SVGPolygon p = new SVGPolygon();

		List<Vector2> newPoints = new ArrayList<Vector2>();

		for (Vector2 point : points)
		{
			Vector2 newPoint = point.applyCTM(ctm);
			newPoints.add(newPoint);
		}

		p.setPoints(newPoints);

		return p;
	}

	public SVGPolygon applyCTM()
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

	public EFillRule getFillRule()
	{
		return fillRule;
	}

	public void setFillRule(EFillRule fillRule)
	{
		this.fillRule = fillRule;
	}

}