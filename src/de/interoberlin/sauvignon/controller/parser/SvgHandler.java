package de.interoberlin.sauvignon.controller.parser;

import java.io.IOException;
import java.io.InputStream;


import org.xmlpull.v1.XmlPullParserException;

import de.interoberlin.sauvignon.model.svg.SVG;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

public class SvgHandler
{
	public static SVG getSVGFromInputStream(InputStream svgData) throws XmlPullParserException, IOException
	{
		return SVGParser.getInstance().parse(svgData);
	}

	public static SVG getSVGFromResource(Resources resources, int resId) throws NotFoundException, XmlPullParserException, IOException
	{
		return SVGParser.getInstance().parse(resources.openRawResource(resId));
	}

	public static SVG getSVGFromAsset(AssetManager assetMngr, String svgPath) throws IOException, XmlPullParserException
	{
		InputStream inputStream = assetMngr.open(svgPath);
		SVG svg = getSVGFromInputStream(inputStream);
		inputStream.close();
		return svg;
	}
}
