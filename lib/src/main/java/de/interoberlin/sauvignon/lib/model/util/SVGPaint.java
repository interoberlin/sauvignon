package de.interoberlin.sauvignon.lib.model.util;

import android.graphics.Color;

public class SVGPaint extends android.graphics.Paint
{
	// -------------------------
	// Constructor
	// -------------------------

	public SVGPaint()
	{
	}

	public SVGPaint(int a, int r, int g, int b)
	{
		setARGB(a, r, g, b);
	}

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

	public void increaseA(int i)
	{
		setA(getA() + i);
	}

	public void increaseR(int i)
	{
		setR(getR() + i);
	}

	public void increaseG(int i)
	{
		setG(getG() + i);
	}

	public void increaseB(int i)
	{
		setB(getB() + i);
	}

	public void increaseH(int i)
	{
		setH(getH() + i);
	}

	public void increaseS(int i)
	{
		setS(getS() + i);
	}

	public void increaseV(int i)
	{
		setV(getV() + i);
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

		hsv[0] = h / 255f * 360f;
		hsv[1] = getS() / 255f;
		hsv[2] = getV() / 255f;

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

		hsv[0] = getH() / 255f * 360f;
		hsv[1] = s / 255f;
		hsv[2] = getV() / 255f;

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

		hsv[0] = getH() / 255f * 360f;
		hsv[1] = getS() / 255f;
		hsv[2] = v / 255f;

		setColor(Color.HSVToColor(hsv));
	}
}
