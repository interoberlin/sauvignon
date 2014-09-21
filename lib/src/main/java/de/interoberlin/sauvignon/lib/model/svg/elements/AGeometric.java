package de.interoberlin.sauvignon.lib.model.svg.elements;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.lib.model.smil.AAnimate;
import de.interoberlin.sauvignon.lib.model.svg.SVG;
import de.interoberlin.sauvignon.lib.model.svg.transform.color.ColorOperator;
import de.interoberlin.sauvignon.lib.model.svg.transform.set.SetOperator;
import de.interoberlin.sauvignon.lib.model.svg.transform.transform.ATransformOperator;
import de.interoberlin.sauvignon.lib.model.svg.transform.transform.SVGTransform;
import de.interoberlin.sauvignon.lib.model.util.Matrix;
import de.interoberlin.sauvignon.lib.model.util.Style;

public class AGeometric extends AElement
{
	/**
	 * SVG Element transformation:
	 * 
	 * Every geometric SVG element may have a transform attribute, which can be
	 * represented by a matrix, by which the element's coordinates as well as
	 * the element's childrens' coordinates are to be modified before rendering.
	 * 
	 * @transform Transformation attribute array
	 * 
	 *            Every element's CTM must include the CTM of it's immediate
	 *            parent.
	 * 
	 * @parentElement The SVG parent element of the current element.
	 * 
	 * @CTM An element's current transformation matrix (CTM) is calculated by
	 *      applying the "transform" matrix onto the parent element's CTM. An
	 *      element's CTM is what is applied onto an element's coordinates to
	 *      obtain final, absolute coordinates for rendering.
	 * 
	 * @animationMatrix Elements can be animated. Animations are represented by
	 *                  a dedicated matrix, which can conveniently be updated
	 *                  via the animate and setAnimationMatrix functions. The
	 *                  animation matrix is applied onto the element's CTM
	 *                  before rendering.
	 * 
	 */

	public static EElement type			= EElement.NONE;

	// Common
	private AGeometric			parentElement;
	private SVG parentSVG;
	private int					zIndex;

	// Geometrics
	private Matrix ctm;
	private boolean				updateCTM		= true;
	private SVGTransform transform;

	// Style
	private Style style			= new Style();

	// Animations
	private List<AAnimate>		animations		= new ArrayList<AAnimate>();

	private ATransformOperator animationTransform;
	private ColorOperator animationColor;
	private List<SetOperator>	animationSets	= new ArrayList<SetOperator>();

	// -------------------------
	// Methods
	// -------------------------
	
	public AGeometric clone()
	{
		AGeometric clone = new AGeometric();
		
		clone.setParentElement(this.getParentElement());
		clone.setParentSVG(this.getParentSVG());
		clone.setzIndex(this.getzIndex());
		
		return clone;
	}

	/**
	 * Returns the current transformation matrix of an element multiplied with
	 * the transformation matrices of all parents
	 * 
	 * @return
	 */
	public Matrix getElementMatrix()
	{
		if (getCTM() != null)
		{
			return getCTM();
		} else
		{
			return new Matrix();
		}
	}

	/**
	 * Returns the scaling matrix
	 * 
	 * @return
	 */
	public Matrix getScaleMatrix()
	{
		if (getParentSVG() != null)
		{
			return getParentSVG().getCTM();
		} else
		{
			return new Matrix();
		}

	}

	// -------------------------
	// Getters / Setter (Common)
	// -------------------------

	public EElement getType()
	{
		return type;
	}

	public AGeometric getParentElement()
	{
		return parentElement;
	}

	public void setParentElement(AGeometric parentElement)
	{
		this.parentElement = parentElement;
		this.mustUpdateCTM();
		parentSVG = null;
	}

	public void setParentSVG(SVG svg)
	{
		parentSVG = svg;
	}

	public SVG getParentSVG()
	{
		if (parentSVG == null && parentElement != null)
		{
			AGeometric p = parentElement;
			while (p.getParentElement() != null)
				p = p.getParentElement();
			parentSVG = (SVG) p;
		}
		return parentSVG;
	}

