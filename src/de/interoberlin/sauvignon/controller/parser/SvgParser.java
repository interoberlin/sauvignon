package de.interoberlin.sauvignon.controller.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Xml;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.AElement;
import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.svg.elements.EPatternUnits;
import de.interoberlin.sauvignon.model.svg.elements.SVGGElement;
import de.interoberlin.sauvignon.model.svg.elements.circle.SVGCircle;
import de.interoberlin.sauvignon.model.svg.elements.ellipse.SVGEllipse;
import de.interoberlin.sauvignon.model.svg.elements.line.SVGLine;
import de.interoberlin.sauvignon.model.svg.elements.path.ESVGPathSegmentCoordinateType;
import de.interoberlin.sauvignon.model.svg.elements.path.ESVGPathSegmentType;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.model.svg.elements.path.SVGPathSegment;
import de.interoberlin.sauvignon.model.svg.elements.rect.SVGRect;
import de.interoberlin.sauvignon.model.svg.meta.CC_Work;
import de.interoberlin.sauvignon.model.svg.meta.DC_Type;
import de.interoberlin.sauvignon.model.svg.meta.Defs;
import de.interoberlin.sauvignon.model.svg.meta.Metadata;
import de.interoberlin.sauvignon.model.svg.meta.Pattern;
import de.interoberlin.sauvignon.model.svg.meta.RDF_RDF;
import de.interoberlin.sauvignon.model.svg.transform.SVGTransform;
import de.interoberlin.sauvignon.model.util.Vector2;

/**
 * Class to parse SVGs
 * 
 * @author Florian
 * 
 */
public class SvgParser
{
	private static SvgParser	instance;

	private SvgParser()
	{

	}

	public static SvgParser getInstance()
	{
		if (instance == null)
		{
			instance = new SvgParser();
		}

		return instance;
	}

