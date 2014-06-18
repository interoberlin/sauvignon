package de.interoberlin.sauvignon.model.svg.elements;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import de.interoberlin.sauvignon.model.smil.SMIL;
import de.interoberlin.sauvignon.model.svg.attributes.ATransformOperator;
import de.interoberlin.sauvignon.model.svg.attributes.SVGTransform;
import de.interoberlin.sauvignon.model.util.CSS;
import de.interoberlin.sauvignon.model.util.Matrix;

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

	private CSS				style = new CSS();

	private AGeometric		parentElement;
	private SVGTransform	transform;
	private Matrix			CTM;
	private boolean			updateCTM	= true;			// does the
														// matrix
														// need
														// recalculation

	private List<SMIL>		animations;
	private Matrix			animationMatrix, previousAnimationMatrix;

	private boolean			redraw		= true;


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
		if (parentElement != null)
			CTM = CTM.multiply(parentElement.getCTM());

		/*
		 * If transform is defined, apply transform to CTM.
		 */
		if (transform != null)
			CTM = CTM.multiply(transform.getResultingMatrix());

		/*
		 * If this element is animated, also apply the animation
		 */
		if (animationMatrix != null)
			CTM = CTM.multiply(animationMatrix);

		updateCTM = false;
		this.mustRedraw();
		return CTM;
	}

	/**
	 * Force argument as transformation matrix. Delete transform attributes and
	 * animation matrix.
	 */
	public void setCTM(Matrix CTM)
	{
		this.CTM = CTM;
		this.transform = null;
		this.animationMatrix = null;
	}

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
		this.getCTM(); // force immediate update

		// update children
		if (this.getType() == EElement.G)
		{
			SVGGElement g = (SVGGElement) this;
			List<AElement> subelements = g.getAllSubElements();
			for (AElement element : subelements)
				element.mustUpdateCTM();
		}
	}

	public boolean needsRedraw()
	{
		return redraw;
	}

	public void mustRedraw()
	{
		redraw = true;
		if (parentElement != null)
			parentElement.mustRedraw();
	}

	public void wasRedrawn()
	{
		redraw = false;
	}

	public Matrix getAnimationMatrix()
	{
		if (this.animationMatrix == null)
			this.animationMatrix = new Matrix();
		return animationMatrix;
	}

	public void setAnimationMatrix(Matrix animationMatrix)
	{
		this.animationMatrix = animationMatrix;
		this.mustUpdateCTM();
	}

	/**
	 * Use a matrix to animate this element relative to it's current position.
	 * 
	 * Mathematically, multiply the element's animation matrix by the function
	 * argument. Next time getCTM() is called, animationMatrix will be
	 * multiplied into CTM.
	 * 
	 * @param animationMatrix
	 */
	public void animate(Matrix animationMatrix)
	{
		if (animationMatrix != null)
		{
			setAnimationMatrix(getAnimationMatrix().multiply(animationMatrix));
			previousAnimationMatrix = animationMatrix;
		}
	}

	/**
	 * Use SVGTransform to animate this element relative to it's current
	 * position.
	 */
	public void animate(ATransformOperator animationOperator)
	{
		if (animationOperator != null)
			this.animate(animationOperator.getResultingMatrix());
	}

	/**
	 * Multiply the same matrix again onto the animationMatrix.
	 */
	public void animateAgain()
	{
		// Can't use this.animate(matrix) here,
		// because that would alter the value of previousAnimationMatrix
		if (previousAnimationMatrix != null)
			setAnimationMatrix(getAnimationMatrix().multiply(previousAnimationMatrix));
	}

	public void startSmiling()
	{
		if (animations != null)
			for (SMIL animation : animations)
				animation.start();
	}

	public void stopSmiling()
	{
		if (animations != null)
			for (SMIL animation : animations)
				animation.stop();
	}
}