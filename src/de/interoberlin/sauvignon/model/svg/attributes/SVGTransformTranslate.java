package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformTranslate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.TRANSLATE;
	
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