	/**
	 * Parses svg file an returns an SVG element
	 * 
	 * @param in
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public SVG parse(InputStream in) throws XmlPullParserException, IOException
	{
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readSVG(parser);
		} finally
		{
			in.close();
		}
	}

	/**
	 * Returns a SVG element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private SVG readSVG(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		SVG svg = new SVG();

		String name = "";
		parser.require(XmlPullParser.START_TAG, null, SVG.getName());

		// Initialize attributes and subelements
		String xmlns_dc = "";
		String xmlns_cc = "";
		String xmlns_rdf = "";
		String xmlns_svg = "";
		String xmlns = "";
		String version = "";
		String width = "0";
		String height = "0";
		String id = "";
		Defs defs = null;
		Metadata metadata = null;
		List<AGeometric> subelements = new ArrayList<AGeometric>();

		// Read attributes
		xmlns_dc = parser.getAttributeValue(null, "dc");
		xmlns_cc = parser.getAttributeValue(null, "cc");
		xmlns_rdf = parser.getAttributeValue(null, "rdf");
		xmlns_svg = parser.getAttributeValue(null, "svg");
		xmlns = parser.getAttributeValue(null, "xmlns");
		version = parser.getAttributeValue(null, "version");
		width = parser.getAttributeValue(null, "width");
		height = parser.getAttributeValue(null, "height");
		id = parser.getAttributeValue(null, "id");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			// Starts by looking for the entry tag
			if (name.equals("defs"))
			{
				defs = (readDefs(parser));
			} else if (name.equals("metadata"))
			{
				metadata = (readMetadata(parser));
			} else if (name.equals("g"))
			{
				subelements.add(parseGroup(parser, svg));
			} else if (name.equals("rect"))
			{
				subelements.add(readRect(parser, svg));
			} else if (name.equals("circle"))
			{
				subelements.add(readCircle(parser, svg));
			} else if (name.equals("ellipse"))
			{
				subelements.add(readEllipse(parser, svg));
			} else if (name.equals("line"))
			{
				subelements.add(readLine(parser, svg));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, svg));
			} else
			{
				skip(parser);
			}
		}

		svg.setId(id);
		svg.setXmlNs_dc(xmlns_dc);
		svg.setXmlNs_cc(xmlns_cc);
		svg.setXmlNs_rdf(xmlns_rdf);
		svg.setXmlNs_svg(xmlns_svg);
		svg.setXmlNs(xmlns);
		svg.setVersion(version);
		svg.setWidth(Float.parseFloat(width));
		svg.setHeight(Float.parseFloat(height));
		svg.setDefs(defs);
		svg.setMetadata(metadata);
		svg.setSubelements(subelements);

		return svg;
	}

	/**
	 * Returns a Defs element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private Defs readDefs(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Defs.getName());

		// Initialize attributes and subelements
		String id = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("pattern"))
			{
				readPattern(parser);
			} else
			{
				skip(parser);
			}
		}

		Defs defs = new Defs();
		defs.setId(id);

		return defs;
	}

	/**
	 * Returns a Pattern element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private Defs readPattern(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Pattern.getName());

		// Initialize attributes and subelements
		String id = "";
		String x = "";
		String y = "";
		String width = "";
		String height = "";
		String patternUnits = "";
		String viewBox = "";
		List<AElement> subelements = new ArrayList<AElement>();

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		x = parser.getAttributeValue(null, "x");
		y = parser.getAttributeValue(null, "y");
		width = parser.getAttributeValue(null, "width");
		height = parser.getAttributeValue(null, "height");
		patternUnits = parser.getAttributeValue(null, "patternUnits");
		viewBox = parser.getAttributeValue(null, "viewBox");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("g"))
			{
				subelements.add(parseGroup(parser, null));
			} else if (name.equals("rect"))
			{
				subelements.add(readRect(parser, null));
			} else if (name.equals("circle"))
			{
				subelements.add(readCircle(parser, null));
			} else if (name.equals("ellipse"))
			{
				subelements.add(readEllipse(parser, null));
			} else if (name.equals("line"))
			{
				subelements.add(readLine(parser, null));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, null));
			} else
			{
				skip(parser);
			}

			Pattern p = new Pattern();

			if (id != null)
				p.setId(id);
			if (x != null)
				p.setX(Float.parseFloat(x));
			if (y != null)
				p.setY(Float.parseFloat(y));
			if (width != null)
				p.setWidth(Float.parseFloat(width));
			if (height != null)
				p.setHeight(Float.parseFloat(height));
			if (patternUnits != null)
			{
				if (patternUnits.equals("userSpaceOnUse"))
				{
					p.setPatternUnits(EPatternUnits.USER_SPACE_ON_USE);
				} else if (patternUnits.equals("userSpace"))
				{
					p.setPatternUnits(EPatternUnits.USER_SPACE);
				} else if (patternUnits.equals("objectBoundingBox"))
				{
					p.setPatternUnits(EPatternUnits.OBJECT_BOUNDING_BOX);
				}
			}
			if (viewBox != null)
				p.setViewBox(readCoordinates(viewBox));

		}

		Defs defs = new Defs();
		defs.setId(id);

		return defs;
	}

	/**
	 * Returns a Metadata element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private Metadata readMetadata(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Metadata.getName());

		// Initialize attributes and subelements
		String id = "";
		RDF_RDF rdf_RDF = null;

		// Read attributes
		id = parser.getAttributeValue(null, "id");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			// Starts by looking for the entry tag
			if (name.equals("rdf:RDF"))
			{
				rdf_RDF = (readRdf_RDF(parser));
			} else
			{
				skip(parser);
			}
		}

		Metadata metadata = new Metadata();
		metadata.setId(id);
		metadata.setRdf_RDF(rdf_RDF);

		return metadata;
	}

	/**
	 * Returns a RDF_RDF element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private RDF_RDF readRdf_RDF(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, RDF_RDF.getName());

		// Initialize attributes and subelements
		CC_Work cc_work = null;

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			// Starts by looking for the entry tag
			if (name.equals("cc:Work"))
			{
				cc_work = (readCC_Work(parser));
			} else
			{
				skip(parser);
			}
		}

		RDF_RDF rdf_RDF = new RDF_RDF();
		rdf_RDF.setCc_work(cc_work);

		return rdf_RDF;
	}

	/**
	 * Returns a CC_Work element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private CC_Work readCC_Work(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, CC_Work.getName());

		// Initialize attributes and subelements
		String rdf_about = "";
		String dc_format = "";
		DC_Type dc_type = null;
		String dc_title = "";

		// Read attributes
		rdf_about = parser.getAttributeValue("rdf", "about");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			// Starts by looking for the entry tag
			if (name.equals("dc:format"))
			{
				dc_format = readString(parser, "dc:format");
			} else if (name.equals("dc:type"))
			{
				dc_type = readDC_Type(parser);
			} else if (name.equals("dc:title"))
			{
				dc_title = readString(parser, "dc:title");
			} else
			{
				skip(parser);
			}
		}

		CC_Work cc_work = new CC_Work();
		cc_work.setRdf_about(rdf_about);
		cc_work.setDc_Format(dc_format);
		cc_work.setDc_type(dc_type);
		cc_work.setDc_title(dc_title);

		return cc_work;
	}

	/**
	 * Returns a CC_Work element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private DC_Type readDC_Type(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, DC_Type.getName());

		// Initialize attributes and subelements
		String rdf_resource = null;

		// Read attributes
		rdf_resource = parser.getAttributeValue("rdf", "resource");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			skip(parser);
		}

		DC_Type dc_type = new DC_Type();
		dc_type.setRdf_resource(rdf_resource);

		return dc_type;
	}

	/**
	 * Returns a G element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	@SuppressLint("DefaultLocale")
	private SVGGElement parseGroup(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGGElement.type.toString().toLowerCase(Locale.getDefault()));

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");

		// Create new element with these attributes
		SVGGElement g = new SVGGElement();

		if (id != null)
			g.setId(id);
		if (transform != null)
			g.setTransform(new SVGTransform(transform));

		if (parentElement != null)
			g.setParentElement(parentElement);

		List<AGeometric> subelements = new ArrayList<AGeometric>();

		// Parse group subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			// Starts by looking for the entry tag
			if (name.equals("g"))
			{
				subelements.add(parseGroup(parser, g));
			} else if (name.equals("rect"))
			{
				subelements.add(readRect(parser, g));
			} else if (name.equals("circle"))
			{
				subelements.add(readCircle(parser, g));
			} else if (name.equals("ellipse"))
			{
				subelements.add(readEllipse(parser, g));
			} else if (name.equals("line"))
			{
				subelements.add(readLine(parser, g));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, g));
			} else
			{
				skip(parser);
			}
		}

		g.setSubelements(subelements);
		return g;
	}

	/**
	 * Returns a Rect element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	@SuppressLint("DefaultLocale")
	private SVGRect readRect(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, SVGRect.type.toString().toLowerCase(Locale.getDefault()));

		// Initialize attributes and subelements
		String id = "";
		String transform = "";

		String x = "";
		String y = "";
		String width = "";
		String height = "";

		String fill = "";
		String opacity = "";
		String stroke = "";
		String style = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		transform = parser.getAttributeValue(null, "transform");

		x = parser.getAttributeValue(null, "x");
		y = parser.getAttributeValue(null, "y");
		width = parser.getAttributeValue(null, "width");
		height = parser.getAttributeValue(null, "height");

		style = parser.getAttributeValue(null, "style");
		fill = parser.getAttributeValue(null, "fill");
		opacity = parser.getAttributeValue(null, "opacity");
		stroke = parser.getAttributeValue(null, "stroke");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		SVGRect rect = new SVGRect();

		if (id != null)
			rect.setId(id);
		if (transform != null)
			rect.setTransform(new SVGTransform(transform));
		if (x != null)
			rect.setX(Float.parseFloat(x));
		if (y != null)
			rect.setY(Float.parseFloat(y));
		if (width != null)
			rect.setWidth(Float.parseFloat(width));
		if (height != null)
			rect.setHeight(Float.parseFloat(height));

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = getAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = getAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = getAttributeFromStyle(style, "stroke");
			}
		}

		rect.getStyle().setFill(readPaint(fill, opacity));
		rect.getStyle().setStroke(readPaint(stroke, opacity));

		if (parentElement != null)
			rect.setParentElement(parentElement);

		return rect;
	}

	/**
	 * Returns a Circle element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private SVGCircle readCircle(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, SVGCircle.type.toString().toLowerCase(Locale.getDefault()));

		// Initialize attributes and subelements
		String id = "";
		String transform = "";

		String cx = "";
		String cy = "";
		String r = "";

		String style = "";
		String fill = "";
		String opacity = "";
		String stroke = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		transform = parser.getAttributeValue(null, "transform");

		cx = parser.getAttributeValue(null, "cx");
		cy = parser.getAttributeValue(null, "cy");
		r = parser.getAttributeValue(null, "r");

		style = parser.getAttributeValue(null, "style");
		fill = parser.getAttributeValue(null, "fill");
		opacity = parser.getAttributeValue(null, "opacity");
		stroke = parser.getAttributeValue(null, "stroke");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		SVGCircle circle = new SVGCircle();

		if (id != null)
			circle.setId(id);
		if (transform != null)
			circle.setTransform(new SVGTransform(transform));
		if (cx != null)
			circle.setCx(Float.parseFloat(cx));
		if (cy != null)
			circle.setCy(Float.parseFloat(cy));
		if (r != null)
			circle.setRadius(Float.parseFloat(r));

		// Evaluate style
		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = getAttributeFromStyle(style, "opacity");
			}

			if (style.contains("fill"))
			{
				fill = getAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = getAttributeFromStyle(style, "stroke");
			}
		}

		circle.getStyle().setFill(readPaint(fill, opacity));
		circle.getStyle().setStroke(readPaint(stroke, opacity));
		if (parentElement != null)
			circle.setParentElement(parentElement);

		return circle;
	}

	/**
	 * Returns a Ellipse element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private SVGEllipse readEllipse(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, SVGEllipse.type.toString().toLowerCase(Locale.getDefault()));

		// Initialize attributes and subelements
		String id = "";
		String transform = "";

		String cx = "";
		String cy = "";
		String rx = "";
		String ry = "";

		String style = "";
		String fill = "";
		String opacity = "";
		String stroke = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		transform = parser.getAttributeValue(null, "transform");

		cx = parser.getAttributeValue(null, "cx");
		cy = parser.getAttributeValue(null, "cy");
		rx = parser.getAttributeValue(null, "rx");
		ry = parser.getAttributeValue(null, "ry");

		style = parser.getAttributeValue(null, "style");
		fill = parser.getAttributeValue(null, "fill");
		opacity = parser.getAttributeValue(null, "opacity");
		stroke = parser.getAttributeValue(null, "stroke");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		SVGEllipse ellipse = new SVGEllipse();

		if (id != null)
			ellipse.setId(id);
		if (transform != null)
			ellipse.setTransform(new SVGTransform(transform));
		if (cx != null)
			ellipse.setCx(Float.parseFloat(cx));
		if (cy != null)
			ellipse.setCy(Float.parseFloat(cy));
		if (rx != null)
			ellipse.setRx(Float.parseFloat(rx));
		if (ry != null)
			ellipse.setRy(Float.parseFloat(ry));

		// Evaluate style
		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = getAttributeFromStyle(style, "opacity");
			}

			if (style.contains("fill"))
			{
				fill = getAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = getAttributeFromStyle(style, "stroke");
			}
		}

		ellipse.getStyle().setFill(readPaint(fill, opacity));
		ellipse.getStyle().setStroke(readPaint(stroke, opacity));
		if (parentElement != null)
			ellipse.setParentElement(parentElement);

		return ellipse;
	}

	/**
	 * Returns a Line element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private SVGLine readLine(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, SVGLine.type.toString().toLowerCase(Locale.getDefault()));

		// Initialize attributes and subelements
		String id = "";
		String transform = "";

		String x1 = "";
		String y1 = "";
		String x2 = "";
		String y2 = "";

		String style = "";
		String fill = "";
		String opacity = "";
		String stroke = "";
		String strokeWidth = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		transform = parser.getAttributeValue(null, "transform");

		x1 = parser.getAttributeValue(null, "x1");
		y1 = parser.getAttributeValue(null, "y1");
		x2 = parser.getAttributeValue(null, "x2");
		y2 = parser.getAttributeValue(null, "y2");

		style = parser.getAttributeValue(null, "style");
		fill = parser.getAttributeValue(null, "fill");
		opacity = parser.getAttributeValue(null, "opacity");
		stroke = parser.getAttributeValue(null, "stroke");
		strokeWidth = parser.getAttributeValue(null, "stroke-width");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		SVGLine line = new SVGLine();

		if (id != null)
			line.setId(id);
		if (transform != null)
			line.setTransform(new SVGTransform(transform));
		if (x1 != null)
			line.setX1(Float.parseFloat(x1));
		if (y1 != null)
			line.setY1(Float.parseFloat(y1));
		if (x2 != null)
			line.setX2(Float.parseFloat(x2));
		if (y2 != null)
			line.setY2(Float.parseFloat(y2));

		// Evaluate style
		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = getAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = getAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = getAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = getAttributeFromStyle(style, "stroke-width");
			}
		}

		line.getStyle().setFill(readPaint(fill, opacity));
		line.getStyle().setStroke(readPaint(stroke, opacity));
		if (strokeWidth != null)
			line.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			line.setParentElement(parentElement);

		return line;
	}

	/**
	 * Returns a Path element
	 * 
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private SVGPath parsePath(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, SVGPath.type.toString().toLowerCase(Locale.getDefault()));

		// Initialize attributes and subelements
		String id = "";
		String d = "";
		String transform = "";

		String style;
		String fill;
		String opacity;
		String stroke;
		String strokeWidth = "";

		// Read attributes
		id = parser.getAttributeValue(null, "id");
		d = parser.getAttributeValue(null, "d");
		transform = parser.getAttributeValue(null, "transform");

		style = parser.getAttributeValue(null, "style");
		fill = parser.getAttributeValue(null, "fill");
		opacity = parser.getAttributeValue(null, "opacity");
		stroke = parser.getAttributeValue(null, "stroke");
		strokeWidth = parser.getAttributeValue(null, "stroke-width");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		SVGPath path = new SVGPath();

		if (id != null)
			path.setId(id);
		if (d != null)
			path.setD(readD(d));
		if (transform != null)
			path.setTransform(new SVGTransform(transform));

		// Evaluate style
		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = getAttributeFromStyle(style, "opacity");
			}

			if (style.contains("fill"))
			{
				fill = getAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = getAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = getAttributeFromStyle(style, "stroke-width");
			}
		}

		path.getStyle().setFill(readPaint(fill, opacity));
		path.getStyle().setStroke(readPaint(stroke, opacity));
		if (strokeWidth != null)
			path.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			path.setParentElement(parentElement);

		return path;
	}

	private List<SVGPathSegment> readD(String d)
	{
		List<SVGPathSegment> ds = new ArrayList<SVGPathSegment>();

		// Replace commas by spaces
		d = d.replace(",", " ");

		// Add space before and after letters
		d = d.replaceAll("[A-Z]|[a-z]", " $0 ").trim();

		// Remove superfluous whitespace
		while (d.contains("  "))
			d = d.replace("  ", " ");

		// Split string into space-separated entities
		String[] dArray = d.split(" ");
		List<String> dList = new ArrayList<String>(Arrays.asList(dArray));

		/*
		 * In case it doesn't start with a character, add numbers to a MOVETO
		 * segment.
		 */
		SVGPathSegment segment = new SVGPathSegment();
		ESVGPathSegmentType lastSegmentType = ESVGPathSegmentType.MOVETO;
		ESVGPathSegmentCoordinateType lastCoordinateType = ESVGPathSegmentCoordinateType.ABSOLUTE;

