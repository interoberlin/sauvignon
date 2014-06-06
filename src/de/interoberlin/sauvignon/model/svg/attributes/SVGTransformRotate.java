package de.interoberlin.sauvignon.model.svg.attributes;

import de.interoberlin.sauvignon.model.util.Matrix;

public class SVGTransformRotate extends ATransformOperator
{
	public final ETransformOperators type = ETransformOperators.ROTATE;
	private Float angle = 0f;
	private Float cx = 0f; // center of rotation
	private Float cy = 0f;
	
	public SVGTransformRotate() {}
	
	public SVGTransformRotate(Float angle)
	{
		this.angle = angle;
		updateMatrix = true;
	}
	
	public SVGTransformRotate(Float cx, Float cy, Float angle)
	{
		this.angle = angle;
		this.cx = cx;
		this.cy = cy;
		updateMatrix = true;
	}
	
	public SVGTransformRotate(Float[] args)
	{
		if (args.length > 0)
		{
			angle = args[0];
			if (args.length > 2)
			{
				cx = args[1];
				cy = args[2];
			}
			updateMatrix = true;
		}
	}
	
	public Float getAngle()
	{
		return angle;
	}

	public void setAngle(Float angle)
	{
		this.angle = angle;
		this.updateMatrix = true;
	}
	
	public Float getCx()
	{
		return cx;
	}

	public void setCx(Float cx)
	{
		this.cx = cx;
		this.updateMatrix = true;
	}

	public Float getCy()
	{
		return cy;
	}

	public void setCy(Float cy)
	{
		this.cy = cy;
		this.updateMatrix = true;
	}

	public Matrix getResultingMatrix()
	{
		if (this.updateMatrix)
		{
			this.resultingMatrix = new Matrix(
									(float) Math.cos(angle),
									(float) Math.sin(angle),
									(float) -Math.sin(angle),
									(float) Math.cos(angle),
									(float) (-cx*Math.cos(angle) + cy*Math.sin(angle) + cx),
									(float) (-cx*Math.sin(angle) - cy*Math.cos(angle) + cy));
			this.updateMatrix = false;
		}
		return this.resultingMatrix;
	}
}
