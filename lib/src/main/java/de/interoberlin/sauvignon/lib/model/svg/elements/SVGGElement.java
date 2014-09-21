package de.interoberlin.sauvignon.lib.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

public class SVGGElement extends AGeometric
{
	public static final EElement	type		= EElement.G;
	private List<AGeometric>		subelements	= new ArrayList<AGeometric>();

	// -------------------------
	// Getters / Setters
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	// public BoundingRect getBoundingRect()
	// {
	// float left = 0;
	// float top = 0;
	// float right = 0;
	// float bottom = 0;
	//
	// for (AGeometric element : getSubelements())
	// {
	// BoundingRect br = element.getBoundingRect();
	//
	// if (br.getUpperLeft().getX() < left)
	// left = br.getUpperLeft().getX();
	//
	// if (br.getUpperLeft().getY() < top)
	// top = br.getUpperLeft().getY();
	//
	// if (br.getLowerRight().getX() > right)
	// right = br.getLowerRight().getX();
	//
	// if (br.getLowerRight().getY() > bottom)
	// bottom = br.getLowerRight().getY();
	// }
	//
	// Vector2 upperLeft = new Vector2(left, top);
	// Vector2 lowerRight = new Vector2(right, bottom);
	//
	// return new BoundingRect(upperLeft, lowerRight);
	// }

	public List<AGeometric> getSubelements()
	{
		return subelements;
	}

	public void setSubelements(List<AGeometric> subelements)
	{
		this.subelements = subelements;
	}

	public List<AGeometric> getAllSubElements()
	{
		List<AGeometric> allSubelements = new ArrayList<AGeometric>();

		// Iterate over direct subelements
		for (AGeometric e : getSubelements())
		{
			allSubelements.add(e);

			if (e.getType() == EElement.G)
			{
				allSubelements.addAll(((SVGGElement) e).getAllSubElements());
			}
		}

		return allSubelements;
	}

	public void addSubelement(AGeometric element)
	{
		subelements.add(element);
	}
}