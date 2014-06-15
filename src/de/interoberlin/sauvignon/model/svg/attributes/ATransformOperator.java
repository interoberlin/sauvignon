package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class ATransformOperator
{
	public ETransformOperators	type;
	public Matrix				resultingMatrix	= new Matrix();
	protected boolean			updateMatrix	= true;

	public ETransformOperators getType()
	{
		return type;
	}

	public void setType(ETransformOperators type)
	{
		this.type = type;
	}

	public Matrix getResultingMatrix()
	{
		return resultingMatrix;
	}

	public void setResultingMatrix(Matrix resultingMatrix)
	{
		this.resultingMatrix = resultingMatrix;
	}

	public boolean isUpdateMatrix()
	{
		return updateMatrix;
	}

	public void setUpdateMatrix(boolean updateMatrix)
	{
		this.updateMatrix = updateMatrix;
	}
}