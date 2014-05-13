package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformSkewY extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.SKEWY;
	
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
