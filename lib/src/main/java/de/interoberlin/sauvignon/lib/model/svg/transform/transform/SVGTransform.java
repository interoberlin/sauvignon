package de.interoberlin.sauvignon.lib.model.svg.transform.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.interoberlin.sauvignon.lib.model.util.Matrix;

public class SVGTransform
{
	private List<ATransformOperator>	transformOperations	= new ArrayList<ATransformOperator>();
	private Matrix						resultingMatrix;
	private boolean						updateMatrix		= true;

	public SVGTransform()
	{
	}

	public SVGTransform(String s)
	{
		if (s == null)
			return;

		/*
		 * Split transform commands
		 */
		String[] transforms = s.split("\\)");
		for (int i = 0; i < transforms.length; i++)
		{
			// System.out.println(transforms[i]+')');
			/*
			 * Split command from arguments
			 */
			String[] parts = transforms[i].split("\\(");
			if (parts.length > 1)
			{
				/*
				 * Parse argument floats
				 */
				// System.out.println(parts[1]);
				String[] b = parts[1].trim().split("\\,");
				Float[] args = new Float[b.length];
				for (int j = 0; j < b.length; j++)
				{
					// System.out.println(b[j]);
					args[j] = Float.parseFloat(b[j].trim());
				}

				/*
				 * Create transform commands with these arguments
				 */
				ATransformOperator operator = null;
				String cmd = parts[0].trim().toLowerCase(Locale.getDefault());
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
	 * 
	 * @return
	 */
	public Matrix getResultingMatrix()
	{
		if (updateMatrix)
		{
			resultingMatrix = new Matrix();
			for (int i = 0; i < transformOperations.size(); i++)
			{
				Matrix matrix = transformOperations.get(i).getResultingMatrix();
				resultingMatrix = resultingMatrix.multiply(matrix);
			}
			updateMatrix = false;
		}
		return resultingMatrix;
	}
}
