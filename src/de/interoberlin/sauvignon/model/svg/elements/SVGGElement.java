package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

public class SVGGElement extends AElement
{
	private static String	name		= "g";
	private EElement		type		= EElement.SVGGElement;

	private String			transform;
	private List<AElement>	subelements	= new ArrayList<AElement>();

	public static String getName()
	{
		return name;
	}

	public EElement getType()
	{
		return type;
	}

	public void setType(EElement type)
	{
		this.type = type;
	}

	public String getTransform()
	{
		return transform;
	}

	public void setTransform(String transform)
	{
		this.transform = transform;
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
			if (e.getType() == EElement.SVGGElement)
			{
				allSubelements.addAll(((SVGGElement) e).getAllSubElements());
			} else
			{
				allSubelements.add(e);
			}
		}

		return allSubelements;
	}
}
