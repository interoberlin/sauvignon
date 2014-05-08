package de.interoberlin.sauvignon.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

/*
 * 
	private List<Vector2> readD(String d)
	{
		List<Vector2> dList = new ArrayList<Vector2>();

		if (d != null)
		{

			String[] ds = d.split(" ");

			for (String c : new ArrayList<String>(Arrays.asList(ds)))
			{
				if (c.contains(","))
				{
					float x = Float.parseFloat(c.replaceAll(",.*", "  "));
					float y = Float.parseFloat(c.replaceAll(".*,", "  "));

					dList.add(new Vector2(x, y));
				}
			}

		}

		return dList;
	}
 */

/**
 * SVG Path Data consists of one or more path segments
 */
public class SVGPathData
{
	public List<SVGPathSegment>		segments = new ArrayList<SVGPathSegment>();
	
	/**
	 * Extract path segments from a path data string
	 * @param d: SVG path data string
	 */
	public void importData(String d)
	{
		// string to object
		SVGPathSegment s = new SVGPathSegment();
		s.segmentType = SVGPathSegmentType.MOVETO;
		s.coordinateType = SVGPathSegmentCoordinateType.ABSOLUTE;
		this.segments.add(s);
	}
	
	/**
	 * Export this path data to string
	 * @return: SVG path data string
	 */
	public String exportData()
	{
		String result = "";
		/*for (segment in this.segments) {
			
		}*/
		return result;
	}
}