		Vector2 cursor = new Vector2();
		Vector2 startPoint = null;

		for (String s : dList)
		{
			Character firstChar = s.charAt(0);

			if (Character.isLetter(firstChar))
			{
				if (segment.hasNumbers() && !segment.isComplete())
				{
					/*
					 * The parser started to fill the previous segment with
					 * numbers but it was not filled with a sufficient amount of
					 * numbers required for this type of segment. The resulting
					 * segment is invalid and must be discarded. The path is
					 * likely to be broken at this point, abort parsing.
					 */
					return ds;
				}

				switch (Character.toUpperCase(firstChar))
				{
					case 'M':
						segment.setSegmentType(ESVGPathSegmentType.MOVETO);
						break;
					case 'L':
						segment.setSegmentType(ESVGPathSegmentType.LINETO);
						break;
					case 'H':
						segment.setSegmentType(ESVGPathSegmentType.LINETO_HORIZONTAL);
						break;
					case 'V':
						segment.setSegmentType(ESVGPathSegmentType.LINETO_VERTICAL);
						break;
					case 'Z':
						segment.setSegmentType(ESVGPathSegmentType.CLOSEPATH);
						break;
					case 'C':
						segment.setSegmentType(ESVGPathSegmentType.CURVETO_CUBIC);
						break;
					case 'S':
						segment.setSegmentType(ESVGPathSegmentType.CURVETO_CUBIC_SMOOTH);
						break;
					case 'Q':
						segment.setSegmentType(ESVGPathSegmentType.CURVETO_QUADRATIC);
						break;
					case 'T':
						segment.setSegmentType(ESVGPathSegmentType.CURVETO_QUADRATIC_SMOOTH);
						break;
					case 'A':
						segment.setSegmentType(ESVGPathSegmentType.ARC);
						break;
					default:
						// Stop parsing, if an invalid letter is encountered
						return ds;
				}

				if (!Character.isUpperCase(firstChar))
				{
					segment.setCoordinateType(ESVGPathSegmentCoordinateType.RELATIVE);
				} else
				{
					segment.setCoordinateType(ESVGPathSegmentCoordinateType.ABSOLUTE);
				}

			} else
			{
				// Not a letter, must be a float then
				segment.addNumber(Float.parseFloat(s));
			}

			// Check if segment has all the numbers it needs as parameters
			if (segment.isComplete())
			{
				// Remember segment and coordinate type
				lastSegmentType = segment.getSegmentType();
				lastCoordinateType = segment.getCoordinateType();

				segment.addCoordinate(cursor);

				// Convert numbers to coordinates
				switch (segment.getSegmentType())
				{
					case LINETO_HORIZONTAL:
					{
						Vector2 coordinate = new Vector2(segment.getNumbers().get(0), cursor.getY());
						if (startPoint == null)
							startPoint = new Vector2(coordinate.getX(), coordinate.getY());
						segment.addCoordinate(coordinate);
						break;
					}
					case LINETO_VERTICAL:
					{
						Vector2 coordinate = new Vector2(cursor.getX(), segment.getNumbers().get(0));
						if (startPoint == null)
							startPoint = new Vector2(coordinate.getX(), coordinate.getY());
						segment.addCoordinate(coordinate);
						break;
					}
					case CLOSEPATH:
					{
						segment.addCoordinate(startPoint);
						break;
					}
					default:
					{
						Vector2 coordinate = new Vector2();

						for (int i = 0; i < segment.getNumbers().size(); i++)
						{
							if (i % 2 == 0)
							{
								coordinate = new Vector2();
								coordinate.setX(segment.getNumber(i));
							} else
							{
								coordinate.setY(segment.getNumber(i));

								if (segment.getCoordinateType() == ESVGPathSegmentCoordinateType.RELATIVE)
								{
									coordinate.add(cursor);
								}

								if (startPoint == null)
									startPoint = new Vector2(coordinate.getX(), coordinate.getY());
								segment.addCoordinate(coordinate);
							}
						}

						break;
					}

				}

				// Add completed segment to path
				ds.add(segment);

				// Set cursor
				cursor.set(segment.getLastCoordinate());

				/*
				 * Create default path segment, where numbers can be added to,
				 * in case, no letter follows to explicitly specify, which kind
				 * of segment the next segment will be.
				 */
				if (lastSegmentType == ESVGPathSegmentType.MOVETO)
				{
					lastSegmentType = ESVGPathSegmentType.LINETO;
				}

				segment = new SVGPathSegment();
				segment.setSegmentType(lastSegmentType);
				segment.setCoordinateType(lastCoordinateType);
			}
		}

