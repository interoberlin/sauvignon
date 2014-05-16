package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformRotate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.ROTATE;
	private Double angle = 0d;
	private Double cx = 0d; // center of rotation
	private Double cy = 0d;
	
	public SVGTransformRotate(Double angle)
	{
		this.angle = angle;
		this.cx = 0d;
		this.cy = 0d;
		this.updateMatrix = true;
	}
	
	public SVGTransformRotate(Double angle, Double cx, Double cy)
	{
		this.angle = angle;
		this.cx = cx;
		this.cy = cy;
		this.updateMatrix = true;
	}
	
	public Double getAngle()
	{
		return angle;
	}

	public void setAngle(Double angle)
	{
		this.angle = angle;
		this.updateMatrix = true;
	}
	
	public Double getCx()
	{
		return cx;
	}

	public void setCx(Double cx)
	{
		this.cx = cx;
		this.updateMatrix = true;
	}

	public Double getCy()
	{
		return cy;
	}

	public void setCy(Double cy)
	{
		this.cy = cy;
		this.updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle), 0d, 0d);
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
