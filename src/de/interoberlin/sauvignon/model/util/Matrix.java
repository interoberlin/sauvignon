package de.interoberlin.sauvignon.model.util;

public class Matrix
{
	// Default: Identity matrix
	private float	a	= 1f;
	private float	b	= 0f;
	private float	c	= 0f;
	private float	d	= 1f;
	private float	e	= 0f;
	private float	f	= 0f;

	// -------------------------
	// Constructors
	// -------------------------
	
	public Matrix()
	{
	}

	public Matrix(Float a, Float b, Float c, Float d, Float e, Float f)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	// -------------------------
	// Methods
	// -------------------------

	/**
	 * Matrix multiplied with matrix yields matrix
	 * 
	 * @param m
	 * @return
	 */
	public Matrix multiply(Matrix m)
	{
		Matrix result = new Matrix();
		result.setA(a * m.getA() + c * m.getB() + 0);
		result.setB(b * m.getA() + d * m.getB() + 0);
		result.setC(a * m.getC() + c * m.getD() + 0);
		result.setD(b * m.getC() + d * m.getD() + 0);
		result.setE(a * m.getE() + c * m.getF() + e * 1);
		result.setF(b * m.getE() + d * m.getF() + f * 1);
		return result;
	}

	/**
	 * Matrix multiplied with vector yields vector
	 * 
	 * @param vector
	 * @return
	 */
	public Vector2 multiply(Vector2 v)
	{
		Vector2 result = new Vector2();
		result.setX(a * v.getX() + c * v.getY() + e * 1);
		result.setY(b * v.getX() + d * v.getY() + f * 1);
		return result;
	}

	// -------------------------
	// Getters / Setter
	// -------------------------
	
	public Float getA()
	{
		return a;
	}

	public void setA(Float a)
	{
		this.a = a;
	}

	public Float getB()
	{
		return b;
	}

	public void setB(Float b)
	{
		this.b = b;
	}

	public Float getC()
	{
		return c;
	}

	public void setC(Float c)
	{
		this.c = c;
	}

	public Float getD()
	{
		return d;
	}

	public void setD(Float d)
	{
		this.d = d;
	}

	public Float getE()
	{
		return e;
	}

	public void setE(Float e)
	{
		this.e = e;
	}

	public Float getF()
	{
		return f;
	}

	public void setF(Float f)
	{
		this.f = f;
	}
}
