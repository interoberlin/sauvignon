package de.interoberlin.sauvignon.model.svg.meta;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.svg.elements.EPatternUnits;
import de.interoberlin.sauvignon.model.svg.elements.SVGGElement;
import de.interoberlin.sauvignon.model.util.Vector2;

public class Pattern
{
	private static String	name		= "pattern";

	private String			id;
	private float			x;
	private float			y;
	private float			width;
	private float			height;
	private EPatternUnits	patternUnits;
	private List<Vector2>	viewBox;
	private List<AElement>	subelements	= new ArrayList<AElement>();

	public static String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public EPatternUnits getPatternUnits()
	{
		return patternUnits;
	}

	public void setPatternUnits(EPatternUnits patternUnits)
	{
		this.patternUnits = patternUnits;
	}

	public List<Vector2> getViewBox()
	{
		return viewBox;
	}

	public void setViewBox(List<Vector2> viewBox)
	{
		this.viewBox = viewBox;
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

	public AElement getElementById(String id)
	{
		for (AElement e : getAllSubElements())
		{
			if (e.getId().equals(id))
			{
				return e;
			}
		}

		return null;
	}
}
