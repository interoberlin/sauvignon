package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformSkewX extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SKEWX;
	
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
