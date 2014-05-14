package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformScale extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SCALE;
	private Double sx = 0d;
	private Double sy = 0d;
	
	public SVGTransformScale() {}
	
	public SVGTransformScale(Double s)
	{
		sx = s;
		sy = s;
		updateMatrix = true;
	}
	
	public SVGTransformScale(Double sx, Double sy)
	{
		this.sx = sx;
		this.sy = sy;
		updateMatrix = true;
	}
	
	public SVGTransformScale(Double[] args)
	{
		if (args.length > 0)
		{
			sx = args[0];
			if (args.length > 1)
				sy = args[1];
			updateMatrix = true;
		}
	}
	
	public Double getSx()
	{
		return sx;
	}

	public void setSx(Double sx)
	{
		this.sx = sx;
		updateMatrix = true;
	}

	public Double getSy()
	{
		return sy;
	}

	public void setSy(Double sy)
	{
		this.sy = sy;
		updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (updateMatrix)
		{
			resultingMatrix = new Matrix(sx, 0d, 0d, sy, 0d, 0d);
			updateMatrix = false;
		}
		return resultingMatrix;
	}
}
