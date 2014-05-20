package de.interoberlin.sauvignon.model.svg.elements;

import java.lang.annotation.ElementType;

import android.graphics.Color;
import android.graphics.Paint;
import de.interoberlin.sauvignon.model.svg.attributes.SVGTransform;
import de.interoberlin.sauvignon.model.util.Matrix;

public class AGeometric extends AElement
{
	public static ElementType	type;
	
	/**
	 * SVG Element transformation:
	 * 
	 * Every geometric SVG element may have a transform attribute,
	 * which is finally represented by a matrix, by which the element's
	 * coordinates are modified.
	 * @transform Holds "transform" attribute of an SVG element.
	 * 
	 * Furthermore every element must also honor the current
	 * transformation matrix (CTM) of it's immediate parent.
	 * @parentElement The SVG parent element of the current element.
	 * 
	 * @CTM An element's current transformation matrix (CTM) is
	 * 		calculated by applying the "transform" matrix onto
	 * 		the parent element's CTM. An element's CTM is
	 * 		what is applied onto an element's coordinates to
	 * 		obtain final, absolute coordinates.
	 */
	private SVGTransform transform;
	private AGeometric parentElement;
	private Matrix CTM;
	private boolean updateCTM = true;

	private Paint				stroke;
	private Paint				fill;
	private float				strokeWidth	= 1.0f;
	
	public SVGTransform getTransform()
	{
		return transform;
	}

	public void setTransform(SVGTransform transform)
	{
		this.transform = transform;
		updateCTM = true;
	}

	public AElement getParentElement()
	{
		return parentElement;
	}

	public void setParentElement(AGeometric parentElement)
	{
		this.parentElement = parentElement;
		updateCTM = true;
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
		updateCTM = false;
		return CTM;
	}

	public void setCTM(Matrix CTM)
	{
		this.CTM = CTM;
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
