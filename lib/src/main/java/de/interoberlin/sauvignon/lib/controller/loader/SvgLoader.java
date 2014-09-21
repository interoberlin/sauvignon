package de.interoberlin.sauvignon.lib.controller.loader;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import de.interoberlin.sauvignon.lib.controller.parser.SvgHandler;
import de.interoberlin.sauvignon.lib.model.svg.SVG;

public class SvgLoader
{
	public static SVG getSVGFromAsset(Context c, String svgPath)
	{
		try
		{
			return SvgHandler.getSVGFromAsset(c.getAssets(), svgPath);
		} catch (IOException e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}

		return null;
	}
}
