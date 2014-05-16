package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformTranslate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.TRANSLATE;
	private Double tx = 0d;
	private Double ty = 0d;
	
	public SVGTransformTranslate() {}
	
	public SVGTransformTranslate(Double t)
	{
		tx = t;
		updateMatrix = true;
	}
	
	public SVGTransformTranslate(Double tx, Double ty)
	{
		this.tx = tx;
		this.ty = ty;
		updateMatrix = true;
	}
	
	public SVGTransformTranslate(Double[] args)
	{
		if (args.length > 0)
		{
			tx = args[0];
			if (args.length > 1)
				ty = args[1];
			updateMatrix = true;
		}
	}
	
	public Double getTx()
	{
		return tx;
	}

	public void setTx(Double tx)
	{
		this.tx = tx;
		updateMatrix = true;
	}

	public Double getTy()
	{
		return ty;
	}

	public void setTy(Double ty)
	{
		this.ty = ty;
		updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (updateMatrix)
		{
			resultingMatrix = new Matrix(1d, 0d, 0d, 1d, tx, ty);
			updateMatrix = false;
		}
		return resultingMatrix;
	}
}
