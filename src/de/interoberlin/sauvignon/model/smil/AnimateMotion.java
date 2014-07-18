package de.interoberlin.sauvignon.model.smil;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.svg.elements.path.SVGPath;

/**
 * Animate along path
 * 
 * http://www.w3.org/TR/SVG/animate.html#AnimateMotionElement
 */
public class AnimateMotion implements IAnimatable
{
	ECalcMode calcMode;
	SVGPath path;
	List<Float> keyPoints = new ArrayList<Float>();
}
