package de.interoberlin.sauvignon.model.svg.attributes;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransform
{
	private List<ATransformOperator> transformOperations = new ArrayList<ATransformOperator>();
	private Matrix resultingMatrix;
	private Boolean updateMatrix = true;

	public List<ATransformOperator> getOperations()
	{
		return transformOperations;
	}

	public void addOperation(ATransformOperator operation)
	{
		this.transformOperations.add(operation);
		this.updateMatrix = true;
	}

	public void clearOperations()
	{
		this.transformOperations = new ArrayList<ATransformOperator>();
		this.updateMatrix = true;
	}
	
	/**
	 * Multiply and return the resulting matrices of all my transform operations
	 * @return
	 */
	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix();
			for (int i=0; i < this.transformOperations.size(); i++)
			{
				Matrix matrix = this.transformOperations.get(i).getResultingMatrix(); 
				this.resultingMatrix = this.resultingMatrix.multiply(matrix);
			}
			this.updateMatrix = false;
		}
		return resultingMatrix;
	}
}
