package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformMatrix extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.MATRIX;
	
	public SVGTransformMatrix() {}
	
	public SVGTransformMatrix(Double a, Double b, Double c, Double d, Double e, Double f)
	{
		resultingMatrix = new Matrix(a, b, c, d, e, f);
	}

	public SVGTransformMatrix(Double[] args)
	{
		resultingMatrix = new Matrix(args[0], args[1], args[2], args[3], args[4], args[5]);
	}
	
	public Double getA()
	{
		return resultingMatrix.getA();
	}

	public void setA(Double a)
	{
		resultingMatrix.setA(a);
	}

	public Double getB()
	{
		return resultingMatrix.getB();
	}

	public void setB(Double b)
	{
		resultingMatrix.setB(b);
	}

	public Double getC()
	{
		return resultingMatrix.getC();
	}

	public void setC(Double c)
	{
		resultingMatrix.setC(c);
	}

	public Double getD()
	{
		return resultingMatrix.getD();
	}

	public void setD(Double d)
	{
		resultingMatrix.setD(d);
	}

	public Double getE()
	{
		return resultingMatrix.getE();
	}

	public void setE(Double e)
	{
		resultingMatrix.setE(e);
	}

	public Double getF()
	{
		return resultingMatrix.getF();
	}

	public void setF(Double f)
	{
		resultingMatrix.setF(f);
	}
}