		return ds;
	}

	private String getAttributeFromStyle(String style, String attribute)
	{
		return style.replaceAll(".*" + attribute + ":", "").replaceAll(";.*", "");
	}

	private List<Vector2> readCoordinates(String s)
	{
		// Replace commas by spaces
		s = s.replace(",", " ");

		// Remove superfluous whitespaces
		s = s.replace("  ", " ").trim();

		// Split
		String[] sArray = s.split(" ");
		List<String> sList = new ArrayList<String>(Arrays.asList(sArray));
		List<Vector2> vList = new ArrayList<Vector2>();

		float x = 0.0f;
		float y = 0.0f;

		for (int i = 0; i < sList.size(); i++)
		{
			if (i % 2 == 0)
			{
				x = Float.parseFloat(sList.get(i));
			} else
			{
				y = Float.parseFloat(sList.get(i));

				vList.add(new Vector2(x, y));
			}
		}

		return vList;
	}

	/**
	 * @TODO FLO Put default values into res file
	 * @param paint
	 * @param opacity
	 * @return
	 */
	private Paint readPaint(String paint, String opacity)
	{
		// Set defaults
		if (paint == null)
		{
			paint = "#000000";
		}
		if (opacity == null)
		{
			opacity = "1";
		}
		if (paint.equals("none"))
		{
			paint = "#FFFFFF";
			opacity = "0";
		}

		// Check format
		if (paint.charAt(0) == '#')
		{
			if (paint.length() == 4)
			{
				paint = "" + paint.charAt(0) + paint.charAt(0) + paint.charAt(1) + paint.charAt(1) + paint.charAt(2) + paint.charAt(2);

			}
		} else if (paint.startsWith("rgb(") && paint.endsWith(")"))
		{
			// TODO implement this
		} else
		{
			// TODO Make this more clever

			if (paint.equals("black"))
			{
				paint = "#000000";
			} else if (paint.equals("red"))
			{
				paint = "#FF0000";
			} else if (paint.equals("blue"))
			{
				paint = "#0000FF";
			} else if (paint.equals("green"))
			{
				paint = "#00FF00";
			} else if (paint.equals("yellow"))
			{
				paint = "#FFFF00";
			} else if (paint.equals("white"))
			{
				paint = "#FFFFFF";
			}
		}

		int colorA = (int) (Float.parseFloat(opacity) * 255);
		int colorR = Integer.parseInt(paint.substring(1, 3), 16);
		int colorG = Integer.parseInt(paint.substring(3, 5), 16);
		int colorB = Integer.parseInt(paint.substring(5, 7), 16);

		Paint paintFill = new Paint();
		paintFill.setARGB(colorA, colorR, colorG, colorB);
		return paintFill;
	}

	/**
	 * Returns the title of a card
	 * 
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readString(XmlPullParser parser, String tag) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, null, tag);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, tag);
		return title;
	}

	/**
	 * Reads the value of a cell
	 * 
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	/**
	 * Skips a tag that does not fit
	 * 
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0)
		{
			switch (parser.next())
			{
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}
}