package de.interoberlin.sauvignon.model.svg.transform.geometric;

import de.interoberlin.sauvignon.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.model.util.Matrix;

public abstract class ATransformOperator
{
	public ETransformOperators	type;
	private AGeometric			relativeTo;

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

	public AGeometric getRelativeTo()
	{
		return relativeTo;
	}

	public void setRelativeTo(AGeometric relativeTo)
	{
		this.relativeTo = relativeTo;
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