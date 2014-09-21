package de.interoberlin.sauvignon.lib.model.svg.meta;

public class CC_Work
{
    private static String name = "cc:Work";
    
    private String rdf_about;
    private String dc_format;
    private DC_Type dc_type;
    private String dc_title;

    public static String getName()
    {
	return name;
    }
    
    public String getRdf_about()
    {
	return rdf_about;
    }

    public void setRdf_about(String rdf_about)
    {
	this.rdf_about = rdf_about;
    }

    public String getDc_Format()
    {
	return dc_format;
    }

    public void setDc_Format(String dc_format)
    {
	this.dc_format = dc_format;
    }

    public DC_Type getDc_type()
    {
	return dc_type;
    }

    public void setDc_type(DC_Type dc_type)
    {
	this.dc_type = dc_type;
    }

    public String getDc_title()
    {
	return dc_title;
    }

    public void setDc_title(String dc_title)
    {
	this.dc_title = dc_title;
    }


}
