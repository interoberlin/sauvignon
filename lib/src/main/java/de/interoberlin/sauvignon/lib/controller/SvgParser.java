package de.interoberlin.sauvignon.lib.controller;

import android.annotation.SuppressLint;
import android.graphics.Paint.Cap;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.interoberlin.sauvignon.lib.model.smil.AAnimate;
import de.interoberlin.sauvignon.lib.model.smil.Animate;
import de.interoberlin.sauvignon.lib.model.smil.AnimateColor;
import de.interoberlin.sauvignon.lib.model.smil.AnimateSet;
import de.interoberlin.sauvignon.lib.model.smil.AnimateTransform;
import de.interoberlin.sauvignon.lib.model.smil.EAnimateTransformType;
import de.interoberlin.sauvignon.lib.model.smil.EAttributeName;
import de.interoberlin.sauvignon.lib.model.smil.EFill;
import de.interoberlin.sauvignon.lib.model.svg.SVG;
import de.interoberlin.sauvignon.lib.model.svg.elements.AElement;
import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.svg.elements.EPatternUnits;
import de.interoberlin.sauvignon.lib.model.svg.elements.SVGGElement;
import de.interoberlin.sauvignon.lib.model.svg.elements.circle.SVGCircle;
import de.interoberlin.sauvignon.lib.model.svg.elements.ellipse.SVGEllipse;
import de.interoberlin.sauvignon.lib.model.svg.elements.line.SVGLine;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.ESVGPathSegmentCoordinateType;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.ESVGPathSegmentType;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.lib.model.svg.elements.path.SVGPathSegment;
import de.interoberlin.sauvignon.lib.model.svg.elements.polygon.EFillRule;
import de.interoberlin.sauvignon.lib.model.svg.elements.polygon.SVGPolygon;
import de.interoberlin.sauvignon.lib.model.svg.elements.polyline.SVGPolyline;
import de.interoberlin.sauvignon.lib.model.svg.elements.rect.SVGRect;
import de.interoberlin.sauvignon.lib.model.svg.meta.CC_Work;
import de.interoberlin.sauvignon.lib.model.svg.meta.DC_Type;
import de.interoberlin.sauvignon.lib.model.svg.meta.Defs;
import de.interoberlin.sauvignon.lib.model.svg.meta.Metadata;
import de.interoberlin.sauvignon.lib.model.svg.meta.Pattern;
import de.interoberlin.sauvignon.lib.model.svg.meta.RDF_RDF;
import de.interoberlin.sauvignon.lib.model.svg.transform.transform.SVGTransform;
import de.interoberlin.sauvignon.lib.model.util.ColorName;
import de.interoberlin.sauvignon.lib.model.util.SVGPaint;
import de.interoberlin.sauvignon.lib.model.util.Vector2;

/**
 * Class to parse SVGs
 * 
 * @author Florian
 * 
 */
