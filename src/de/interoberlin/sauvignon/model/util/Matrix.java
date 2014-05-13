package de.interoberlin.sauvignon.model.util;

public class Matrix
{
	// Default: Identity matrix
	private float a=1, b=0, c=0, d=1, e=0, f=0;

	public float getA()
	{
		return a;
	}

	public void setA(float a)
	{
		this.a = a;
	}

	public float getB()
	{
		return b;
	}

	public void setB(float b)
	{
		this.b = b;
	}

	public float getC()
	{
		return c;
	}

	public void setC(float c)
	{
		this.c = c;
	}

	public float getD()
	{
		return d;
	}

	public void setD(float d)
	{
		this.d = d;
	}

	public float getE()
	{
		return e;
	}

	public void setE(float e)
	{
		this.e = e;
	}

	public float getF()
	{
		return f;
	}

	public void setF(float f)
	{
		this.f = f;
	}
	
	/**
	 * Matrix multiplication
	 * 
	 * @param matrix: Matrix to be multiplied with
	 * @return Multiplied matrices.
	 */
	public Matrix multiply(Matrix matrix)
	{
		return matrix;
	}
}
