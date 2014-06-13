package de.interoberlin.sauvignon.model.svg.elements;

import java.lang.annotation.ElementType;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import de.interoberlin.sauvignon.model.smil.SMIL;
import de.interoberlin.sauvignon.model.svg.attributes.ATransformOperator;
import de.interoberlin.sauvignon.model.svg.attributes.SVGTransform;
import de.interoberlin.sauvignon.model.util.Matrix;

public class AGeometric extends AElement
{	
	/**
	 * SVG Element transformation:
	 * 
	 * Every geometric SVG element may have a transform attribute,
	 * which can be represented by a matrix, by which the element's
	 * coordinates as well as the element's childrens' coordinates
	 * are to be modified before rendering.
	 * 
	 * @transform Transformation attribute array
	 * 
	 * Every element's CTM must include the CTM of it's immediate parent.
	 * 
	 * @parentElement SVG parent element of the current element
	 * 
	 * @CTM An element's current transformation matrix (CTM) is
	 * 		calculated by applying the matrix resulting from the transform attributes
	 * 		onto the parent element's CTM. An element's CTM is
	 * 		what is applied onto an element's coordinates to
	 * 		obtain final, absolute coordinates for rendering.
	 * 
	 * @animationMatrix Elements can be animated. Animations are represented
	 * 					by a dedicated matrix, which can conveniently be updated via the
	 * 					animate and setAnimationMatrix functions.
	 * 					The animation matrix is applied onto the element's CTM before rendering.
	 * 
	 */
	public static ElementType type;
	
	private AGeometric parentElement;

	private SVGTransform transform;
	private Matrix CTM;
	private boolean updateCTM = true;

	private List<SMIL> animations;
	private Matrix animationMatrix;

	private Paint		stroke;
	private Paint		fill;
	private float		strokeWidth	= 1.0f;
	
	public SVGTransform getTransform()
	{
		return transform;
	}

	public void setTransform(SVGTransform transform)
	{
		this.transform = transform;
		this.mustUpdateCTM();
	}

	public AElement getParentElement()
	{
		return parentElement;
	}

	public void setParentElement(AGeometric parentElement)
	{
		this.parentElement = parentElement;
		this.mustUpdateCTM();
	}

	/**
	 * @return Current transformation matrix, including all possible parent element transformations.
	 */
	public Matrix getCTM()
	{
		if (!updateCTM)
			return CTM;
		
		/*
		 * If element has parent, get parent's CTM,
		 * else start with identity matrix.
		 */
		CTM = new Matrix();
		if (parentElement != null)
			CTM = CTM.multiply(parentElement.getCTM());
		
		/*
		 * If transform is defined,
		 * apply transform to CTM.
		 */
		if (transform != null)
			CTM = CTM.multiply(transform.getResultingMatrix());
		
		/*
		 * If this element is animated,
		 * also apply the animation
		 */
		if (animationMatrix != null)
			CTM = CTM.multiply(animationMatrix);
		
		updateCTM = false;
		return CTM;
	}
	
	public void setCTM(Matrix CTM)
	{
		this.CTM = CTM;
	}

	public Matrix getAnimationMatrix()
	{
		return animationMatrix;
	}

	/**
	 * Whenever the transform attribute of an element changes,
	 * it's CTM must be recalculated immediately,
	 * since else a non-up-to-date childrens' CTM may be queried
	 * before this.getCTM() is called the next time.
	 * 
	 * After recalculating this's CTM, all subelements must be updated as well,
	 * since they depend on their parents' CTMs.
	 */
	public void mustUpdateCTM()
	{
		this.updateCTM = true;
		this.getCTM(); // force immediate update
		
		// update children
		if (this.getType() == EElement.G) {
			SVGGElement g = (SVGGElement) this;
			List<AElement> subelements = g.getAllSubElements();
			for (AElement element : subelements)
				element.mustUpdateCTM();
		}
	}
	
	public void setAnimationMatrix(Matrix animationMatrix)
	{
		this.animationMatrix = animationMatrix;
		this.mustUpdateCTM();
	}

	/**
	 * Use a matrix to animate this element
	 * relative to it's current position.
	 * 
	 * @param animationMatrix
	 */
	public void animate(Matrix animationMatrix)
	{
		if (animationMatrix != null) {
			if (this.animationMatrix == null)
				this.animationMatrix = new Matrix();
			this.animationMatrix = this.animationMatrix.multiply(animationMatrix);
			this.mustUpdateCTM();
		}
	}

	/**
	 * Use SVGTransform to animate this element
	 * relative to it's current position.
	 */
	public void animate(ATransformOperator animationOperator)
	{
		if (animationOperator != null) {
			this.animate( animationOperator.getResultingMatrix() );
			this.mustUpdateCTM();
		}
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
	
	public Paint getStroke()
	{
		return stroke;
	}

	public void setStroke(Paint stroke)
	{
		this.stroke = stroke;
	}

	public Paint getFill()
	{
		return fill;
	}

	public int getFillA()
	{
		return fill.getAlpha();
	}

	public void setFillA(int a)
	{
		fill.setAlpha(a);
	}

	public int getFillR()
	{
		return Color.red(fill.getColor());
	}

	public void setFillR(int r)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), (r % 255), getFillG(), getFillB());

		setFill(p);
	}

	public int getFillG()
	{
		return Color.green(fill.getColor());
	}

	public void setFillG(int g)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), getFillR(), (g % 255), getFillB());

		setFill(p);
	}

	public int getFillB()
	{
		return Color.blue(fill.getColor());
	}

	public void setFillB(int b)
	{
		Paint p = new Paint();
		p.setARGB(getFillA(), getFillR(), getFillG(), (b % 255));

		setFill(p);
	}

	public void setFill(Paint fill)
	{
		this.fill = fill;
	}

	public float getStrokeWidth()
	{
		return strokeWidth;
	}

	public void setStrokeWidth(float f)
	{
		if (f >= 0)
		{
			this.strokeWidth = f;
		}
	}
}
