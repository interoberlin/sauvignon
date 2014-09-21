package de.interoberlin.sauvignon.lib.model.svg.meta;

public class RDF_RDF
{
	private static String	name	= "rdf:RDF";

	private CC_Work			cc_work;

	public static String getName()
	{
		return name;
	}

	public CC_Work getCc_work()
	{
		return cc_work;
	}

	public void setCc_work(CC_Work cc_work)
	{
		this.cc_work = cc_work;
	}

}
