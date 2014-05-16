package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformTranslate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.TRANSLATE;
	private Double tx = 0d;
	private Double ty = 0d;
	
	public SVGTransformTranslate(Double t)
	{
		this.tx = t;
		this.ty = 0d;
		this.updateMatrix = true;
	}
	
	public SVGTransformTranslate(Double tx, Double ty)
	{
		this.tx = tx;
		this.ty = ty;
		this.updateMatrix = true;
	}
	
	public Double getTx()
	{
		return tx;
	}

	public void setTx(Double tx)
	{
		this.tx = tx;
		this.updateMatrix = true;
	}

	public Double getTy()
	{
		return ty;
	}

	public void setTy(Double ty)
	{
		this.ty = ty;
		this.updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(1d, 0d, 0d, 1d, tx, ty);
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
