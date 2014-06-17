package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformTranslate extends ATransformOperator
{
	public final ETransformOperators	type	= ETransformOperators.TRANSLATE;
	private Float						tx		= 0f;
	private Float						ty		= 0f;

	public SVGTransformTranslate()
	{
	}

	public SVGTransformTranslate(Float t)
	{
		tx = t;
		updateMatrix = true;
	}

	public SVGTransformTranslate(Float tx, Float ty)
	{
		this.tx = tx;
		this.ty = ty;
		updateMatrix = true;
	}

	public SVGTransformTranslate(Float[] args)
	{
		if (args.length > 0)
		{
			tx = args[0];
			if (args.length > 1)
				ty = args[1];
			updateMatrix = true;
		}
	}

	public Float getTx()
	{
		return tx;
	}

	public void setTx(Float tx)
	{
		this.tx = tx;
		updateMatrix = true;
	}

	public Float getTy()
	{
		return ty;
	}

	public void setTy(Float ty)
	{
		this.ty = ty;
		updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (updateMatrix)
		{
			resultingMatrix = new Matrix(1f, 0f, 0f, 1f, tx, ty);
			updateMatrix = false;
		}
		return resultingMatrix;
	}
}
