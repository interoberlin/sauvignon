package de.interoberlin.sauvignon.model.svg.elements.text;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;

public class SVGText extends AGeometric
{
	private float	x = 0f;
	private float	y = 0f;
	private String	text = "";
	
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
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
