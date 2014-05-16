package de.interoberlin.sauvignon.model.svg.attributes;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransform
{
	private List<ATransformOperator> transformOperations = new ArrayList<ATransformOperator>();
	private Matrix resultingMatrix;
	private Boolean updateMatrix = true;

	public SVGTransform() {}
	
	public SVGTransform(String s)
	{
		/*
		 * Split transform commands
		 */
		String[] transforms = s.split(")");
		for (int i=0; i < transforms.length; i++)
		{
			/*
			 * Split command from arguments
			 */
			String[] parts = transforms[i].split("(");
			if (parts.length > 1)
			{
				/*
				 * Parse argument floats
				 */
				String[] b = parts[1].trim().split(",");
				Double[] args = new Double[1];
				for (int j=0; j < b.length; j++)
					args[j] = Double.parseDouble(b[j].trim());
				
				/*
				 * Create transform commands with these arguments
				 */
				ATransformOperator operator = null;
				String cmd = parts[0].trim().toLowerCase();
				if (cmd.equals("matrix"))
					operator = new SVGTransformMatrix(args);
				else if (cmd.equals("translate"))
					operator = new SVGTransformTranslate(args);
				else if (cmd.equals("scale"))
					operator = new SVGTransformScale(args);
				else if (cmd.equals("rotate"))
					operator = new SVGTransformRotate(args);
				else if (cmd.equals("skewx"))
					operator = new SVGTransformSkewX(args);
				else if (cmd.equals("skewy"))
					operator = new SVGTransformSkewX(args);
				if (operator != null)
					transformOperations.add(operator);
			}
		}
	}
	
	public List<ATransformOperator> getOperations()
	{
		return transformOperations;
	}
	
	public void setOperations(List<ATransformOperator> operations)
	{
		transformOperations = operations;
		updateMatrix = true;
	}
	
	public void clearOperations()
	{
		transformOperations = new ArrayList<ATransformOperator>();
		updateMatrix = true;
	}

	public void addOperation(ATransformOperator operation)
	{
		transformOperations.add(operation);
		updateMatrix = true;
	}
	
	/**
	 * Multiply and return the resulting matrices of all my transform operations
	 * @return
	 */
	public Matrix getResultingMatrix()
	{
		if (updateMatrix)
		{
			resultingMatrix = new Matrix();
			for (int i=0; i < transformOperations.size(); i++)
			{
				Matrix matrix = transformOperations.get(i).getResultingMatrix(); 
				resultingMatrix = resultingMatrix.multiply(matrix);
			}
			updateMatrix = false;
		}
		return resultingMatrix;
	}
}