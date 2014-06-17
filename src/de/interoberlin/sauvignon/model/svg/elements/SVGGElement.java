package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

public class SVGGElement extends AGeometric
{
	public static final EElement	type		= EElement.G;

	private List<AElement>			subelements	= new ArrayList<AElement>();

	public EElement getType()
	{
		return type;
	}

	public List<AElement> getSubelements()
	{
		return subelements;
	}

	public void setSubelements(List<AElement> subelements)
	{
		this.subelements = subelements;
	}

	public List<AElement> getAllSubElements()
	{
		List<AElement> allSubelements = new ArrayList<AElement>();

		// Iterate over direct subelements
		for (AElement e : getSubelements())
		{
			allSubelements.add(e);

			if (e.getType() == EElement.G)
			{
				allSubelements.addAll(((SVGGElement) e).getAllSubElements());
			}
		}

		return allSubelements;
	}

	public void addSubelement(AElement element)
	{
		subelements.add(element);
	}
}
