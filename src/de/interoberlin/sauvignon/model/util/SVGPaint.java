package de.interoberlin.sauvignon.model.util;

import android.graphics.Color;

public class SVGPaint extends android.graphics.Paint
{

	// -------------------------
	// Methods
	// -------------------------

	public SVGPaint clone()
	{
		SVGPaint p = new SVGPaint();

		p.setA(this.getA());
		p.setR(this.getR());
		p.setG(this.getG());
		p.setB(this.getB());

		return p;
	}

	// -------------------------
	// Getters / Setters
	// -------------------------

	public int getA()
	{
		return getAlpha();
	}

	public void setA(int a)
	{
		setAlpha(a);
	}

	public int getR()
	{
		return Color.red(getColor());
	}

	public void setR(int r)
	{
		setARGB(getA(), r, getG(), getB());
	}

	public int getG()
	{
		return Color.green(getColor());
	}

	public void setG(int g)
	{
		setARGB(getA(), getR(), g, getB());
	}

	public int getB()
	{
		return Color.blue(getColor());
	}

	public void setB(int b)
	{
		setARGB(getA(), getR(), getG(), b);
	}

	public int getH()
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };

		Color.colorToHSV(getColor(), hsv);

		return (int) (hsv[0] * 255 / 360);
	}

	public void setH(int h)
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };
		
		hsv[0] = h / 255 * 360;
		hsv[1] = getS() / 255;
		hsv[2] = getV() / 255;

		setColor(Color.HSVToColor(hsv));
	}

	public int getS()
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };

		Color.colorToHSV(getColor(), hsv);

		return (int) (hsv[1] * 255);
	}

	public void setS(int s)
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };
		
		hsv[0] = getH() / 255 * 360;
		hsv[1] = s / 255;
		hsv[2] = getV() / 255;

		setColor(Color.HSVToColor(hsv));
	}

	public int getV()
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };

		Color.colorToHSV(getColor(), hsv);

		return (int) (hsv[2] * 255);
	}

	public void setV(int v)
	{
		float[] hsv =
		{ 0.0f, 0.0f, 0.0f };
		
		hsv[0] = getH() / 255 * 360;
		hsv[1] = getS() / 255;
		hsv[2] = v / 255;

		setColor(Color.HSVToColor(hsv));
	}
}
