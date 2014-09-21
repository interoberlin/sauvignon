package de.interoberlin.sauvignon.lib.model.svg.meta;

public class Defs
{
	private static String	name	= "defs";
	private String			id;
	private Pattern			pattern;

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

	public Pattern getPattern()
	{
		return pattern;
	}

	public void setPattern(Pattern pattern)
	{
		this.pattern = pattern;
	}
}
