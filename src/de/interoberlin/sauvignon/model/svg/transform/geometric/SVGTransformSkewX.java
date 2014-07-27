package de.interoberlin.sauvignon.model.svg.transform.geometric;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformSkewX extends ATransformOperator
{
	public final ETransformOperatorType type = ETransformOperatorType.SKEWX;
	private Float angle = 0f;
	
	public SVGTransformSkewX() {}
	
	public SVGTransformSkewX(Float angle)
	{
		this.angle = angle;
		updateMatrix = true;
	}
	
	public SVGTransformSkewX(Float[] args)
	{
		if (args.length > 0)
		{
			angle = args[0];
			updateMatrix = true;
		}
	}

	public Float getAngle()
	{
		return angle;
	}

	public void setAngle(Float angle)
	{
		this.angle = angle;
		updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(1f, 0f, (float) Math.tan(Math.toRadians(angle)), 1f, 0f, 0f);
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
