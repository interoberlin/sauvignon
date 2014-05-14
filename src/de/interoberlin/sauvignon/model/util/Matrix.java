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
	 * @param m
	 * @return
	 */
	public Matrix multiply(Matrix m)
	{
		Matrix result = new Matrix();
		result.setA(a*m.getA() + c*m.getB() + 0);
		result.setB(b*m.getA() + d*m.getB() + 0);
		result.setC(a*m.getC() + c*m.getD() + 0);
		result.setD(b*m.getC() + d*m.getD() + 0);
		result.setE(a*m.getE() + c*m.getF() + e*1);
		result.setF(b*m.getE() + d*m.getF() + f*1);
		return result;
	}
	
	/**
	 * Matrix multiplied with vector yields vector
	 * @param vector
	 * @return
	 */
	public Vector2 multiply(Vector2 v)
	{
		Vector2 result = new Vector2();
		result.setX(a*v.getX() + c*v.getY() + e*1);
		result.setY(b*v.getX() + d*v.getY() + f*1);
		return result;
	}
}