public class SvgParser
{
	private static SvgParser	instance;
	private static int			zIndex	= 1;

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
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	public SVG parse(InputStream in) throws XmlPullParserException, IOException
	{
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return parseSVG(parser);
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
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVG parseSVG(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = "";
		parser.require(XmlPullParser.START_TAG, null, SVG.getName());

		// Create element
		SVG svg = new SVG();

		// Read attributes
		String xmlns_dc = parser.getAttributeValue(null, "dc");
		String xmlns_cc = parser.getAttributeValue(null, "cc");
		String xmlns_rdf = parser.getAttributeValue(null, "rdf");
		String xmlns_svg = parser.getAttributeValue(null, "svg");
		String xmlns = parser.getAttributeValue(null, "xmlns");
		String version = parser.getAttributeValue(null, "version");
		String width = parser.getAttributeValue(null, "width");
		String height = parser.getAttributeValue(null, "height");
		String id = parser.getAttributeValue(null, "id");

		// Read sub elements
		Defs defs = null;
		Metadata metadata = null;
		List<AGeometric> subelements = new ArrayList<AGeometric>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("defs"))
			{
				defs = parseDefs(parser);
			} else if (name.equals("metadata"))
			{
				metadata = parseMetadata(parser);
			} else if (name.equals("g"))
			{
				subelements.add(parseGroup(parser, svg));
			} else if (name.equals("rect"))
			{
				subelements.add(parseRect(parser, svg));
			} else if (name.equals("circle"))
			{
				subelements.add(parseCircle(parser, svg));
			} else if (name.equals("ellipse"))
			{
				subelements.add(parseEllipse(parser, svg));
			} else if (name.equals("line"))
			{
				subelements.add(parseLine(parser, svg));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, svg));
			} else if (name.equals("polyline"))
			{
				subelements.add(parsePolyline(parser, svg));
			} else if (name.equals("polygon"))
			{
				subelements.add(parsePolygon(parser, svg));
			} else
			{
				skip(parser);
			}
		}

		// Fill element
		if (id != null)
			svg.setId(id);
		if (xmlns_dc != null)
			svg.setXmlNs_dc(xmlns_dc);
		if (xmlns_cc != null)
			svg.setXmlNs_cc(xmlns_cc);
		if (xmlns_rdf != null)
			svg.setXmlNs_rdf(xmlns_rdf);
		if (xmlns_svg != null)
			svg.setXmlNs_svg(xmlns_svg);
		if (xmlns != null)
			svg.setXmlNs(xmlns);
		if (version != null)
			svg.setVersion(version);
		if (width != null)
			svg.setWidth(Float.parseFloat(width));
		if (height != null)
			svg.setHeight(Float.parseFloat(height));
		if (defs != null)
			svg.setDefs(defs);
		if (metadata != null)
			svg.setMetadata(metadata);
		if (!subelements.isEmpty())
			svg.setSubelements(subelements);

		svg.setMaxZindex(zIndex - 1);
		return svg;
	}

	/**
	 * Returns a Defs element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private Defs parseDefs(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Defs.getName());

		// Create element
		Defs defs = new Defs();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");

		// Read sub elements
		Pattern pattern = null;

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("pattern"))
			{
				pattern = parsePattern(parser);
			} else
			{
				skip(parser);
			}
		}

		// Fill element with attributes
		if (id != null)
			defs.setId(id);
		if (pattern != null)
			defs.setPattern(pattern);

		return defs;
	}

	/**
	 * Returns a Pattern element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private Pattern parsePattern(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Pattern.getName());

		// Create element
		Pattern pattern = new Pattern();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String x = parser.getAttributeValue(null, "x");
		String y = parser.getAttributeValue(null, "y");
		String width = parser.getAttributeValue(null, "width");
		String height = parser.getAttributeValue(null, "height");
		String patternUnits = parser.getAttributeValue(null, "patternUnits");
		String viewBox = parser.getAttributeValue(null, "viewBox");

		// Read sub elements
		List<AElement> subelements = new ArrayList<AElement>();

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
				subelements.add(parseRect(parser, null));
			} else if (name.equals("circle"))
			{
				subelements.add(parseCircle(parser, null));
			} else if (name.equals("ellipse"))
			{
				subelements.add(parseEllipse(parser, null));
			} else if (name.equals("line"))
			{
				subelements.add(parseLine(parser, null));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, null));
			} else
			{
				skip(parser);
			}
		}

		// Fill elements
		if (id != null)
			pattern.setId(id);
		if (x != null)
			pattern.setX(Float.parseFloat(x));
		if (y != null)
			pattern.setY(Float.parseFloat(y));
		if (width != null)
			pattern.setWidth(Float.parseFloat(width));
		if (height != null)
			pattern.setHeight(Float.parseFloat(height));
		if (patternUnits != null)
		{
			if (patternUnits.equals("userSpaceOnUse"))
			{
				pattern.setPatternUnits(EPatternUnits.USER_SPACE_ON_USE);
			} else if (patternUnits.equals("userSpace"))
			{
				pattern.setPatternUnits(EPatternUnits.USER_SPACE);
			} else if (patternUnits.equals("objectBoundingBox"))
			{
				pattern.setPatternUnits(EPatternUnits.OBJECT_BOUNDING_BOX);
			}
		}
		if (viewBox != null)
			pattern.setViewBox(parseCoordinates(viewBox));

		return pattern;
	}

	/**
	 * Returns a Metadata element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private Metadata parseMetadata(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, Metadata.getName());

		// Create element
		Metadata metadata = new Metadata();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");

		// Read sub elements
		RDF_RDF rdf_RDF = null;

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("rdf:RDF"))
			{
				rdf_RDF = (parseRdf_RDF(parser));
			} else
			{
				skip(parser);
			}
		}

		// Fill element
		if (id != null)
			metadata.setId(id);
		if (rdf_RDF != null)
			metadata.setRdf_RDF(rdf_RDF);

		return metadata;
	}

	/**
	 * Returns a RDF_RDF element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private RDF_RDF parseRdf_RDF(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, RDF_RDF.getName());

		// Create element
		RDF_RDF rdf_RDF = new RDF_RDF();

		// Read attributes

		// Read sub elements
		CC_Work cc_work = null;

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("cc:Work"))
			{
				cc_work = (parseCC_Work(parser));
			} else
			{
				skip(parser);
			}
		}

		// Fill element with attributes
		if (cc_work != null)
			rdf_RDF.setCc_work(cc_work);

		return rdf_RDF;
	}

	/**
	 * Returns a CC_Work element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private CC_Work parseCC_Work(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, CC_Work.getName());

		// Create element
		CC_Work cc_work = new CC_Work();

		// Read attributes
		String rdf_about = parser.getAttributeValue("rdf", "about");
		String dc_format = parser.getAttributeValue("dc", "format");
		String dc_title = parser.getAttributeValue("dc", "title");

		// Read sub elements
		DC_Type dc_type = null;

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("dc:format"))
			{
				dc_format = parseString(parser, "dc:format");
			} else if (name.equals("dc:type"))
			{
				dc_type = parseDC_Type(parser);
			} else if (name.equals("dc:title"))
			{
				dc_title = parseString(parser, "dc:title");
			} else
			{
				skip(parser);
			}
		}

		// Fill element
		if (rdf_about != null)
			cc_work.setRdf_about(rdf_about);
		if (dc_format != null)
			cc_work.setDc_Format(dc_format);
		if (dc_title != null)
			cc_work.setDc_title(dc_title);
		if (dc_type != null)
			cc_work.setDc_type(dc_type);

		return cc_work;
	}

	/**
	 * Returns a CC_Work element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private DC_Type parseDC_Type(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, DC_Type.getName());

		// Create element
		DC_Type dc_type = new DC_Type();

		// Read attributes
		String rdf_resource = parser.getAttributeValue("rdf", "resource");

		// Read subelements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			skip(parser);
		}

		// Fill element
		if (rdf_resource != null)
			dc_type.setRdf_resource(rdf_resource);

		return dc_type;
	}

	// -------------------------
	// Elements
	// -------------------------

	/**
	 * Returns a G element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	@SuppressLint("DefaultLocale")
	private SVGGElement parseGroup(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGGElement.type.toString().toLowerCase(Locale.getDefault()));

		// Create new element
		SVGGElement g = new SVGGElement();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");

		// Read sub elements
		List<AGeometric> subelements = new ArrayList<AGeometric>();
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("g"))
			{
				subelements.add(parseGroup(parser, g));
			} else if (name.equals("rect"))
			{
				subelements.add(parseRect(parser, g));
			} else if (name.equals("circle"))
			{
				subelements.add(parseCircle(parser, g));
			} else if (name.equals("ellipse"))
			{
				subelements.add(parseEllipse(parser, g));
			} else if (name.equals("line"))
			{
				subelements.add(parseLine(parser, g));
			} else if (name.equals("path"))
			{
				subelements.add(parsePath(parser, g));
			} else if (name.equals("polyline"))
			{
				subelements.add(parsePolyline(parser, g));
			} else if (name.equals("polygon"))
			{
				subelements.add(parsePolygon(parser, g));
			} else if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, g));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, g));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, g));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, g));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (id != null)
			g.setId(id);
		if (transform != null)
			g.setTransform(new SVGTransform(transform));
		if (parentElement != null)
			g.setParentElement(parentElement);
		if (!subelements.isEmpty())
			g.setSubelements(subelements);
		if (!animations.isEmpty())
			g.setAnimations(animations);

		return g;
	}

	/**
	 * Returns a Rect element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	@SuppressLint("DefaultLocale")
	private SVGRect parseRect(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGRect.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGRect rect = new SVGRect();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String x = parser.getAttributeValue(null, "x");
		String y = parser.getAttributeValue(null, "y");
		String width = parser.getAttributeValue(null, "width");
		String height = parser.getAttributeValue(null, "height");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, rect));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, rect));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, rect));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, rect));
			} else
			{
				skip(parser);
			}
		}

		// Fill element
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
		if (fill != null)
			rect.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			rect.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			rect.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			rect.setParentElement(parentElement);
		if (!animations.isEmpty())
			rect.setAnimations(animations);

		rect.setzIndex(zIndex++);

		return rect;
	}

	/**
	 * Returns a Circle element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGCircle parseCircle(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGCircle.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGCircle circle = new SVGCircle();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String cx = parser.getAttributeValue(null, "cx");
		String cy = parser.getAttributeValue(null, "cy");
		String r = parser.getAttributeValue(null, "r");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, circle));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, circle));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, circle));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, circle));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
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
		if (fill != null)
			circle.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			circle.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			circle.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			circle.setParentElement(parentElement);
		if (!animations.isEmpty())
			circle.setAnimations(animations);

		circle.setzIndex(zIndex++);

		return circle;
	}

	/**
	 * Returns a Ellipse element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGEllipse parseEllipse(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGEllipse.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGEllipse ellipse = new SVGEllipse();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String cx = parser.getAttributeValue(null, "cx");
		String cy = parser.getAttributeValue(null, "cy");
		String rx = parser.getAttributeValue(null, "rx");
		String ry = parser.getAttributeValue(null, "ry");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, ellipse));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, ellipse));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, ellipse));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, ellipse));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
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
		if (fill != null)
			ellipse.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			ellipse.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			ellipse.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			ellipse.setParentElement(parentElement);
		if (!animations.isEmpty())
			ellipse.setAnimations(animations);

		ellipse.setzIndex(zIndex++);

		return ellipse;
	}

	/**
	 * Returns a Line element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGLine parseLine(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGLine.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGLine line = new SVGLine();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String x1 = parser.getAttributeValue(null, "x1");
		String y1 = parser.getAttributeValue(null, "y1");
		String x2 = parser.getAttributeValue(null, "x2");
		String y2 = parser.getAttributeValue(null, "y2");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, line));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, line));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, line));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, line));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}
		// Fill element
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
		if (fill != null)
			line.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			line.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			line.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			line.setParentElement(parentElement);
		if (!animations.isEmpty())
			line.setAnimations(animations);

		line.setzIndex(zIndex++);

		return line;
	}

	/**
	 * Returns a Path element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGPath parsePath(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGPath.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGPath path = new SVGPath();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String d = parser.getAttributeValue(null, "d");
		String transform = parser.getAttributeValue(null, "transform");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");
		String strokeLinecap = parser.getAttributeValue(null, "stroke-linecap");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
			if (style.contains("stroke-linecap"))
			{
				strokeLinecap = parseAttributeFromStyle(style, "stroke-linecap");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, path));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, path));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, path));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, path));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (id != null)
			path.setId(id);
		if (d != null)
			path.setD(parseD(d));
		if (transform != null)
			path.setTransform(new SVGTransform(transform));
		if (fill != null)
			path.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			path.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeLinecap != null)
			path.getStyle().setStrokeLinecap(parseLinecap(strokeLinecap));
		if (strokeWidth != null)
			path.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			path.setParentElement(parentElement);
		if (!animations.isEmpty())
			path.setAnimations(animations);

		path.setzIndex(zIndex++);

		return path;
	}

	/**
	 * Returns a Polyline element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGPolyline parsePolyline(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGPolyline.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGPolyline polyline = new SVGPolyline();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String points = parser.getAttributeValue(null, "points");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, polyline));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, polyline));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, polyline));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, polyline));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (id != null)
			polyline.setId(id);
		if (transform != null)
			polyline.setTransform(new SVGTransform(transform));
		if (points != null)
			polyline.setPoints(parsePoints(points));
		if (fill != null)
			polyline.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			polyline.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			polyline.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (parentElement != null)
			polyline.setParentElement(parentElement);
		if (!animations.isEmpty())
			polyline.setAnimations(animations);

		polyline.setzIndex(zIndex++);

		return polyline;
	}

	/**
	 * Returns a Polygon element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private SVGPolygon parsePolygon(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		String name = null;
		parser.require(XmlPullParser.START_TAG, null, SVGPolygon.type.toString().toLowerCase(Locale.getDefault()));

		// Create element
		SVGPolygon polygon = new SVGPolygon();

		// Read attributes
		String id = parser.getAttributeValue(null, "id");
		String transform = parser.getAttributeValue(null, "transform");
		String points = parser.getAttributeValue(null, "points");
		String style = parser.getAttributeValue(null, "style");
		String fill = parser.getAttributeValue(null, "fill");
		String opacity = parser.getAttributeValue(null, "opacity");
		String stroke = parser.getAttributeValue(null, "stroke");
		String strokeWidth = parser.getAttributeValue(null, "stroke-width");
		String fillRule = parser.getAttributeValue(null, "fill-rule");

		if (style != null)
		{
			if (style.contains("opacity"))
			{
				opacity = parseAttributeFromStyle(style, "opacity");
			}
			if (style.contains("fill"))
			{
				fill = parseAttributeFromStyle(style, "fill");
			}
			if (style.contains("stroke"))
			{
				stroke = parseAttributeFromStyle(style, "stroke");
			}
			if (style.contains("stroke-width"))
			{
				strokeWidth = parseAttributeFromStyle(style, "stroke-width");
			}
			if (style.contains("fill-rule"))
			{
				fillRule = parseAttributeFromStyle(style, "fill-rule");
			}
		}

		// Read sub elements
		List<AAnimate> animations = new ArrayList<AAnimate>();

		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			name = parser.getName();

			if (name.equals("animate"))
			{
				animations.add(parseAnimate(parser, polygon));
			} else if (name.equals("animateTransform"))
			{
				animations.add(parseAnimateTransform(parser, polygon));
			} else if (name.equals("animateColor"))
			{
				animations.add(parseAnimateColor(parser, polygon));
			} else if (name.equals("set"))
			{
				animations.add(parseAnimateSet(parser, polygon));
			} else if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (id != null)
			polygon.setId(id);
		if (transform != null)
			polygon.setTransform(new SVGTransform(transform));
		if (points != null)
			polygon.setPoints(parsePoints(points));
		if (fill != null)
			polygon.getStyle().setFill(parsePaint(fill, opacity));
		if (stroke != null)
			polygon.getStyle().setStroke(parsePaint(stroke, opacity));
		if (strokeWidth != null)
			polygon.getStyle().setStrokeWidth(Float.parseFloat(strokeWidth));
		if (fillRule != null)
			polygon.setFillRule(parseFillRule(fillRule));
		if (parentElement != null)
			polygon.setParentElement(parentElement);
		if (!animations.isEmpty())
			polygon.setAnimations(animations);

		polygon.setzIndex(zIndex++);

		return polygon;
	}

	// -------------------------
	// Animation
	// -------------------------

	/**
	 * Returns an Animate element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private Animate parseAnimate(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, "animate");

		// Create element
		Animate animate = new Animate();

		// Read attributes
		String attributeName = parser.getAttributeValue(null, "attributeName");
		String from = parser.getAttributeValue(null, "from");
		String to = parser.getAttributeValue(null, "to");
		String begin = parser.getAttributeValue(null, "begin");
		String dur = parser.getAttributeValue(null, "dur");
		String repeatCount = parser.getAttributeValue(null, "repeatCount");

		// Read sub elements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (attributeName != null)
			animate.setAttributeName(parseAttributeName(attributeName));
		if (from != null)
			animate.setFrom(from);
		if (to != null)
			animate.setTo(to);
		if (begin != null)
			animate.setBegin(begin);
		if (dur != null)
			animate.setDur(dur);
		if (repeatCount != null)
			animate.setRepeatCount(repeatCount);

		return animate;
	}

	/**
	 * Returns an AnimateTransform element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private AnimateTransform parseAnimateTransform(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, "animateTransform");

		// Create element
		AnimateTransform animateTransform = new AnimateTransform();

		// Read attributes
		String attributeName = parser.getAttributeValue(null, "attributeName");
		String type = parser.getAttributeValue(null, "type");
		String from = parser.getAttributeValue(null, "from");
		String to = parser.getAttributeValue(null, "to");
		String begin = parser.getAttributeValue(null, "begin");
		String dur = parser.getAttributeValue(null, "dur");
		String repeatCount = parser.getAttributeValue(null, "repeatCount");

		// Read sub elements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (attributeName != null)
			animateTransform.setAttributeName(attributeName);
		if (type != null)
			animateTransform.setType(parseAnimateTransformType(type));
		if (from != null)
			animateTransform.setFrom(from);
		if (to != null)
			animateTransform.setTo(to);
		if (begin != null)
			animateTransform.setBegin(begin);
		if (dur != null)
			animateTransform.setDur(dur);
		if (repeatCount != null)
			animateTransform.setRepeatCount(repeatCount);

		return animateTransform;
	}

	/**
	 * Returns an AnimateColor element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private AnimateColor parseAnimateColor(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, "animateColor");

		// Create element
		AnimateColor animateColor = new AnimateColor();

		// Read attributes
		String attributeName = parser.getAttributeValue(null, "attributeName");
		String from = parser.getAttributeValue(null, "from");
		String to = parser.getAttributeValue(null, "to");
		String begin = parser.getAttributeValue(null, "begin");
		String dur = parser.getAttributeValue(null, "dur");
		String repeatCount = parser.getAttributeValue(null, "repeatCount");
		String fill = parser.getAttributeValue(null, "fill");
		String values = parser.getAttributeValue(null, "values");

		// Read sub elements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (attributeName != null)
			animateColor.setAttributeName(attributeName);
		if (from != null)
			animateColor.setFrom(from);
		if (to != null)
			animateColor.setTo(to);
		if (begin != null)
			animateColor.setBegin(begin);
		if (dur != null)
			animateColor.setDur(dur);
		if (repeatCount != null)
			animateColor.setRepeatCount(repeatCount);
		if (fill != null)
			animateColor.setFill(parseFill(fill));
		if (values != null)
			animateColor.setValues(parseValues(values));

		return animateColor;
	}

	/**
	 * Returns an AnimateSet element
	 * 
	 * @param parser
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	private AnimateSet parseAnimateSet(XmlPullParser parser, AGeometric parentElement) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, null, "set");

		// Create element
		AnimateSet animateSet = new AnimateSet();

		// Read attributes
		String attributeName = parser.getAttributeValue(null, "attributeName");
		String to = parser.getAttributeValue(null, "to");
		String begin = parser.getAttributeValue(null, "begin");
		String dur = parser.getAttributeValue(null, "dur");
		String repeatDur = parser.getAttributeValue(null, "repeatDur");
		String fill = parser.getAttributeValue(null, "fill");

		// Read sub elements
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
		}

		// Fill element
		if (attributeName != null)
			animateSet.setAttributeName(parseAttributeName(attributeName));
		if (to != null)
			animateSet.setTo(to);
		if (begin != null)
			animateSet.setBegin(begin);
		if (dur != null)
			animateSet.setDur(dur);
		if (repeatDur != null)
			animateSet.setRepeatDur(repeatDur);
		if (fill != null)
			animateSet.setFill(parseFill(fill));

		return animateSet;
	}

	private EAttributeName parseAttributeName(String attributeName)
	{
		if (attributeName.equals("x"))
		{
			return EAttributeName.X;
		} else if (attributeName.equals("y"))
		{
			return EAttributeName.Y;
		} else if (attributeName.equals("width"))
		{
			return EAttributeName.WIDTH;
		} else if (attributeName.equals("height"))
		{
			return EAttributeName.HEIGHT;
		} else
		{
			return null;
		}
	}

	// -------------------------
	// Sub
	// -------------------------

	private List<SVGPathSegment> parseD(String d)
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

		// In case it doesn't start with a character, add numbers to a MOVETO
		// segment
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

	private List<Vector2> parsePoints(String points)
	{
		List<Vector2> coordinates = new ArrayList<Vector2>();

		// Replace commas by spaces
		points = points.replace(",", " ");

		// Remove superfluous whitespace
		while (points.contains("  "))
			points = points.replace("  ", " ");

		// Split string into space-separated entities
		String[] pointsArray = points.split(" ");
		List<String> pointsList = new ArrayList<String>(Arrays.asList(pointsArray));

		Vector2 coordinate = new Vector2();

		for (int i = 0; i < pointsList.size(); i++)
		{
			if (i % 2 == 0)
			{
				coordinate = new Vector2();
				coordinate.setX(Float.parseFloat(pointsList.get(i)));
			} else
			{
				coordinate.setY(Float.parseFloat(pointsList.get(i)));
				coordinates.add(coordinate);
			}
		}

		return coordinates;
	}

	private EFillRule parseFillRule(String fillRule)
	{
		if (fillRule.equals("evenodd"))
		{
			return EFillRule.EVENODD;
		} else if (fillRule.equals("nonzero"))
		{
			return EFillRule.NONZERO;
		} else if (fillRule.equals("inherit"))
		{
			return EFillRule.INHERIT;
		} else
		{
			return null;
		}
	}

	private String parseAttributeFromStyle(String style, String attribute)
	{
		return style.replaceAll(".*" + attribute + ":", "").replaceAll(";.*", "");
	}

	private List<Vector2> parseCoordinates(String s)
	{
		// Replace commas by spaces and remove superfluous whitespaces
		s = s.replace(",", " ");
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
	 * @param paintString
	 * @param opacity
	 * @return
	 */
	private SVGPaint parsePaint(String paintString, String opacity)
	{
		// Set defaults
		if (paintString == null)
		{
			paintString = "#000000";
		}
		if (opacity == null)
		{
			opacity = "1";
		}
		if (paintString.equals("none"))
		{
			paintString = "#FFFFFF";
			opacity = "0";
		}

		int colorA = 0;
		int colorR = 0;
		int colorG = 0;
		int colorB = 0;

		// Check format
		if (paintString.charAt(0) == '#')
		{
			if (paintString.length() == 4)
			{
				paintString = "" + paintString.charAt(0) + paintString.charAt(0) + paintString.charAt(1) + paintString.charAt(1) + paintString.charAt(2) + paintString.charAt(2);

			}

			colorA = (int) (Float.parseFloat(opacity) * 255);
			colorR = Integer.parseInt(paintString.substring(1, 3), 16);
			colorG = Integer.parseInt(paintString.substring(3, 5), 16);
			colorB = Integer.parseInt(paintString.substring(5, 7), 16);
		} else if (paintString.startsWith("rgb(") && paintString.endsWith(")"))
		{
			paintString = paintString.replace("rgb(", "");
			paintString = paintString.replace(")", "");
			paintString = paintString.replace(" ", "");
			String[] pArray = paintString.split(",");
			List<String> p = new ArrayList<String>(Arrays.asList(pArray));

			colorA = 255;

			if (!p.get(0).contains("%"))
			{
				colorR = Integer.parseInt(p.get(0));
			} else
			{
				colorR = (int) (Float.parseFloat(p.get(0)) * 255 / 100);
			}

			if (!p.get(1).contains("%"))
			{
				colorG = Integer.parseInt(p.get(1));
			} else
			{
				colorG = (int) (Float.parseFloat(p.get(1)) * 255 / 100);
			}

			if (!p.get(2).contains("%"))
			{
				colorB = Integer.parseInt(p.get(2));
			} else
			{
				colorB = (int) (Float.parseFloat(p.get(2)) * 255 / 100);
			}
		} else
		{
			paintString = ColorName.getColorByName(paintString);

			if (paintString == null)
			{
				paintString = "#FFFFFF";
			}

			colorA = (int) (Float.parseFloat(opacity) * 255);
			colorR = Integer.parseInt(paintString.substring(1, 3), 16);
			colorG = Integer.parseInt(paintString.substring(3, 5), 16);
			colorB = Integer.parseInt(paintString.substring(5, 7), 16);
		}

		SVGPaint paint = new SVGPaint();
		paint.setARGB(colorA, colorR, colorG, colorB);
		return paint;
	}

	private Cap parseLinecap(String strokeLinecap)
	{
		if (strokeLinecap == null)
		{
			return Cap.BUTT;
		} else if (strokeLinecap.equals("butt"))
		{
			return Cap.BUTT;
		} else if (strokeLinecap.equals("round"))
		{
			return Cap.ROUND;
		} else if (strokeLinecap.equals("square"))
		{
			return Cap.SQUARE;
		} else
		{
			return Cap.BUTT;
		}
	}

	/**
	 * Returns the title of a card
	 * 
	 * @param parser
	 * @return
	 * @throws java.io.IOException
	 * @throws org.xmlpull.v1.XmlPullParserException
	 */
	private String parseString(XmlPullParser parser, String tag) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, null, tag);
		String title = parseText(parser);
		parser.require(XmlPullParser.END_TAG, null, tag);
		return title;
	}

	/**
	 * Reads the value of a cell
	 * 
	 * @param parser
	 * @return
	 * @throws java.io.IOException
	 * @throws org.xmlpull.v1.XmlPullParserException
	 */
	private String parseText(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private EAnimateTransformType parseAnimateTransformType(String type)
	{
		if (type.equals("translate"))
		{
			return EAnimateTransformType.TRANSLATE;
		} else if (type.equals("scale"))
		{
			return EAnimateTransformType.SCALE;
		} else if (type.equals("rotate"))
		{
			return EAnimateTransformType.ROTATE;
		} else if (type.equals("skew-x"))
		{
			return EAnimateTransformType.SKEW_X;
		} else if (type.equals("skew-y"))
		{
			return EAnimateTransformType.SKEW_Y;
		}

		return null;
	}

	private EFill parseFill(String fill)
	{
		if (fill.equals("remove"))
		{
			return EFill.REMOVE;
		} else if (fill.equals("FREEZE"))
		{
			return EFill.FREEZE;
		}

		return null;
	}

	private List<String> parseValues(String values)
	{
		String[] vArray = values.split(";");
		return new ArrayList<String>(Arrays.asList(vArray));
	}

	// -------------------------
	// Skip
	// -------------------------

	/**
	 * Skips a tag that does not fit
	 * 
	 * @param parser
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
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