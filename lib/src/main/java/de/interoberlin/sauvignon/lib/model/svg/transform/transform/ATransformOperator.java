package de.interoberlin.sauvignon.lib.model.svg.transform.transform;

import de.interoberlin.sauvignon.lib.model.svg.elements.AGeometric;
import de.interoberlin.sauvignon.lib.model.util.Matrix;

public abstract class ATransformOperator
{
	public ETransformOperatorType type;
	private AGeometric relativeTo;

	public Matrix resultingMatrix	= new Matrix();
	protected boolean			updateMatrix	= true;

	public ETransformOperatorType getType()
	{
		return type;
	}

	public void setType(ETransformOperatorType type)
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