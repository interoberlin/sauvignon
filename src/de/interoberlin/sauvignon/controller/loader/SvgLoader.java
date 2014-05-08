package de.interoberlin.sauvignon.controller.loader;

import java.io.IOException;


import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import de.interoberlin.sauvignon.controller.parser.SvgHandler;
import de.interoberlin.sauvignon.model.svg.SVG;

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
