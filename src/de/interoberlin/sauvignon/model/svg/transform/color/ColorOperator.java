package de.interoberlin.sauvignon.model.svg.transform.color;

import android.graphics.Paint;

public class ColorOperator
{
	public EColorOperatorType	type;
	public Paint				resultingPaint	= new Paint();

	public ColorOperator(EColorOperatorType type, Paint paint)
	{
		this.type = type;
		this.resultingPaint = paint;
	}

	public EColorOperatorType getType()
	{
		return type;
	}

	public void setType(EColorOperatorType type)
	{
		this.type = type;
	}

	public Paint getResultingPaint()
	{
		return resultingPaint;
	}

	public void setResultingPaint(Paint resultingPaint)
	{
		this.resultingPaint = resultingPaint;
	}
}