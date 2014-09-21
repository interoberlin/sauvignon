package de.interoberlin.sauvignon.lib.model.smil;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.lib.model.svg.elements.path.SVGPath;
import de.interoberlin.sauvignon.lib.model.svg.transform.transform.ATransformOperator;

/**
 * Animate along path
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateMotionElement
 */
public class AnimateMotion extends AAnimate
{
	ECalcMode calcMode;
	SVGPath path;
	List<Float> keyPoints = new ArrayList<Float>();
	
	public ATransformOperator getTransformOperator(long millisSinceStart)
	{
		return null;
	}
}