	public int getzIndex()
	{
		return zIndex;
	}

	public void setzIndex(int zIndex)
	{
		this.zIndex = zIndex;
	}

	// -------------------------
	// Getters / Setter (Geometric)
	// -------------------------

	/**
	 * @return current transformation matrix, including all possible parent
	 *         element transformations
	 */
	public Matrix getCTM()
	{
		if (!updateCTM && ctm != null)
		{
			return ctm;
		}

		// If element has parent, get parent's CTM, else start with identity
		// matrix
		ctm = new Matrix();

		// If transform is defined, apply transform to CTM.
		if (transform != null)
		{
			ctm = transform.getResultingMatrix().multiply(ctm);
		}

		/*
		 * Apply parent transformation
		 * 
		 * Dont't apply SVG transformation. This needs to be done later, since
		 * animations are relative to untransformed SVG coordinates.
		 */
		if (parentElement != null && !(parentElement instanceof SVG))
		{
			ctm = parentElement.getCTM().multiply(ctm);
		}

		/*
		 * If this element is animated, apply animation.
		 */
		if (animationTransform != null)
		{
			ctm = animationTransform.getResultingMatrix().multiply(ctm);
		}

		/*
		 * If this element is animated, apply animation.
		 */

		updateCTM = false;
		return ctm;
	}

	/**
	 * Forcing a CTM does not make sense and is therefore not possible: The CTM
	 * is an implicit value, that is calculated from parent CTM, coordinates,
	 * transform attribute and animations. Changing the final value would
	 * require changing coordinates, deleting animation and transform matrices,
	 * as well as changing the parent's CTM. While the first three measures
	 * might be realizable, it is undesirable to change another element's CTM.
	 * Other elements depend on it.
	 */
	/*
	 * public void setCTM(Matrix CTM)
	 */

	/**
	 * Whenever the transform attribute of an element changes, it's CTM must be
	 * recalculated immediately, since else a non-up-to-date childrens' CTM may
	 * be queried before this.getCTM() is called the next time.
	 * 
	 * After recalculating this's CTM, all subelements must be updated as well,
	 * since they depend on their parents' CTMs.
	 */
	public void mustUpdateCTM()
	{
		this.updateCTM = true;

		// force immediate update
		this.getCTM();

		// update children
		if (this.getType() == EElement.G)
		{
			SVGGElement g = (SVGGElement) this;
			List<AGeometric> subelements = g.getAllSubElements();
			for (AGeometric element : subelements)
			{
				element.mustUpdateCTM();
			}
		}
	}

	public SVGTransform getTransform()
	{
		return transform;
	}

	public void setTransform(SVGTransform transform)
	{
		this.transform = transform;
		this.mustUpdateCTM();
	}

	// -------------------------
	// Getters / Setter (Style)
	// -------------------------

	public Style getStyle()
	{
		style.setParentElement(this);
		return style;
	}

	public void setStyle(Style style)
	{
		style.setParentElement(this);
		this.style = style;
	}

	// -------------------------
	// Getters / Setter (Animation)
	// -------------------------

	public List<AAnimate> getAnimations()
	{
		return animations;
	}

	public void setAnimations(List<AAnimate> animations)
	{
		this.animations = animations;
	}

	public ATransformOperator getAnimationTransform()
	{
		return animationTransform;
	}

	public void setAnimationTransform(ATransformOperator animation)
	{
		this.animationTransform = animation;
		this.mustUpdateCTM();
	}

	public ColorOperator getAnimationColor()
	{
		return animationColor;
	}

	public void setAnimationColor(ColorOperator animationColor)
	{
		this.animationColor = animationColor;
	}

	public List<SetOperator> getAnimationSets()
	{
		return animationSets;
	}

	public void addAnimationSet(SetOperator animationSet)
	{
		this.animationSets.add(animationSet);
	}
}