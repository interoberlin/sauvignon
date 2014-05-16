package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformScale extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SCALE;
	private Double sx = 0d;
	private Double sy = 0d;
	
	public SVGTransformScale(Double s)
	{
		this.sx = s;
		this.sy = s;
		this.updateMatrix = true;
	}
	
	public SVGTransformScale(Double sx, Double sy)
	{
		this.sx = sx;
		this.sy = sy;
		this.updateMatrix = true;
	}
	
	public Double getSx()
	{
		return sx;
	}

	public void setSx(Double sx)
	{
		this.sx = sx;
		this.updateMatrix = true;
	}

	public Double getSy()
	{
		return sy;
	}

	public void setSy(Double sy)
	{
		this.sy = sy;
		this.updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(sx, 0d, 0d, sy, 0d, 0d);
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
