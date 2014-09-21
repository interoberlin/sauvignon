package de.interoberlin.sauvignon.lib.controller;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import de.interoberlin.sauvignon.lib.model.svg.SVG;

public class SvgHandler
{
	public static SVG getSVGFromInputStream(InputStream svgData) throws XmlPullParserException, IOException
	{
		return SvgParser.getInstance().parse(svgData);
	}

	public static SVG getSVGFromResource(Resources resources, int resId) throws NotFoundException, XmlPullParserException, IOException
	{
		return SvgParser.getInstance().parse(resources.openRawResource(resId));
	}

	public static SVG getSVGFromAsset(AssetManager assetMngr, String svgPath) throws IOException, XmlPullParserException
	{
		InputStream inputStream = assetMngr.open(svgPath);
		SVG svg = getSVGFromInputStream(inputStream);
		inputStream.close();
		return svg;
	}
}
