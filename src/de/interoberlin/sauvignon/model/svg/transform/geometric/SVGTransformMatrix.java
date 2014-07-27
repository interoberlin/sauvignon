package de.interoberlin.sauvignon.model.svg.transform.geometric;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformMatrix extends ATransformOperator
{
	public final ETransformOperatorType	type	= ETransformOperatorType.MATRIX;

	public SVGTransformMatrix()
	{
	}

	public SVGTransformMatrix(Float a, Float b, Float c, Float d, Float e, Float f)
	{
		resultingMatrix = new Matrix(a, b, c, d, e, f);
	}

	public SVGTransformMatrix(Float[] args)
	{
		resultingMatrix = new Matrix(args[0], args[1], args[2], args[3], args[4], args[5]);
	}

	public Float getA()
	{
		return resultingMatrix.getA();
	}

	public void setA(Float a)
	{
		resultingMatrix.setA(a);
	}

	public Float getB()
	{
		return resultingMatrix.getB();
	}

	public void setB(Float b)
	{
		resultingMatrix.setB(b);
	}

	public Float getC()
	{
		return resultingMatrix.getC();
	}

	public void setC(Float c)
	{
		resultingMatrix.setC(c);
	}

	public Float getD()
	{
		return resultingMatrix.getD();
	}

	public void setD(Float d)
	{
		resultingMatrix.setD(d);
	}

	public Float getE()
	{
		return resultingMatrix.getE();
	}

	public void setE(Float e)
	{
		resultingMatrix.setE(e);
	}

	public Float getF()
	{
		return resultingMatrix.getF();
	}

	public void setF(Float f)
	{
		resultingMatrix.setF(f);
	}
}
