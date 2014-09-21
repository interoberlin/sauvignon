package de.interoberlin.sauvignon.lib.model.svg.meta;

public class Metadata
{
	private static String	name	= "metadata";

	private String			id;
	private RDF_RDF rdf_RDF;

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

	public RDF_RDF getRdf_RDF()
	{
		return rdf_RDF;
	}

	public void setRdf_RDF(RDF_RDF rdf_RDF)
	{
		this.rdf_RDF = rdf_RDF;
	}
}
