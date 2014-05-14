package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformSkewY extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SKEWY;
	private Double angle = 0d;
	
	public SVGTransformSkewY() {}
	
	public SVGTransformSkewY(Double angle)
	{
		this.angle = angle;
		updateMatrix = true;
	}
	
	public SVGTransformSkewY(Double[] args)
	{
		if (args.length > 0)
		{
			angle = args[0];
			updateMatrix = true;
		}
	}
	
	public Double getAngle()
	{
		return angle;
	}

	public void setAngle(Double angle)
	{
		this.angle = angle;
		updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(1d, Math.tan(angle), 0d, 1d, 0d, 0d);
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
