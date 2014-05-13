package de.interoberlin.sauvignon.model.util;

public class Matrix
{
	// Default: Identity matrix
	private Double a=1d, b=0d, c=0d, d=1d, e=0d, f=0d;
	
	public Matrix() {}
	
	public Matrix(Double a, Double b, Double c, Double d, Double e, Double f)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	public Double getA()
	{
		return a;
	}

	public void setA(Double a)
	{
		this.a = a;
	}

	public Double getB()
	{
		return b;
	}

	public void setB(Double b)
	{
		this.b = b;
	}

	public Double getC()
	{
		return c;
	}

	public void setC(Double c)
	{
		this.c = c;
	}

	public Double getD()
	{
		return d;
	}

	public void setD(Double d)
	{
		this.d = d;
	}

	public Double getE()
	{
		return e;
	}

	public void setE(Double e)
	{
		this.e = e;
	}

	public Double getF()
	{
		return f;
	}

	public void setF(Double f)
	{
		this.f = f;
	}
	
	/**
	 * Matrix multiplied with matrix yields matrix
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix multiply(Matrix matrix)
	{
		return matrix;
	}
	
	/**
	 * Matrix multiplied with vector yields matrix
	 * @param vector
	 * @return
	 */
	public Matrix multiply(Vector2 vector)
	{
		return this;
	}
}
