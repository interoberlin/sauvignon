package de.interoberlin.sauvignon.model.svg.elements;

import java.util.List;
import java.util.Random;

import android.graphics.Paint;

import de.interoberlin.sauvignon.model.smil.SMIL;
import de.interoberlin.sauvignon.model.svg.SVG;
import de.interoberlin.sauvignon.model.svg.elements.circle.SVGCircle;
import de.interoberlin.sauvignon.model.svg.transform.ATransformOperator;
import de.interoberlin.sauvignon.model.svg.transform.SVGTransform;
import de.interoberlin.sauvignon.model.svg.transform.SVGTransformRotate;
import de.interoberlin.sauvignon.model.util.CSS;
import de.interoberlin.sauvignon.model.util.Matrix;
import de.interoberlin.sauvignon.model.util.Vector2;

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

	public static EElement	type		= EElement.NONE;

	private CSS				style		= new CSS();

	private AGeometric		parentElement;
	private AGeometric		mySVG;
	private SVGTransform	transform;

	private Matrix			CTM;
	// does the matrix need recalculation ?
	private boolean			updateCTM	= true;
	
	private ATransformOperator	animation;

	private int				zIndex;

	public EElement getType()
	{
		return type;
	}
	
	public CSS getStyle()
	{
		style.setParentElement(this);
		return style;
	}

	public void setStyle(CSS style)
	{
		style.setParentElement(this);
		this.style = style;
	}

	public AGeometric getParentElement()
	{
		return parentElement;
	}

	public void setParentElement(AGeometric parentElement)
	{
		this.parentElement = parentElement;
		this.mustUpdateCTM();
		mySVG = null;
	}

	public AGeometric getMySVG()
	{
		if (mySVG == null)
		{
			AGeometric p = parentElement;
			while (p.getParentElement() != null)
				p = p.getParentElement();
			mySVG = (SVG) p;
		}
		return mySVG;
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

	/**
	 * @return Current transformation matrix, including all possible parent
	 *         element transformations.
	 */
	public Matrix getCTM()
	{
		if (!updateCTM)
			return CTM;

		/*
		 * If element has parent, get parent's CTM, else start with identity
		 * matrix.
		 */
		CTM = new Matrix();

		/*
		 * If transform is defined, apply transform to CTM.
		 */
		if (transform != null)
			CTM = transform.getResultingMatrix().multiply(CTM);
		
		/*
		 * Apply parent transformation
		 * 
		 * Dont't apply SVG transformation.
		 * This needs to be done later, since animations are relative
		 * to untransformed SVG coordinates.
		 */
		if (parentElement != null && !(parentElement instanceof SVG))
		{
			CTM = parentElement.getCTM().multiply(CTM);
		}

		/*
		 * If this element is animated, apply animation.
		 */
		if (animation != null)
		{
			if (animation instanceof SVGTransformRotate)
			{
/*				// for debugging:
				SVG svg = (SVG) getMySVG();
				SVGCircle c = (SVGCircle) svg.getElementById("rotArmLeft");
				SVGTransformRotate r = (SVGTransformRotate) animation;
				
				// move circle to rotation center
				c.setCx(r.getCx());
				c.setCy(r.getCy());
				c.setRadius(5);
				Paint p = new Paint();
				p.setARGB(
							255, // opacity: visible 
							new Random().nextInt(255),
							new Random().nextInt(255),
							new Random().nextInt(255)
						);
				c.getStyle().setFill(p);
*/				
			}
			CTM = animation.getResultingMatrix().multiply(CTM);
		}

		updateCTM = false;
		return CTM;
	}

	/**
	 * Forcing a CTM does not make sense and is therefore not possible:
	 * The CTM is an implicit value, that is calculated from
	 * parent CTM, coordinates, transform attribute and animations.
	 * Changing the final value would require changing coordinates,
	 * deleting animation and transform matrices, as well as changing the parent's CTM.
	 * While the first three measures might be realizable,
	 * it is undesirable to change another element's CTM.
	 * Other elements depend on it.
	 */
/*	public void setCTM(Matrix CTM)
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

	public ATransformOperator getAnimation()
	{
		return animation;
	}

	public void setAnimation(ATransformOperator animation)
	{
		this.animation = animation;
		this.mustUpdateCTM();
	}

	public int getzIndex()
	{
		return zIndex;
	}

	public void setzIndex(int zIndex)
	{
		this.zIndex = zIndex;
	}
}