package de.interoberlin.sauvignon.model.svg;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.EElement;
import de.interoberlin.sauvignon.model.svg.elements.SVGGElement;
import de.interoberlin.sauvignon.model.svg.meta.Defs;
import de.interoberlin.sauvignon.model.svg.meta.Metadata;
import de.interoberlin.sauvignon.model.svg.transform.geometric.SVGTransformScale;
import de.interoberlin.sauvignon.model.util.Matrix;

/**
 * Represents the complex type svgType
 * 
 * @author Florian
 * 
 */
public class SVG extends AGeometric
{
	private static String		name			= "svg";

	private String				xmlns_dc;
	private String				xmlns_cc;
	private String				xmlns_rdf;
	private String				xmlns_svg;
	private String				xmlns;
	private String				version;
	private float				width;
	private float				height;
	private String				id;
	private Defs				defs;
	private Metadata			metadata;

	private EScaleMode			canvasScaleMode	= EScaleMode.DEFAULT;
	private Matrix				CTM				= new Matrix();
	private Matrix				scaleMatrix		= new Matrix();

	private List<AGeometric>	subelements		= new ArrayList<AGeometric>();
	private boolean				changed			= true;

	public static String getName()
	{
		return name;
	}

	public String getXmlNs_dc()
	{
		return xmlns_dc;
	}

	public void setXmlNs_dc(String xmlns_dc)
	{
		this.xmlns_dc = xmlns_dc;
	}

	public String getXmlns_cc()
	{
		return xmlns_cc;
	}

	public void setXmlNs_cc(String xmlns_cc)
	{
		this.xmlns_cc = xmlns_cc;
	}

	public String getXmlNs_rdf()
	{
		return xmlns_rdf;
	}

	public void setXmlNs_rdf(String xmlns_rdf)
	{
		this.xmlns_rdf = xmlns_rdf;
	}

	public String getXmlns_svg()
	{
		return xmlns_svg;
	}

	public void setXmlNs_svg(String xmlns_svg)
	{
		this.xmlns_svg = xmlns_svg;
	}

	public String getXmlNs()
	{
		return xmlns;
	}

	public void setXmlNs(String xmlns)
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

	public List<AGeometric> getSubelements()
	{
		return subelements;
	}

	public void setSubelements(List<AGeometric> subelements)
	{
		this.subelements = subelements;
	}

	public List<AGeometric> getAllSubElements()
	{
		List<AGeometric> allSubelements = new ArrayList<AGeometric>();

		// Iterate over direct subelements
		for (AGeometric e : getSubelements())
		{
			allSubelements.add(e);

			if (e.getType() == EElement.G)
			{
				allSubelements.addAll(((SVGGElement) e).getAllSubElements());
			}
		}

		return allSubelements;
	}

	public void addSubelement(AGeometric element)
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
		for (AGeometric element : getAllSubElements())
		{
			element.mustUpdateCTM();
		}
	}

	public void setScaleMatrix(Matrix scaleMatrix)
	{
		this.scaleMatrix = scaleMatrix;
	}

	public Matrix getScaleMatrix()
	{
		return scaleMatrix;
	}

	public void scaleTo(float newWidth, float newHeight)
	{
		float ratioX = newWidth / getWidth();
		float ratioY = newHeight / getHeight();

		float scaleX = 1;
		float scaleY = 1;

		switch (canvasScaleMode)
		{
			case DEFAULT:
			{
				break;
			}
			case FILL:
			{
				if (ratioX > ratioY)
				{
					scaleX = ratioX;
					scaleY = ratioX;
				} else
				{
					scaleX = ratioY;
					scaleY = ratioY;
				}
				break;
			}
			case FIT:
			{
				if (ratioX > ratioY)
				{
					scaleX = ratioY;
					scaleY = ratioY;
				} else
				{
					scaleX = ratioX;
					scaleY = ratioX;
				}
				break;
			}
			case STRETCH:
			{
				scaleX = ratioX;
				scaleY = ratioY;
				break;
			}
		}

		setScaleMatrix(new SVGTransformScale(scaleX, scaleY).getResultingMatrix());

		if (ratioX != 1 || ratioY != 1)
		{
			setCTM(getCTM().multiply(getScaleMatrix()));
//			if (ratioX != 1)
//				setWidth(getWidth() * ratioX);
//			if (ratioY != 1)
//				setHeight(getHeight() * ratioY);
		}
	}

	public boolean isChanged()
	{
		return changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}
}
