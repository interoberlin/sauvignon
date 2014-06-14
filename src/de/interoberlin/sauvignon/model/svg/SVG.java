package de.interoberlin.sauvignon.model.svg;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.svg.elements.SVGGElement;
import de.interoberlin.sauvignon.model.svg.meta.Defs;
import de.interoberlin.sauvignon.model.svg.meta.Metadata;
import de.interoberlin.sauvignon.model.util.Matrix;

/**
 * Represents the complex type svgType
 * 
 * @author Florian
 * 
 */
public class SVG implements Transformable
{
	private static String	name			= "svg";

	private EScaleMode		canvasScaleMode	= EScaleMode.DEFAULT;
	private Matrix			CTM = new Matrix();

	private List<AElement>	subelements		= new ArrayList<AElement>();

	private String			xmlns_dc;
	private String			xmlns_cc;
	private String			xmlns_rdf;
	private String			xmlns_svg;
	private String			xmlns;
	private String			version;
	private float			width;
	private float			height;
	private String			id;
	private Defs			defs;
	private Metadata		metadata;

	public static String getName()
	{
		return name;
	}

	public String getXmlns_dc()
	{
		return xmlns_dc;
	}

	public void setXmlns_dc(String xmlns_dc)
	{
		this.xmlns_dc = xmlns_dc;
	}

	public String getXmlns_cc()
	{
		return xmlns_cc;
	}

	public void setXmlns_cc(String xmlns_cc)
	{
		this.xmlns_cc = xmlns_cc;
	}

	public String getXmlns_rdf()
	{
		return xmlns_rdf;
	}

	public void setXmlns_rdf(String xmlns_rdf)
	{
		this.xmlns_rdf = xmlns_rdf;
	}

	public String getXmlns_svg()
	{
		return xmlns_svg;
	}

	public void setXmlns_svg(String xmlns_svg)
	{
		this.xmlns_svg = xmlns_svg;
	}

	public String getXmlns()
	{
		return xmlns;
	}

	public void setXmlns(String xmlns)
	{
		this.xmlns = xmlns;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Defs getDefs()
	{
		return defs;
	}

	public void setDefs(Defs defs)
	{
		this.defs = defs;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
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

	public EScaleMode getCanvasScaleMode()
	{
		return canvasScaleMode;
	}

	public void setCanvasScaleMode(EScaleMode canvasScaleMode)
	{
		this.canvasScaleMode = canvasScaleMode;
	}

	public Matrix getCTM()
	{
		return CTM;
	}

	public void setCTM(Matrix CTM)
	{
		this.CTM = CTM;
	}
}
