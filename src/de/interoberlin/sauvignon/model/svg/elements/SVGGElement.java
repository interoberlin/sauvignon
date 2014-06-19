package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

public class SVGGElement extends AGeometric
{
	public static final EElement	type		= EElement.G;

	private List<AGeometric>		subelements	= new ArrayList<AGeometric>();

	public EElement getType()
	{
		return type;
	}

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
