package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformScale extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SCALE;
	private Float sx;
	private Float sy;
	
	public Float getSx()
	{
		return sx;
	}

	public void setSx(Float sx)
	{
		this.sx = sx;
		this.sy = sx; // If <sy> is not provided, it is assumed to be equal to <sx>.
	}

	public Float getSy()
	{
		return sy;
	}

	public void setSy(Float sy)
	{
		this.sy = sy;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			// ...
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
