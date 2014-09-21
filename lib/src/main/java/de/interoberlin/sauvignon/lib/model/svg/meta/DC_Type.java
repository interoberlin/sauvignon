package de.interoberlin.sauvignon.lib.model.svg.meta;

public class DC_Type
{
    private static String name = "dc:type";
    
    private String rdf_resource;

    public static String getName()
    {
	return name;
    }
    
    public String getRdf_resource()
    {
	return rdf_resource;
    }

    public void setRdf_resource(String rdf_resource)
    {
	this.rdf_resource = rdf_resource;
    }
}
