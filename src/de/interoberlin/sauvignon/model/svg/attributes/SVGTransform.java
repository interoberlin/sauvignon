package de.interoberlin.sauvignon.model.svg.attributes;

import java.util.ArrayList;
import java.util.List;

public class SVGTransform
{
	private List<ATransformOperator> operations  = new ArrayList<ATransformOperator>();

	public List<ATransformOperator> getOperations()
	{
		return operations;
	}

	public void setOperations(List<ATransformOperator> operations)
	{
		this.operations = operations;
	}
}
