package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformRotate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.ROTATE;
	private Float angle = 0f;
	
	public Float getAngle()
	{
		return angle;
	}

	public void setAngle(Float angle)
	{
		this.angle = angle;
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
